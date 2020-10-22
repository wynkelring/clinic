package pl.pisarkiewicz.User.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.pisarkiewicz.User.entity.User;

public interface IUserService {
    public User getUser(Long id);
    public Page<User> getUsers(Pageable pageable);
    public void addUser(User user);
    public void editUser(User user);
    public void deleteUser(Long id);
}
