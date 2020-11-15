package pl.pisarkiewicz.Global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.User.service.IUserService;
import pl.pisarkiewicz.User.validator.UserValidator;

import javax.validation.Valid;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping(value = "/")
public class MainController {

    private final IUserService userService;
    private final UserValidator userValidator;

    public MainController(IUserService userService) {
        this.userService = userService;
        this.userValidator = new UserValidator(userService);
    }

    @GetMapping
    public String helloWorld(Locale locale, Model model) {

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
        String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);

        return "hello";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "login.error");
        }
        if (logout != null) {
            model.addAttribute("msg", "login.logout");
        }
        return "login";
    }


    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register", "register", new User());
    }

    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute("register") User user, BindingResult result) {
        userValidator.validate(user, result);
        System.out.println(result.getAllErrors());
        if (result.getErrorCount() == 0) {
            userService.addUser(user);
            return "redirect:/";
        }
        return "register";
    }

    @RequestMapping("/activateAccount")
    public String activateAccount(@RequestParam(name = "token") String token) {
        userService.activateUser(token);
        return "login";
    }

    @RequestMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }
}
