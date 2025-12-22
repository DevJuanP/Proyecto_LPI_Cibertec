package service;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.List;

import dto.shared.PagedResult;
import dto.user.UserData;
import model.User;

public interface IUserService {

    User authenticate(String email, String password) throws SQLException, ClassNotFoundException;
    User adminAuthenticate(String email, String password) throws SQLException, ClassNotFoundException, AccessDeniedException;
    User register(String email, String password, String statusId) throws SQLException, ClassNotFoundException;
    User getUserById(String userId) throws SQLException, ClassNotFoundException;
    User getUserByEmail(String email) throws SQLException, ClassNotFoundException;
    List<User> getAllUsers() throws SQLException, ClassNotFoundException;
    void updateUser(User user) throws SQLException, ClassNotFoundException;
    void changePassword(String userId, String currentPassword, String newPassword) throws SQLException, ClassNotFoundException;
    void deleteUser(String userId) throws SQLException, ClassNotFoundException;
    void assignRole(String userId, String roleId) throws SQLException, ClassNotFoundException;
    void removeRole(String userId, String roleId) throws SQLException, ClassNotFoundException;
    boolean hasRole(String userId, String roleName) throws SQLException, ClassNotFoundException;
    public PagedResult<UserData> getRegisteredUsers(int page, int pageSize, String search, String roleId, String statusId) throws SQLException, ClassNotFoundException;
    int getTotalUsersCount() throws SQLException, ClassNotFoundException;
    int getActiveUsersCount() throws SQLException, ClassNotFoundException;
}
