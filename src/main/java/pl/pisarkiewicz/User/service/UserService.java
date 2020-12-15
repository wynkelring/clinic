package pl.pisarkiewicz.User.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pisarkiewicz.ActivationToken.entity.ActivationToken;
import pl.pisarkiewicz.ActivationToken.repository.ActivationTokenRepository;
import pl.pisarkiewicz.Global.service.EmailService;
import pl.pisarkiewicz.Role.entity.Role;
import pl.pisarkiewicz.Role.service.RoleService;
import pl.pisarkiewicz.User.dto.UserEditDTO;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.User.repository.UserRepository;

import java.util.*;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ActivationTokenRepository activationTokenRepository;
    private final RoleService roleService;
    private final EmailService emailService;
    private final MessageSource messageSource;

    public UserService(UserRepository userRepository, ActivationTokenRepository activationTokenRepository, RoleService roleService, EmailService emailService, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.activationTokenRepository = activationTokenRepository;
        this.roleService = roleService;
        this.emailService = emailService;
        this.messageSource = messageSource;
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
    public List<User> getDoctorsList() {
        return userRepository.findAllByRoles(roleService.getByRoleName("ROLE_DOCTOR"));
    }

    @Override
    public void addUser(User user) {
        user.getRoles().add(roleService.getByRoleName("ROLE_PATIENT"));
        user.setPassword(hashPassword(user.getPassword()));
        ActivationToken activationToken = new ActivationToken();
        activationToken.setToken(createVerificationToken());
        activationTokenRepository.save(activationToken);
        user.setActivationToken(activationToken);
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(),
                messageSource.getMessage("email.title.register", null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("email.content.register", null, LocaleContextHolder.getLocale()) +
                        "\n" +
                        "http://localhost:8080/activateAccount?token=" + user.getActivationToken().getToken());
    }

    @Override
    public void editUserForAdmin(UserEditDTO euser, User user) {
        user.setFirstName(euser.getFirstName());
        user.setLastName(euser.getLastName());
        user.setTelephone(euser.getTelephone());
        user.setPesel(euser.getPesel());
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
        if (userRepository.findByActivationTokenTokenAndDeletedIsFalse(token).isPresent()) {
            User user = userRepository.findByActivationTokenTokenAndDeletedIsFalse(token).get();
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

    private String createVerificationToken() {
        return UUID.randomUUID().toString();
    }
}
