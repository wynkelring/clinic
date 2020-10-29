package pl.pisarkiewicz.Role.service;

import org.springframework.stereotype.Service;
import pl.pisarkiewicz.Role.entity.Role;
import pl.pisarkiewicz.Role.repository.RoleRepository;

import java.util.List;

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
}
