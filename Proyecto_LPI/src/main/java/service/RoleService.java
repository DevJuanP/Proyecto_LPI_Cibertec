package service;

import java.sql.SQLException;
import java.util.List;

import model.Role;
import repository.IRoleRepository;

public class RoleService implements IRoleService {
    private final IRoleRepository roleRepository;

    public RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() throws SQLException, ClassNotFoundException {
        return roleRepository.findAll();
    }
}