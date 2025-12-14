package core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Dependency Injection Container para aplicaciones web Java
 */
public class ServiceContainer {
    private final Map<Class<?>, Object> instances = new HashMap<>();
    private final Map<Class<?>, Class<?>> singletons = new HashMap<>();
    private final Map<Class<?>, Class<?>> transients = new HashMap<>();
    private final Map<Class<?>, Object> singletonInstances = new HashMap<>();

    /**
     * Registra una instancia previamente creada
     */
    public <T> void addInstance(Class<T> serviceType, T instance) {
        instances.put(serviceType, instance);
    }

    /**
     * Registra un servicio como singleton (una sola instancia para toda la aplicación)
     */
    public <TService, TImplementation extends TService> void addSingleton(
            Class<TService> serviceType, 
            Class<TImplementation> implementationType) {
        singletons.put(serviceType, implementationType);
    }

    /**
     * Registra un servicio como transient (nueva instancia cada vez que se solicita)
     */
    public <TService, TImplementation extends TService> void addTransient(
            Class<TService> serviceType, 
            Class<TImplementation> implementationType) {
        transients.put(serviceType, implementationType);
    }

    /**
     * Obtiene una instancia del servicio del contenedor
     */
    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceType) {
        // Verificar si existe una instancia registrada
        if (instances.containsKey(serviceType)) {
            return (T) instances.get(serviceType);
        }

        // Verificar si es un singleton
        if (singletons.containsKey(serviceType)) {
            return getSingleton(serviceType);
        }

        // Verificar si es un transient
        if (transients.containsKey(serviceType)) {
            return getTransient(serviceType);
        }

        throw new IllegalArgumentException("Servicio no registrado: " + serviceType.getName());
    }

    /**
     * Obtiene o crea una instancia singleton
     */
    @SuppressWarnings("unchecked")
    private <T> T getSingleton(Class<T> serviceType) {
        if (singletonInstances.containsKey(serviceType)) {
            return (T) singletonInstances.get(serviceType);
        }

        Class<?> implementationType = singletons.get(serviceType);
        T instance = createInstance((Class<T>) implementationType);
        singletonInstances.put(serviceType, instance);
        return instance;
    }

    /**
     * Crea una nueva instancia transient
     */
    @SuppressWarnings("unchecked")
    private <T> T getTransient(Class<T> serviceType) {
        Class<?> implementationType = transients.get(serviceType);
        return createInstance((Class<T>) implementationType);
    }

    /**
     * Crea una instancia del tipo especificado, resolviendo sus dependencias
     */
    @SuppressWarnings("unchecked")
    private <T> T createInstance(Class<T> type) {
        try {
            Constructor<?>[] constructors = type.getConstructors();
            if (constructors.length == 0) {
                throw new IllegalArgumentException("No hay constructores públicos para: " + type.getName());
            }

            // Usar el primer constructor (se puede mejorar para buscar el más apropiado)
            Constructor<?> constructor = constructors[0];
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] parameters = new Object[parameterTypes.length];

            // Resolver cada dependencia
            for (int i = 0; i < parameterTypes.length; i++) {
                parameters[i] = getService(parameterTypes[i]);
            }

            return (T) constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error creando instancia de: " + type.getName(), e);
        }
    }

    /**
     * Verifica si un servicio está registrado
     */
    public boolean isRegistered(Class<?> serviceType) {
        return instances.containsKey(serviceType) 
            || singletons.containsKey(serviceType) 
            || transients.containsKey(serviceType);
    }

    /**
     * Imprime todos los servicios registrados (útil para debugging)
     */
    public void printRegisteredServices() {
        System.out.println("=== Servicios Registrados ===");
        
        if (!instances.isEmpty()) {
            System.out.println("\nInstancias:");
            instances.keySet().forEach(type -> 
                System.out.println("  " + type.getSimpleName()));
        }
        
        if (!singletons.isEmpty()) {
            System.out.println("\nSingletons:");
            singletons.keySet().forEach(type -> 
                System.out.println("  " + type.getSimpleName() + " -> " + 
                    singletons.get(type).getSimpleName()));
        }
        
        if (!transients.isEmpty()) {
            System.out.println("\nTransients:");
            transients.keySet().forEach(type -> 
                System.out.println("  " + type.getSimpleName() + " -> " + 
                    transients.get(type).getSimpleName()));
        }
        
        System.out.println("=============================\n");
    }

    /**
     * Limpia todas las instancias singleton (útil para testing o shutdown)
     */
    public void clearSingletons() {
        singletonInstances.clear();
    }

    /**
     * Obtiene la cantidad total de servicios registrados
     */
    public int getServiceCount() {
        return instances.size() + singletons.size() + transients.size();
    }
}