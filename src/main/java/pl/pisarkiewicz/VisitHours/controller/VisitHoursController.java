package pl.pisarkiewicz.VisitHours.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pisarkiewicz.User.service.UserService;
import pl.pisarkiewicz.VisitHours.dto.VisitHoursDTO;
import pl.pisarkiewicz.VisitHours.service.VisitHoursService;
import pl.pisarkiewicz.VisitHours.validation.AddVisitHoursValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/visitHours")
public class VisitHoursController {
    private final VisitHoursService visitHoursService;
    private final UserService userService;
    private final AddVisitHoursValidator addVisitHoursValidator;

    public VisitHoursController(VisitHoursService visitHoursService, UserService userService) {
        this.visitHoursService = visitHoursService;
        this.userService = userService;
        this.addVisitHoursValidator = new AddVisitHoursValidator();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    @GetMapping()
    public String getVisitHoursList() {
        return "visitHoursList";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    @GetMapping("/add")
    public String addVisitHours(@RequestParam(value = "errorDate", required = false) String errorDate,
                                Model model) {
        if(errorDate != null) {
            model.addAttribute("errorDate", "visitHours.error.startDate2");
        }
        model.addAttribute("doctorsList", userService.getDoctorsList());
        model.addAttribute("addVisitHours", new VisitHoursDTO());
        return "addVisitHours";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    @PostMapping("/add")
    public String addVisitHoursPost(@Valid @ModelAttribute("addVisitHours") VisitHoursDTO visitHours,
                                    BindingResult result,
                                    Principal principal,
                                    Model model) {
        addVisitHoursValidator.validate(visitHours, result);
        if (result.getErrorCount() == 0) {
            if (visitHours.getDoctorId() == null) {
                visitHours.setDoctorId(userService.getUserByEmail(principal.getName()).getId());
            }
            if (visitHoursService.hasDoctorVisitingHours(
                    visitHours.getDoctorId(),
                    visitHours.getStartDate(),
                    visitHours.getStartDate().plusMinutes(visitHours.getVisitLength() * visitHours.getVisitsCount()))) {
                return "redirect:/visitHours/add?errorDate=true";
            }
            visitHoursService.addVisitHours(visitHours);
            //return "redirect:/visitHours";
        }
        model.addAttribute("doctorsList", userService.getDoctorsList());
        return "addVisitHours";
    }
}
