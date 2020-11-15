package pl.pisarkiewicz.User.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import pl.pisarkiewicz.User.entity.User;

public interface IUserService {
    User getUser(Long id);
    User getUserByEmail(String email);
    Page<User> getUsers(Pageable pageable);
    void addUser(User user);
    @PreAuthorize("hasRole('ROLE_ADMIN') OR (#user.email == principal.username)")
    void editUser(User user);
    @Secured("ROLE_ADMIN")
    void deleteUser(Long id);
    void activateUser(String token);
    boolean userExists(String email);
}
