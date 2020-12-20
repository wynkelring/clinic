package pl.pisarkiewicz.VisitHours.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pisarkiewicz.Global.service.EmailService;
import pl.pisarkiewicz.User.service.UserService;
import pl.pisarkiewicz.Visit.repository.VisitRepository;
import pl.pisarkiewicz.VisitHours.dto.VisitHoursDTO;
import pl.pisarkiewicz.VisitHours.entity.VisitHours;
import pl.pisarkiewicz.VisitHours.repository.VisitHoursRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VisitHoursService implements IVisitHoursService {
    private final VisitHoursRepository visitHoursRepository;
    private final VisitRepository visitRepository;
    private final UserService userService;
    private final EmailService emailService;

    public VisitHoursService(VisitHoursRepository visitHoursRepository, VisitRepository visitRepository, UserService userService, EmailService emailService) {
        this.visitHoursRepository = visitHoursRepository;
        this.visitRepository = visitRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public VisitHours getSingleVisitHours(Long id) {
        Optional<VisitHours> visitHours = visitHoursRepository.findById(id);
        return visitHours.orElse(null);
    }

    @Override
    public void addVisitHours(VisitHoursDTO visitHoursDTO) {
        VisitHours visitHours = new VisitHours();
        visitHours.setStartDate(visitHoursDTO.getStartDate());
        visitHours.setVisitCost(visitHoursDTO.getVisitCost());
        visitHours.setVisitLength(visitHoursDTO.getVisitLength());
        visitHours.setVisitsCount(visitHoursDTO.getVisitsCount());
        visitHours.setDescription(visitHoursDTO.getDescription());
        visitHours.setDoctor(userService.getUser(visitHoursDTO.getDoctorId()));
        visitHours.setEndDate(
                visitHours.getStartDate().plusMinutes(
                        visitHours.getVisitLength() * visitHours.getVisitsCount()));
        visitHoursRepository.save(visitHours);
    }

    @Override
    public void cancelVisitHoursForAdmin(Long id) {
        Optional<VisitHours> visitHours = visitHoursRepository.findById(id);
        visitHours.ifPresent(hours -> {
            hours.setCancelled(true);
            hours.getVisits().forEach(visit -> {
                visit.setCancelled(true);
                visitRepository.save(visit);
                emailService.sendEmail(visit.getPatient().getEmail(),
                        "Anulowano wizytę",
                        "Wizyta u doktora " +
                                visit.getVisitHours().getDoctor().getFirstName() + " " + visit.getVisitHours().getDoctor().getFirstName() +
                        " w dniu " + visit.getVisitHours().getStartDate().plusMinutes(visit.getVisitHours().getVisitLength() * visit.getNumberInQueue()) +
                        " została anulowana");
            });
            visitHoursRepository.save(hours);
        });
    }

    @Override
    public void cancelVisitHoursForDoctor(Long doctorId, Long id) {
        Optional<VisitHours> visitHours = visitHoursRepository.findByIdAndDoctorId(id, doctorId);
        visitHours.ifPresent(hours -> {
            hours.setCancelled(true);
            hours.getVisits().forEach(visit -> {
                visit.setCancelled(true);
                visitRepository.save(visit);
                emailService.sendEmail(visit.getPatient().getEmail(),
                        "Anulowano wizytę",
                        "Wizyta u doktora " +
                                visit.getVisitHours().getDoctor().getFirstName() + " " + visit.getVisitHours().getDoctor().getFirstName() +
                                " w dniu " + visit.getVisitHours().getStartDate().plusMinutes(visit.getVisitHours().getVisitLength() * visit.getNumberInQueue()) +
                                " została anulowana");
            });
            visitHoursRepository.save(hours);
        });
    }

    @Override
    public boolean hasDoctorVisitingHours(Long doctorId, LocalDateTime startDate, LocalDateTime endDate) {
        return visitHoursRepository.findCountByDoctorIdAndStartDateAndEndDate(doctorId, startDate, endDate) > 0;
    }

    @Override
    public Page<VisitHours> getVisitHoursNotCancelledForAdmin(Pageable pageable) {
        return visitHoursRepository.findAllByCancelledIsFalseOrderByStartDateDesc(pageable);
    }

    @Override
    public Page<VisitHours> getVisitHoursNotCancelledForDoctor(Long doctorId, Pageable pageable) {
        return visitHoursRepository.findAllByCancelledIsFalseAndDoctorIdOrderByStartDateDesc(doctorId, pageable);
    }

    @Override
    public List<VisitHours> getVisitHoursNotCancelledByDoctor(Long doctorId) {
        return visitHoursRepository.findAllByCancelledIsFalseAndDoctorIdAndEndDateAfterOrderByStartDateAsc(doctorId, LocalDateTime.now());
    }
}
