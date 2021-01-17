package pl.pisarkiewicz.Visit.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pisarkiewicz.Global.service.PdfService;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.User.service.UserService;
import pl.pisarkiewicz.Visit.entity.Visit;
import pl.pisarkiewicz.Visit.service.VisitService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/visits")
public class VisitController {
  private final UserService userService;
  private final VisitService visitService;
  private final PdfService pdfService;

  public VisitController(
      UserService userService, VisitService visitService, PdfService pdfService) {
    this.userService = userService;
    this.visitService = visitService;
    this.pdfService = pdfService;
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_PATIENT')")
  @GetMapping("/invoice/{id}")
  public void getInvoice(Principal principal, @PathVariable Long id, HttpServletResponse response)
      throws IOException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User princ = userService.getUserByEmail(principal.getName());
    Visit visit = visitService.getVisit(id);
    if (visit != null
        && visit.isApproved()
        && (visit.getPatient().getId().equals(princ.getId())
            || visit.getVisitHours().getDoctor().getId().equals(princ.getId())
            || authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")))) {
      pdfService.generateInvoice(visit, response);
    } else {
      response.sendRedirect("/visits/myVisits/1");
    }
  }

  @PreAuthorize("hasRole('ROLE_PATIENT')")
  @GetMapping("/myVisits/{id}")
  public String getMyVisits(Principal principal, @PathVariable Integer id, Model model) {
    Pageable pageable = PageRequest.of(id - 1, 10);
    User princ = userService.getUserByEmail(principal.getName());
    Page<Visit> visitPage = visitService.getVisitsPageForUser(princ.getId(), pageable);
    model.addAttribute("visitList", visitPage.getContent());
    model.addAttribute("totalPages", visitPage.getTotalPages());
    model.addAttribute("currentPage", id);
    model.addAttribute("ldtNow", LocalDateTime.now());
    return "myVisits";
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
  @GetMapping("/list/{id}")
  public String getVisitHoursList(Principal principal, @PathVariable Integer id, Model model) {
    Pageable pageable = PageRequest.of(id - 1, 10);
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    boolean hasUserRoleAdmin =
        authentication.getAuthorities().stream()
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
  public String book(Principal principal, @PathVariable Long id, @PathVariable Integer queue) {
    User princ = userService.getUserByEmail(principal.getName());
    if (visitService.addVisit(princ, id, queue)) {
      return "redirect:/visits/myVisits/1";
    }
    return "redirect:/visitHours/" + id + "?notAvailable=true";
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_PATIENT')")
  @PostMapping("/cancel/{id}")
  public String cancelVisit(Principal principal, @PathVariable Long id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    boolean hasUserRoleAdmin =
        authentication.getAuthorities().stream()
            .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
    boolean hasUserRoleDoctor =
        authentication.getAuthorities().stream()
            .anyMatch(r -> r.getAuthority().equals("ROLE_DOCTOR"));
    User princ = userService.getUserByEmail(principal.getName());
    if (hasUserRoleAdmin) {
      visitService.cancelVisitForAdmin(id);
    } else if (hasUserRoleDoctor) {
      visitService.cancelVisitForDoctor(id, princ.getId());
    } else {
      visitService.cancelVisitForPatient(id, princ.getId());
    }
    return "redirect:/visits/list/1";
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
  @PostMapping("/approve/{id}")
  public String approveVisit(Principal principal, @PathVariable Long id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    boolean hasUserRoleAdmin =
        authentication.getAuthorities().stream()
            .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
    if (hasUserRoleAdmin) {
      visitService.approveVisitForAdmin(id);
    } else {
      User princ = userService.getUserByEmail(principal.getName());
      visitService.approveVisitForDoctor(id, princ.getId());
    }
    return "redirect:/visits/list/1";
  }
}
