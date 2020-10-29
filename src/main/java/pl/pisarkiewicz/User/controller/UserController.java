package pl.pisarkiewicz.User.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.User.service.IUserService;
import pl.pisarkiewicz.User.service.UserService;
import pl.pisarkiewicz.User.validator.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;
    private final UserValidator userValidator = new UserValidator();

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/addUser")
    public ModelAndView showUsers() {
        return new ModelAndView("addUser", "addUser", new User());
    }

    @PostMapping("/addUser")
    public String addUser(@Valid @ModelAttribute("addUser") User user, BindingResult result, Model model) {

        System.out.println("First Name: " + user.getFirstName() +
                " Last Name: " + user.getLastName() + " Tel.: " +
                user.getTelephone() + " Email: " + user.getEmail());

        userValidator.validate(user, result);
        if(result.getErrorCount() == 0) {
            userService.addUser(user);
            return "redirect:addUser";
        }
        return "addUser";
    }

}

