package pl.pisarkiewicz.User.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list/{id}")
    public String register(Principal principal,
                                 @PathVariable Integer id,
                                 Model model) {
        User user = userService.getUserByEmail(principal.getName());
        Pageable pageable = PageRequest.of(id - 1, 1);
        Page<User> userPage = userService.getUsersWhereIdIsNot(user.getId(), pageable);
        model.addAttribute("userList", userPage.getContent());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("currentPage", id);
        return "userList";
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

