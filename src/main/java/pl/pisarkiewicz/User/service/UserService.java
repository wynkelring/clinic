package pl.pisarkiewicz.User.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pisarkiewicz.Global.service.EmailService;
import pl.pisarkiewicz.Role.repository.RoleRepository;
import pl.pisarkiewicz.User.dto.UserEditDTO;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.User.repository.UserRepository;

import java.time.LocalDate;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
    }

    @Override
    public User getUser(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public Page<User> getUsersWhereIdIsNot(Long id, Pageable pageable) {
        return userRepository.findAllByIdIsNot(id, pageable);
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
    public void editUser(UserEditDTO euser, User user) {
        user.setFirstName(euser.getFirstName());
        user.setLastName(euser.getLastName());
        user.setTelephone(euser.getTelephone());
        user.setPesel(euser.getPesel());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
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
