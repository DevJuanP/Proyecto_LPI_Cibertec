package core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Dependency Injection Container para aplicaciones web Java
 */
public class ServiceContainer implements AutoCloseable {
    
    private final Map<Class<?>, ServiceDescriptor> serviceDescriptors = new ConcurrentHashMap<>();
    
    private final Map<Class<?>, Object> singletonInstances = new ConcurrentHashMap<>();
    private final Map<Class<?>, ReentrantLock> singletonLocks = new ConcurrentHashMap<>();
    
    private final ThreadLocal<ScopeContext> currentScope = ThreadLocal.withInitial(ScopeContext::new);
    
    private final ThreadLocal<Set<Class<?>>> resolutionStack = ThreadLocal.withInitial(HashSet::new);
    
    private volatile boolean disposed = false;
    
    /**
     * Service descriptor holding registration information
     */
    private static class ServiceDescriptor {
        final Class<?> serviceType;
        final Class<?> implementationType;
        final ServiceLifetime lifetime;
        final Object instance;
        
        ServiceDescriptor(Class<?> serviceType, Class<?> implementationType, ServiceLifetime lifetime) {
            this.serviceType = serviceType;
            this.implementationType = implementationType;
            this.lifetime = lifetime;
            this.instance = null;
        }
        
        ServiceDescriptor(Class<?> serviceType, Object instance) {
            this.serviceType = serviceType;
            this.implementationType = instance.getClass();
            this.lifetime = ServiceLifetime.SINGLETON;
            this.instance = instance;
        }
    }
    
    /**
     * Service lifetime enumeration
     */
    public enum ServiceLifetime {
        SINGLETON,
        SCOPED,
        TRANSIENT
    }
    
    /**
     * Scope context for managing scoped instances
     */
    private static class ScopeContext {
        final Map<Class<?>, Object> instances = new HashMap<>();
        boolean isActive = false;
        
        void activate() {
            isActive = true;
        }
        
        void clear() {
            instances.values().forEach(instance -> {
                if (instance instanceof AutoCloseable) {
                    try {
                        ((AutoCloseable) instance).close();
                    } catch (Exception e) {
                        System.err.println("Error disposing scoped instance: " + e.getMessage());
                    }
                }
            });
            instances.clear();
            isActive = false;
        }
    }
    
    /**
     * Registers a pre-created instance as a singleton
     */
    public <T> ServiceContainer addInstance(Class<T> serviceType, T instance) {
        validateNotDisposed();
        Objects.requireNonNull(serviceType, "Service type cannot be null");
        Objects.requireNonNull(instance, "Instance cannot be null");
        
        if (!serviceType.isInstance(instance)) {
            throw new IllegalArgumentException(
                "Instance must be assignable to service type: " + serviceType.getName());
        }
        
        serviceDescriptors.put(serviceType, new ServiceDescriptor(serviceType, instance));
        singletonInstances.put(serviceType, instance);
        return this;
    }
    
    /**
     * Registers a service as a singleton (one instance for entire application)
     */
    public <TService, TImplementation extends TService> ServiceContainer addSingleton(
            Class<TService> serviceType, 
            Class<TImplementation> implementationType) {
        return addService(serviceType, implementationType, ServiceLifetime.SINGLETON);
    }
    
    /**
     * Registers a service as scoped (one instance per scope)
     */
    public <TService, TImplementation extends TService> ServiceContainer addScoped(
            Class<TService> serviceType, 
            Class<TImplementation> implementationType) {
        return addService(serviceType, implementationType, ServiceLifetime.SCOPED);
    }
    
    /**
     * Registers a service as transient (new instance every time)
     */
    public <TService, TImplementation extends TService> ServiceContainer addTransient(
            Class<TService> serviceType, 
            Class<TImplementation> implementationType) {
        return addService(serviceType, implementationType, ServiceLifetime.TRANSIENT);
    }
    
    /**
     * Internal method to register a service with specified lifetime
     */
    private <TService, TImplementation extends TService> ServiceContainer addService(
            Class<TService> serviceType, 
            Class<TImplementation> implementationType,
            ServiceLifetime lifetime) {
        validateNotDisposed();
        Objects.requireNonNull(serviceType, "Service type cannot be null");
        Objects.requireNonNull(implementationType, "Implementation type cannot be null");
        Objects.requireNonNull(lifetime, "Lifetime cannot be null");
        
        if (!serviceType.isAssignableFrom(implementationType)) {
            throw new IllegalArgumentException(
                implementationType.getName() + " is not assignable to " + serviceType.getName());
        }
        
        serviceDescriptors.put(serviceType, 
            new ServiceDescriptor(serviceType, implementationType, lifetime));
        return this;
    }
    
    /**
     * Gets an instance of the requested service type
     */
    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceType) {
        validateNotDisposed();
        Objects.requireNonNull(serviceType, "Service type cannot be null");
        
        ServiceDescriptor descriptor = serviceDescriptors.get(serviceType);
        if (descriptor == null) {
            throw new ServiceNotFoundException(
                "Service not registered: " + serviceType.getName() + 
                ". Did you forget to register it?");
        }
        
        if (descriptor.instance != null) {
            return (T) descriptor.instance;
        }
        
        switch (descriptor.lifetime) {
            case SINGLETON:
                return (T) resolveSingleton(descriptor);
            case SCOPED:
                return (T) resolveScoped(descriptor);
            case TRANSIENT:
                return (T) resolveTransient(descriptor);
            default:
                throw new IllegalStateException("Unknown lifetime: " + descriptor.lifetime);
        }
    }
    
    /**
     * Gets an instance or returns null if not registered
     */
    public <T> T getServiceOrNull(Class<T> serviceType) {
        try {
            return getService(serviceType);
        } catch (ServiceNotFoundException e) {
            return null;
        }
    }
    
    /**
     * Checks if a service is registered
     */
    public boolean isRegistered(Class<?> serviceType) {
        return serviceDescriptors.containsKey(serviceType);
    }
    
    /**
     * Resolves a singleton instance (thread-safe, double-checked locking)
     */
    private Object resolveSingleton(ServiceDescriptor descriptor) {
        Object instance = singletonInstances.get(descriptor.serviceType);
        if (instance != null) {
            return instance;
        }
        
        ReentrantLock lock = singletonLocks.computeIfAbsent(
            descriptor.serviceType, k -> new ReentrantLock());
        
        lock.lock();
        try {
            instance = singletonInstances.get(descriptor.serviceType);
            if (instance != null) {
                return instance;
            }
            
            instance = createInstance(descriptor.implementationType);
            singletonInstances.put(descriptor.serviceType, instance);
            return instance;
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * Resolves a scoped instance
     */
    private Object resolveScoped(ServiceDescriptor descriptor) {
        ScopeContext scope = currentScope.get();
        
        if (!scope.isActive) {
            throw new ScopeNotActiveException(
                "Cannot resolve scoped service '" + descriptor.serviceType.getName() + 
                "' outside of an active scope. Call beginScope() first.");
        }
        
        Object instance = scope.instances.get(descriptor.serviceType);
        if (instance != null) {
            return instance;
        }
        
        instance = createInstance(descriptor.implementationType);
        scope.instances.put(descriptor.serviceType, instance);
        return instance;
    }
    
    /**
     * Resolves a transient instance (always creates new)
     */
    private Object resolveTransient(ServiceDescriptor descriptor) {
        return createInstance(descriptor.implementationType);
    }
    
    /**
     * Creates an instance of the specified type, resolving all dependencies
     */
    private <T> T createInstance(Class<T> type) {
        Set<Class<?>> stack = resolutionStack.get();
        if (stack.contains(type)) {
            throw new CircularDependencyException(
                "Circular dependency detected while resolving: " + type.getName() + 
                ". Resolution chain: " + stack);
        }
        
        stack.add(type);
        try {
            Constructor<T> constructor = selectConstructor(type);
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] parameters = new Object[parameterTypes.length];
            
            for (int i = 0; i < parameterTypes.length; i++) {
                parameters[i] = getService(parameterTypes[i]);
            }
            
            return constructor.newInstance(parameters);
        } catch (InvocationTargetException e) {
            throw new ServiceCreationException(
                "Error invoking constructor for: " + type.getName(), 
                e.getCause());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ServiceCreationException(
                "Error creating instance of: " + type.getName(), e);
        } finally {
            stack.remove(type);
        }
    }
    
    /**
     * Selects the best constructor for dependency injection
     * Prefers constructors with more parameters (greedy constructor selection)
     */
    @SuppressWarnings("unchecked")
    private <T> Constructor<T> selectConstructor(Class<T> type) {
        Constructor<?>[] constructors = type.getConstructors();
        
        if (constructors.length == 0) {
            throw new ServiceCreationException(
                "No public constructors found for: " + type.getName());
        }
        
        Arrays.sort(constructors, (c1, c2) -> 
            Integer.compare(c2.getParameterCount(), c1.getParameterCount()));
        
        for (Constructor<?> constructor : constructors) {
            if (canResolveParameters(constructor)) {
                return (Constructor<T>) constructor;
            }
        }
        
        return (Constructor<T>) constructors[0];
    }
    
    /**
     * Checks if all parameters of a constructor can be resolved
     */
    private boolean canResolveParameters(Constructor<?> constructor) {
        for (Class<?> paramType : constructor.getParameterTypes()) {
            if (!isRegistered(paramType)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Begins a new scope (call at start of HTTP request, transaction, etc.)
     */
    public ServiceScope beginScope() {
        validateNotDisposed();
        ScopeContext scope = currentScope.get();
        
        if (scope.isActive) {
            throw new IllegalStateException(
                "Cannot begin a new scope while another scope is active. " +
                "Call endScope() first or use try-with-resources.");
        }
        
        scope.activate();
        return new ServiceScope(this);
    }
    
    /**
     * Ends the current scope and disposes all scoped instances
     */
    void endScope() {
        ScopeContext scope = currentScope.get();
        scope.clear();
    }
    
    /**
     * Represents a scope that can be used with try-with-resources
     */
    public static class ServiceScope implements AutoCloseable {
        private final ServiceContainer container;
        private boolean closed = false;
        
        private ServiceScope(ServiceContainer container) {
            this.container = container;
        }
        
        @Override
        public void close() {
            if (!closed) {
                container.endScope();
                closed = true;
            }
        }
    }
    
    /**
     * Prints all registered services (useful for debugging)
     */
    public void printRegisteredServices() {
        System.out.println("=== Registered Services ===");
        
        Map<ServiceLifetime, List<ServiceDescriptor>> grouped = new EnumMap<>(ServiceLifetime.class);
        for (ServiceLifetime lifetime : ServiceLifetime.values()) {
            grouped.put(lifetime, new ArrayList<>());
        }
        
        serviceDescriptors.values().forEach(descriptor -> 
            grouped.get(descriptor.lifetime).add(descriptor));
        
        for (ServiceLifetime lifetime : ServiceLifetime.values()) {
            List<ServiceDescriptor> descriptors = grouped.get(lifetime);
            if (!descriptors.isEmpty()) {
                System.out.println("\n" + lifetime + ":");
                descriptors.forEach(d -> 
                    System.out.println("  " + d.serviceType.getSimpleName() + 
                        " -> " + d.implementationType.getSimpleName()));
            }
        }
        
        System.out.println("\nTotal: " + serviceDescriptors.size() + " services");
        System.out.println("===========================\n");
    }
    
    /**
     * Gets the total number of registered services
     */
    public int getServiceCount() {
        return serviceDescriptors.size();
    }
    
    /**
     * Gets statistics about the container
     */
    public ContainerStatistics getStatistics() {
        int singletonCount = 0, scopedCount = 0, transientCount = 0;
        
        for (ServiceDescriptor descriptor : serviceDescriptors.values()) {
            switch (descriptor.lifetime) {
                case SINGLETON: singletonCount++; break;
                case SCOPED: scopedCount++; break;
                case TRANSIENT: transientCount++; break;
            }
        }
        
        return new ContainerStatistics(
            singletonCount, scopedCount, transientCount,
            singletonInstances.size());
    }
    
    /**
     * Container statistics
     */
    public static class ContainerStatistics {
        public final int singletonCount;
        public final int scopedCount;
        public final int transientCount;
        public final int instantiatedSingletons;
        
        ContainerStatistics(int singletonCount, int scopedCount, 
                          int transientCount, int instantiatedSingletons) {
            this.singletonCount = singletonCount;
            this.scopedCount = scopedCount;
            this.transientCount = transientCount;
            this.instantiatedSingletons = instantiatedSingletons;
        }
        
        @Override
        public String toString() {
            return String.format(
                "Singletons: %d (%d instantiated), Scoped: %d, Transient: %d",
                singletonCount, instantiatedSingletons, scopedCount, transientCount);
        }
    }
    
    /**
     * Validates the container configuration
     * Checks for captive dependencies and missing services
     */
    public ValidationResult validate() {
        List<String> errors = new ArrayList<>();
        List<String> warnings = new ArrayList<>();
        
        for (ServiceDescriptor descriptor : serviceDescriptors.values()) {
            if (descriptor.instance != null) continue; // Skip pre-created instances
            
            try {
                Constructor<?> constructor = selectConstructor(descriptor.implementationType);
                Class<?>[] paramTypes = constructor.getParameterTypes();
                
                for (Class<?> paramType : paramTypes) {
                    if (!isRegistered(paramType)) {
                        errors.add(String.format(
                            "%s depends on unregistered service: %s",
                            descriptor.implementationType.getSimpleName(),
                            paramType.getSimpleName()));
                        continue;
                    }
                    
                    ServiceDescriptor depDescriptor = serviceDescriptors.get(paramType);
                    if (isCaptiveDependency(descriptor.lifetime, depDescriptor.lifetime)) {
                        warnings.add(String.format(
                            "CAPTIVE DEPENDENCY: %s (%s) depends on %s (%s). " +
                            "The %s service will be captured and live longer than intended!",
                            descriptor.serviceType.getSimpleName(),
                            descriptor.lifetime,
                            depDescriptor.serviceType.getSimpleName(),
                            depDescriptor.lifetime,
                            depDescriptor.lifetime));
                    }
                }
            } catch (Exception e) {
                errors.add(String.format(
                    "Error validating %s: %s",
                    descriptor.implementationType.getSimpleName(),
                    e.getMessage()));
            }
        }
        
        return new ValidationResult(errors, warnings);
    }
    
    /**
     * Checks if a dependency relationship would cause a captive dependency
     */
    private boolean isCaptiveDependency(ServiceLifetime consumer, ServiceLifetime dependency) {
        if (consumer == ServiceLifetime.SINGLETON && dependency != ServiceLifetime.SINGLETON) {
            return true;
        }
        
        if (consumer == ServiceLifetime.SCOPED && dependency == ServiceLifetime.TRANSIENT) {
            return true;
        }
        return false;
    }
    
    /**
     * Validation result
     */
    public static class ValidationResult {
        public final List<String> errors;
        public final List<String> warnings;
        
        ValidationResult(List<String> errors, List<String> warnings) {
            this.errors = Collections.unmodifiableList(errors);
            this.warnings = Collections.unmodifiableList(warnings);
        }
        
        public boolean isValid() {
            return errors.isEmpty();
        }
        
        public void printResults() {
            if (warnings.isEmpty() && errors.isEmpty()) {
                System.out.println("✓ Container validation passed with no issues");
                return;
            }
            
            if (!warnings.isEmpty()) {
                System.out.println("⚠ Warnings:");
                warnings.forEach(w -> System.out.println("  " + w));
            }
            
            if (!errors.isEmpty()) {
                System.out.println("✗ Errors:");
                errors.forEach(e -> System.out.println("  " + e));
            }
        }
    }
    
    /**
     * Disposes the container and all singleton instances
     */
    @Override
    public void close() {
        if (disposed) return;
        
        disposed = true;
        
        singletonInstances.values().forEach(instance -> {
            if (instance instanceof AutoCloseable) {
                try {
                    ((AutoCloseable) instance).close();
                } catch (Exception e) {
                    System.err.println("Error disposing singleton: " + e.getMessage());
                }
            }
        });
        
        singletonInstances.clear();
        serviceDescriptors.clear();
        singletonLocks.clear();
    }
    
    private void validateNotDisposed() {
        if (disposed) {
            throw new IllegalStateException("Container has been disposed");
        }
    }
    
    
    public static class ServiceNotFoundException extends RuntimeException {
        public ServiceNotFoundException(String message) {
            super(message);
        }
    }
    
    public static class ServiceCreationException extends RuntimeException {
        public ServiceCreationException(String message, Throwable cause) {
            super(message, cause);
        }
        
        public ServiceCreationException(String message) {
            super(message);
        }
    }
    
    public static class CircularDependencyException extends RuntimeException {
        public CircularDependencyException(String message) {
            super(message);
        }
    }
    
    public static class ScopeNotActiveException extends RuntimeException {
        public ScopeNotActiveException(String message) {
            super(message);
        }
    }
}