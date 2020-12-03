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
import pl.pisarkiewicz.Role.repository.RoleRepository;
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
    private final RoleRepository roleRepository;

    public UserController(IUserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.userValidator = new UserValidator(userService);
        this.roleRepository = roleRepository;
        this.userEditValidator = new UserEditValidator();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list/{id}")
    public String getUserList(Principal principal,
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/{id}")
    public String getUser(Principal principal,
                          @PathVariable Long id,
                          @RequestParam(value = "success", required = false) String success,
                          @RequestParam(value = "deleted", required = false) String deleted,
                          Model model) {
        if(success != null) {
            model.addAttribute("success", "editProfile.success");
        }
        if(deleted != null) {
            model.addAttribute("deleted", "editProfile.deleted");
        }
        User princ = userService.getUserByEmail(principal.getName());
        User editedUser = userService.getUser(id);
        if(princ.getId().equals(id) || editedUser == null) {
            return "redirect:/users/list/1";
        }
        model.addAttribute("editUser", editedUser);
        model.addAttribute("availableRoles", roleRepository.findAll());
        return "editUser";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/get/{id}")
    public String updateUser(@PathVariable Long id,
                             @Valid @ModelAttribute("editUser") UserEditDTO user,
                             BindingResult result,
                             Principal principal) {
        User princ = userService.getUserByEmail(principal.getName());
        User editedUser = userService.getUser(id);
        if (princ.getId().equals(id)) {
            return "redirect:/users/list/1";
        }
        userEditValidator.validate(user, result);
        if (result.getErrorCount() == 0 && editedUser != null) {
            userService.editUserForAdmin(user, editedUser);
            return "redirect:/users/get/" + id + "?success=true";
        }
        return "redirect:/users/get/" + id;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id,
                             Principal principal) {
        User princ = userService.getUserByEmail(principal.getName());
        if (princ.getId().equals(id)) {
            return "redirect:/users/list/1";
        }
        userService.deleteUser(id);
        return "redirect:/users/get/" + id + "?deleted=true";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_PATIENT')")
    @GetMapping("/editProfile")
    public ModelAndView editProfile(Principal principal,
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_PATIENT')")
    @PostMapping("/editProfile")
    public String editProfilePost(@Valid @ModelAttribute("editProfile") UserEditDTO user, BindingResult result, Principal principal) {
        userEditValidator.validate(user, result);
        User princ = userService.getUserByEmail(principal.getName());
        if (result.getErrorCount() == 0) {
            princ.setFirstName(user.getFirstName());
            userService.editUserForUser(user, princ);
            return "redirect:/users/editProfile?success=true";
        }
        return "editProfile";
    }

}

