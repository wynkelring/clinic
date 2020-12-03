package pl.pisarkiewicz.VisitHours.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.User.service.UserService;
import pl.pisarkiewicz.VisitHours.dto.VisitHoursDTO;
import pl.pisarkiewicz.VisitHours.entity.VisitHours;
import pl.pisarkiewicz.VisitHours.service.VisitHoursService;
import pl.pisarkiewicz.VisitHours.validation.AddVisitHoursValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

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
    @GetMapping("/list/{id}")
    public String getVisitHoursList(Principal principal,
                              @PathVariable Integer id,
                              Model model) {
        Pageable pageable = PageRequest.of(id - 1, 1);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRoleAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        Page<VisitHours> visitHourPage = null;
        if (hasUserRoleAdmin) {
            visitHourPage = visitHoursService.getVisitHoursNotCancelledForAdmin(pageable);
        } else {
            User princ = userService.getUserByEmail(principal.getName());
            visitHourPage = visitHoursService.getVisitHoursNotCancelledForDoctor(princ.getId(), pageable);
        }
        model.addAttribute("visitHoursList", visitHourPage.getContent());
        model.addAttribute("totalPages", visitHourPage.getTotalPages());
        model.addAttribute("currentPage", id);
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
            return "redirect:/visitHours/list/1";
        }
        model.addAttribute("doctorsList", userService.getDoctorsList());
        return "addVisitHours";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    @PostMapping("/cancel/{id}")
    public String cancelVisitHoursPost(@PathVariable Long id,
                                       Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRoleAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        if (hasUserRoleAdmin) {
            visitHoursService.cancelVisitHoursForAdmin(id);
        } else {
            User princ = userService.getUserByEmail(principal.getName());
            visitHoursService.cancelVisitHoursForDoctor(princ.getId(), id);
        }
        return "redirect:/visitHours/list/1";
    }
}
