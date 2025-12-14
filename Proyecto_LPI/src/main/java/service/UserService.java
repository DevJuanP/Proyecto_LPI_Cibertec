package service;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

import model.User;
import repository.IUserRepository;
import util.PasswordUtil;
import java.util.List;

/**
 * Implementación del servicio de usuarios.
 * Maneja autenticación, registro y gestión de usuarios.
 */
public class UserService implements IUserService {
    
    private final IUserRepository userRepository;
    
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public User authenticate(String email, String password) throws SQLException, ClassNotFoundException {
        if (email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            return null;
        }
        
        User user = userRepository.findByEmail(email);
        
        if (user == null) {
            return null;
        }
        
        if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
            return null;
        }
        
        if (!"active".equalsIgnoreCase(user.getStatus().getStatusName())) {
            throw new IllegalStateException("La cuenta no está activa");
        }
        
        return user;
    }

    @Override
    public User adminAuthenticate(String email, String password) throws SQLException, ClassNotFoundException, AccessDeniedException {
        if (email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            return null;
        }
        
        User user = userRepository.findByEmail(email);
        
        if (user == null) {
            return null;
        }
        
        if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
            return null;
        }
        
        if (!"active".equalsIgnoreCase(user.getStatus().getStatusName())) {
            throw new IllegalStateException("La cuenta no está activa");
        }

        if (!user.getRoles().stream()
            .anyMatch(role -> "Admin".equalsIgnoreCase(role.getRoleName()))) {
            throw new AccessDeniedException("Acceso denegado: se requieren permisos de administrador");
        }
        
        return user;
    }
    
    @Override
    public User register(String email, String password, String statusId) throws SQLException, ClassNotFoundException {
        
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es requerido");
        }
        
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }
        
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        
        User user = new User();
        user.setEmail(email);
        user.setPassword(PasswordUtil.hashPassword(password));
        user.setStatusId(statusId);
        
        userRepository.save(user);
        
        return user;
    }
    
    @Override
    public User getUserById(String userId) throws SQLException, ClassNotFoundException {
        return userRepository.findById(userId);
    }
    
    @Override
    public User getUserByEmail(String email) throws SQLException, ClassNotFoundException {
        return userRepository.findByEmail(email);
    }
    
    @Override
    public List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        return userRepository.findAll();
    }
    
    @Override
    public void updateUser(User user) throws SQLException, ClassNotFoundException {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("Usuario inválido");
        }
        
        userRepository.update(user);
    }
    
    @Override
    public void changePassword(String userId, String currentPassword, String newPassword) 
            throws SQLException, ClassNotFoundException {
        
        User user = userRepository.findById(userId);
        
        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        
        if (!PasswordUtil.verifyPassword(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta");
        }
        
        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("La nueva contraseña debe tener al menos 6 caracteres");
        }
        
        user.setPassword(PasswordUtil.hashPassword(newPassword));
        userRepository.update(user);
    }
    
    @Override
    public void deleteUser(String userId) throws SQLException, ClassNotFoundException {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID de usuario inválido");
        }
        
        userRepository.delete(userId);
    }
    
    @Override
    public void assignRole(String userId, String roleId) throws SQLException, ClassNotFoundException {
        userRepository.addRoleToUser(userId, roleId);
    }
    
    @Override
    public void removeRole(String userId, String roleId) throws SQLException, ClassNotFoundException {
        userRepository.removeRoleFromUser(userId, roleId);
    }
    
    @Override
    public boolean hasRole(String userId, String roleName) throws SQLException, ClassNotFoundException {
        User user = userRepository.findById(userId);
        
        if (user == null || user.getRoles() == null) {
            return false;
        }
        
        return user.getRoles().stream()
            .anyMatch(role -> roleName.equalsIgnoreCase(role.getRoleName()));
    }
}