package pl.pisarkiewicz.VisitHours.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.pisarkiewicz.User.service.UserService;
import pl.pisarkiewicz.VisitHours.dto.VisitHoursDTO;
import pl.pisarkiewicz.VisitHours.entity.VisitHours;
import pl.pisarkiewicz.VisitHours.service.VisitHoursService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/visitHours")
public class VisitHoursController {
    private final VisitHoursService visitHoursService;
    private final UserService userService;

    public VisitHoursController(VisitHoursService visitHoursService, UserService userService) {
        this.visitHoursService = visitHoursService;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    @GetMapping()
    public String getVisitHoursList() {
        return "visitHoursList";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    @GetMapping("/add")
    public String addVisitHours(Model model) {
        model.addAttribute("doctorsList", userService.getDoctorsList());
        model.addAttribute("addVisitHours", new VisitHoursDTO());
        return "addVisitHours";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    @PostMapping("/add")
    public String addVisitHoursPost(@Valid @ModelAttribute("addVisitHours") VisitHoursDTO visitHours,
                                    BindingResult result,
                                    Principal principal) {
        System.out.println(visitHours.getDoctorId());
        if (visitHours.getDoctorId() == null) {
           visitHours.setDoctorId(userService.getUserByEmail(principal.getName()).getId());
        }
        visitHoursService.addVisitHours(visitHours);
        return "redirect:/visitHours";
    }
}
