package pl.pisarkiewicz.Visit.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.User.service.UserService;
import pl.pisarkiewicz.Visit.entity.Visit;
import pl.pisarkiewicz.Visit.service.VisitService;
import pl.pisarkiewicz.VisitHours.entity.VisitHours;

import java.security.Principal;

@Controller
@RequestMapping("/visits")
public class VisitController {
    private final UserService userService;
    private final VisitService visitService;

    public VisitController(UserService userService, VisitService visitService) {
        this.userService = userService;
        this.visitService = visitService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    @GetMapping("/list/{id}")
    public String getVisitHoursList(Principal principal,
                                    @PathVariable Integer id,
                                    Model model) {
        Pageable pageable = PageRequest.of(id - 1, 10);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRoleAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        Page<Visit> visitPage;
        if (hasUserRoleAdmin) {
            visitPage = visitService.getVisitsPageForAdmin(pageable);
        } else {
            User princ = userService.getUserByEmail(principal.getName());
            visitPage = visitService.getVisitsPageForDoctor(princ.getId(), pageable);
        }
        model.addAttribute("visitList", visitPage.getContent());
        model.addAttribute("totalPages", visitPage.getTotalPages());
        model.addAttribute("currentPage", id);
        return "visitList";
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("/book/{id}/queue/{queue}")
    public String book(Principal principal,
                       @PathVariable Long id,
                       @PathVariable Integer queue) {
        User princ = userService.getUserByEmail(principal.getName());
        if (visitService.addVisit(princ, id, queue)) {
            return "redirect:/visitHours";
        }
        return "redirect:/visitHours/" + id + "?alreadyBooked=true";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    @PostMapping("/cancel/{id}")
    public String cancelVisit(Principal principal,
                              @PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRoleAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        if (hasUserRoleAdmin) {
            visitService.cancelVisitForAdmin(id);
        } else {
            User princ = userService.getUserByEmail(principal.getName());
            visitService.cancelVisitForDoctor(princ.getId(), id);
        }
        return "redirect:/visits/list/1";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    @PostMapping("/approve/{id}")
    public String approveVisit(Principal principal,
                              @PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRoleAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        if (hasUserRoleAdmin) {
            visitService.approveVisitForAdmin(id);
        } else {
            User princ = userService.getUserByEmail(principal.getName());
            visitService.approveVisitForDoctor(princ.getId(), id);
        }
        return "redirect:/visits/list/1";
    }
}
