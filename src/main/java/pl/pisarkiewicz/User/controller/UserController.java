package pl.pisarkiewicz.User.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.pisarkiewicz.User.entity.User;

@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/addUser")
    public ModelAndView showAppUsers() {
        return new ModelAndView("addUser", "addUser", new User());
    }

    @PostMapping("/addUser")
    public String addAppUser(@ModelAttribute("addUser") User user) {
        System.out.println("First Name: " + user.getFirstName() +
                " Last Name: " + user.getLastName() + " Tel.: " +
                user.getTelephone() + " Email: " + user.getEmail());

        return "redirect:addUser";
    }

}

