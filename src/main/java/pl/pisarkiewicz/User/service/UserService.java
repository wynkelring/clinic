package pl.pisarkiewicz.User.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pisarkiewicz.Global.service.EmailService;
import pl.pisarkiewicz.Role.entity.Role;
import pl.pisarkiewicz.Role.service.RoleService;
import pl.pisarkiewicz.User.dto.UserEditDTO;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.User.repository.UserRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, RoleService roleService, EmailService emailService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.emailService = emailService;
    }

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public Page<User> getUsersWhereIdIsNot(Long id, Pageable pageable) {
        return userRepository.findAllByIdIsNot(id, pageable);
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    @Override
    public void addUser(User user) {
        user.getRoles().add(roleService.getByRoleName("ROLE_PATIENT"));
        user.setPassword(hashPassword(user.getPassword()));
        user.setActivationToken(createVerificationToken(user));
        userRepository.save(user);
        /*emailService.sendEmail(
                user.getEmail(),
                "Dziękujemy za rejestrację",
                "Witaj na naszej stronie - aktywuj swoje konto <br>" +
                        "http://localhost:8080/activateAccount?token=" + user.getActivationToken()
        );*/
    }

    @Override
    public void editUserForAdmin(UserEditDTO euser, User user) {
        user.setFirstName(euser.getFirstName());
        user.setLastName(euser.getLastName());
        user.setTelephone(euser.getTelephone());
        user.setPesel(euser.getPesel());
        System.out.println(euser.getRoles());
        if (!euser.getRoles().isEmpty()) {
            user.setRoles(roleService.convertStringsToRoles(euser.getRoles()));
        } else {
            Set<Role> roleSet = new HashSet<>(0);
            roleSet.add(roleService.getByRoleName("ROLE_PATIENT"));
            user.setRoles(roleSet);
        }
        userRepository.save(user);
    }

    @Override
    public void editUserForUser(UserEditDTO euser, User user) {
        user.setFirstName(euser.getFirstName());
        user.setLastName(euser.getLastName());
        user.setTelephone(euser.getTelephone());
        user.setPesel(euser.getPesel());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> oUser = userRepository.findById(id);
        if (oUser.isPresent()) {
            User user = oUser.get();
            user.setEnabled(false);
            user.setDeleted(true);
            userRepository.save(user);
        }
    }

    @Override
    public void activateUser(String token) {
        if(userRepository.findByActivationToken(token).isPresent()) {
            User user = userRepository.findByActivationToken(token).get();
            user.setEnabled(true);
            userRepository.save(user);
        }
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    private String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private String createVerificationToken(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode("s@lty" + user.getEmail() + LocalDate.now());
    }
}
