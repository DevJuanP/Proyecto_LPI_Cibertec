package repository;

import java.sql.SQLException;
import java.util.List;

import model.Role;
import model.User;

public interface IUserRepository {
    User findById(String userId) throws SQLException, ClassNotFoundException;
    User findByEmail(String email) throws SQLException, ClassNotFoundException;
    List<User> findAll() throws SQLException, ClassNotFoundException;
    void save(User user) throws SQLException, ClassNotFoundException;
    void update(User user) throws SQLException, ClassNotFoundException;
    void delete(String userId) throws SQLException, ClassNotFoundException;
    boolean existsByEmail(String email) throws SQLException, ClassNotFoundException;
    List<Role> findRolesByUserId(String userId) throws SQLException, ClassNotFoundException;
    void addRoleToUser(String userId, String roleId) throws SQLException, ClassNotFoundException;
    void removeRoleFromUser(String userId, String roleId) throws SQLException, ClassNotFoundException;
}
