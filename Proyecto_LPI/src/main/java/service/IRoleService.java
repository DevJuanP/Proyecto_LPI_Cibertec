package service;

import java.sql.SQLException;
import java.util.List;

import model.Role;

public interface IRoleService {
    List<Role> findAll() throws SQLException, ClassNotFoundException;
}