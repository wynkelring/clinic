package pl.pisarkiewicz.Visit.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pisarkiewicz.Global.service.EmailService;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.Visit.entity.Visit;
import pl.pisarkiewicz.Visit.repository.VisitRepository;
import pl.pisarkiewicz.VisitHours.entity.VisitHours;
import pl.pisarkiewicz.VisitHours.service.VisitHoursService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VisitService implements IVisitService {
    private final VisitHoursService visitHoursService;
    private final VisitRepository visitRepository;
    private final EmailService emailService;

    public VisitService(VisitHoursService visitHoursService, VisitRepository visitRepository, EmailService emailService) {
        this.visitHoursService = visitHoursService;
        this.visitRepository = visitRepository;
        this.emailService = emailService;
    }

    @Override
    public boolean addVisit(User user, Long visitHoursId, Integer numberInQueue) {
        VisitHours visitHours = visitHoursService.getSingleVisitHours(visitHoursId);
        if (isBookingAvailable(visitHoursId, numberInQueue) && !visitHours.getDoctor().getId().equals(user.getId())) {
            Visit visit = new Visit();
            visit.setNumberInQueue(numberInQueue);
            visit.setPatient(user);
            visit.setVisitHours(visitHours);
            visitRepository.save(visit);
            /*emailService.sendEmail(visit.getPatient().getEmail(),
                        "Zarezerwowano wizytę",
                        "Zarezerwowano wizytę u doktora " +
                                visit.getVisitHours().getDoctor().getFirstName() + " " + visit.getVisitHours().getDoctor().getFirstName() +
                                " w dniu " + visit.getVisitHours().getStartDate().plusMinutes(visit.getVisitHours().getVisitLength() * visit.getNumberInQueue()));*/
            return true;
        }
        return false;
    }

    @Override
    public boolean isBookingAvailable(Long visitHoursId, Integer numberInQueue) {
        VisitHours visitHours = visitHoursService.getSingleVisitHours(visitHoursId);
        if (visitHours != null) {
            LocalDateTime visitDate = visitHours.getStartDate().plusMinutes(visitHours.getVisitLength() * (numberInQueue - 1));
            return (!visitRepository.existsByCancelledIsFalseAndVisitHoursIdAndNumberInQueue(visitHoursId, numberInQueue) &&
                    visitDate.isAfter(LocalDateTime.now()));
        }
        return false;
    }

    @Override
    public Page<Visit> getVisitsPageForAdmin(Pageable pageable) {
        return visitRepository.findAllByOrderByVisitHoursStartDate(pageable);
    }

    @Override
    public Page<Visit> getVisitsPageForDoctor(Long doctorId, Pageable pageable) {
        return visitRepository.findAllByVisitHoursDoctorIdOrderByVisitHoursStartDate(doctorId, pageable);
    }

    @Override
    public void cancelVisitForAdmin(Long visitId) {
        Optional<Visit> optVisit = visitRepository.findById(visitId);
        optVisit.ifPresent(visit -> {
            visit.setCancelled(true);
            visitRepository.save(visit);
        });
    }

    @Override
    public void cancelVisitForDoctor(Long visitId, Long doctorId) {
        Optional<Visit> optVisit = visitRepository.findByIdAndVisitHoursDoctorIdAndCancelledIsFalse(visitId, doctorId);
        optVisit.ifPresent(visit -> {
            visit.setCancelled(true);
            visitRepository.save(visit);
        });
    }

    @Override
    public void approveVisitForAdmin(Long visitId) {
        Optional<Visit> optVisit = visitRepository.findById(visitId);
        optVisit.ifPresent(visit -> {
            visit.setApproved(true);
            visitRepository.save(visit);
        });
    }

    @Override
    public void approveVisitForDoctor(Long visitId, Long doctorId) {
        Optional<Visit> optVisit = visitRepository.findByIdAndVisitHoursDoctorIdAndCancelledIsFalse(visitId, doctorId);
        optVisit.ifPresent(visit -> {
            visit.setApproved(true);
            visitRepository.save(visit);
        });
    }
}
