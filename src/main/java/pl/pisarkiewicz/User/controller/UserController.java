package pl.pisarkiewicz.User.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pisarkiewicz.User.service.IUserService;
import pl.pisarkiewicz.User.validator.UserValidator;

@Controller
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;
    private final UserValidator userValidator;

    public UserController(IUserService userService) {
        this.userService = userService;
        this.userValidator = new UserValidator(userService);
    }



}

