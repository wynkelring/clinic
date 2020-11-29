package pl.pisarkiewicz.User.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.pisarkiewicz.User.dto.UserEditDTO;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.User.service.IUserService;
import pl.pisarkiewicz.User.validator.UserEditValidator;
import pl.pisarkiewicz.User.validator.UserValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;
    private final UserValidator userValidator;
    private final UserEditValidator userEditValidator;

    public UserController(IUserService userService) {
        this.userService = userService;
        this.userValidator = new UserValidator(userService);
        this.userEditValidator = new UserEditValidator();
    }

    @GetMapping("/editProfile")
    public ModelAndView register(Principal principal,
                                 @RequestParam(value = "success", required = false) String success,
                                 Model model) {
        if(success != null) {
            model.addAttribute("success", "editProfile.success");
        }
        User user = userService.getUserByEmail(principal.getName());
        return new ModelAndView("editProfile",
                "editProfile",
                user);
    }

    @PostMapping("/editProfile")
    public String registerPost(@Valid @ModelAttribute("editProfile") UserEditDTO user, BindingResult result, Principal principal) {
        userEditValidator.validate(user, result);
        User princ = userService.getUserByEmail(principal.getName());
        if (result.getErrorCount() == 0) {
            princ.setFirstName(user.getFirstName());
            userService.editUser(user, princ);
            return "redirect:/users/editProfile?success=true";
        }
        return "editProfile";
    }

}

