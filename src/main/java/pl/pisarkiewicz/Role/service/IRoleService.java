package pl.pisarkiewicz.Role.service;

import pl.pisarkiewicz.Role.entity.Role;

import java.util.List;

public interface IRoleService {
    void addRole(Role role);
    List<Role> listRole();
    Role getRoleById(Long id);
}
