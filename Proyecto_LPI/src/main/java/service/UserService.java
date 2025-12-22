package service;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

import model.User;
import repository.IStatusRepository;
import repository.IUserRepository;
import util.PasswordUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dto.shared.PagedResult;
import dto.user.UserData;

/**
 * Implementación del servicio de usuarios.
 * Maneja autenticación, registro y gestión de usuarios.
 */
public class UserService implements IUserService {
    
    private final IUserRepository userRepository;
    private final IStatusRepository statusRepository;
    
    public UserService(IUserRepository userRepository, IStatusRepository statusRepository) {
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
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

    @Override
    public PagedResult<UserData> getRegisteredUsers(int page, int pageSize, String search, 
            String roleId, String statusId) throws SQLException, ClassNotFoundException {
        
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;
        if (pageSize > 100) pageSize = 100;
        
        int offset = (page - 1) * pageSize;
        
        int totalItems = userRepository.count(search, roleId, statusId);
        
        LinkedList<User> users = userRepository.findAllPaginated(offset, pageSize, search, roleId, statusId);
        
        List<UserData> userDataList = new ArrayList<>(users.size());
        for (User user : users) {
            userDataList.add(mapToUserData(user));
        }
        
        return new PagedResult<>(userDataList, page, pageSize, totalItems);
    }

    @Override
    public int getTotalUsersCount() throws SQLException, ClassNotFoundException {
        return userRepository.count();
    }

    @Override
    public int getActiveUsersCount() throws SQLException, ClassNotFoundException {
        String activeStatusId = statusRepository.findByName("Active").getStatusId();

        return userRepository.count(null, null, activeStatusId);
    }

    private UserData mapToUserData(User user) {
        ArrayList<String> roleNames = new ArrayList<>(2);
        if (user.getRoles() != null) {
            user.getRoles().forEach(role -> roleNames.add(role.getRoleName()));
        }
        
        return new UserData(
            user.getUserId(),
            user.getEmail(),
            user.getCreatedAt(),
            user.getUpdatedAt(),
            user.getStatusId(),
            user.getStatus() != null ? user.getStatus().getStatusName() : null,
            roleNames
        );
    }
}