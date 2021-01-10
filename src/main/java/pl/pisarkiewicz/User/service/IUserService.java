package pl.pisarkiewicz.User.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.pisarkiewicz.User.dto.UserEditDTO;
import pl.pisarkiewicz.User.entity.User;

import java.util.List;

public interface IUserService {
    User getUser(Long id);

    Page<User> getUsersWhereIdIsNot(Long id, Pageable pageable);

    User getUserByEmail(String email);

    List<User> getDoctorsList();

    void addUser(User user);

    void editUserForUser(UserEditDTO euser, User user);

    void editUserForAdmin(UserEditDTO euser, User user);

    void deleteUser(Long id);

    void activateUser(String token);

    boolean userExists(String email);
}
