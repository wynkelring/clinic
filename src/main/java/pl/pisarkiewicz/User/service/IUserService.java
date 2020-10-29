package pl.pisarkiewicz.User.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import pl.pisarkiewicz.User.entity.User;

public interface IUserService {
    User getUser(Long id);
    User getUserByLogin(String login);
    Page<User> getUsers(Pageable pageable);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void addUser(User user);
    @PreAuthorize("hasRole('ROLE_ADMIN') OR (#user.login == principal.username)")
    void editUser(User user);
    @Secured("ROLE_ADMIN")
    void deleteUser(Long id);
}
