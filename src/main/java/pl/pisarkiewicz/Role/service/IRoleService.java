package pl.pisarkiewicz.Role.service;

import pl.pisarkiewicz.Role.entity.Role;

import java.util.List;
import java.util.Set;

public interface IRoleService {
    void addRole(Role role);
    List<Role> listRole();
    Role getRoleById(Long id);
    Role getByRoleName(String role);
    Set<Role> convertStringsToRoles(Set<String> roles);
}
