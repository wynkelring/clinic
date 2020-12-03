package pl.pisarkiewicz.Role.service;

import org.springframework.stereotype.Service;
import pl.pisarkiewicz.Role.entity.Role;
import pl.pisarkiewicz.Role.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> listRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public Role getByRoleName(String role) {
        Optional<Role> oRole = roleRepository.findByRole(role);
        return oRole.orElse(null);
    }

    @Override
    public Set<Role> convertStringsToRoles(Set<String> roles) {
        Set<Role> roleSet = new HashSet<>(0);
        roles.forEach(role -> roleSet.add(getByRoleName(role)));
        return roleSet;
    }
}
