package repository;

import java.sql.SQLException;
import java.util.List;

import model.Role;

public interface IRoleRepository {
    Role findById(String roleId) throws SQLException, ClassNotFoundException;
    Role findByName(String roleName) throws SQLException, ClassNotFoundException;
    List<Role> findAll() throws SQLException, ClassNotFoundException;
    
}
