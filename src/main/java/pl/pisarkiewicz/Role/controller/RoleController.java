package pl.pisarkiewicz.Role.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pisarkiewicz.Role.entity.Role;
import pl.pisarkiewicz.Role.service.IRoleService;

@Controller
public class RoleController {

    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public String showUserRole(Model model) {
        model.addAttribute("userRole", new Role());
        return "addRole";
    }

    @PostMapping("/roles/add")
    public String addRole(@ModelAttribute("userRole") Role role, BindingResult result) {
        roleService.addRole(role);
        return "redirect:/";
    }
}
