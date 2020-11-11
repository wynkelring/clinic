package pl.pisarkiewicz.User.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pisarkiewicz.Role.repository.RoleRepository;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.User.repository.UserRepository;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User getUser(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public void addUser(User user) {
        user.getRoles().add(roleRepository.findByRole("ROLE_PATIENT").orElse(null));
        user.setPassword(hashPassword(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void editUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }

    private String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
