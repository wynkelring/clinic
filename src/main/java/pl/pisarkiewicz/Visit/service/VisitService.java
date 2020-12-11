package pl.pisarkiewicz.Visit.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
import java.util.List;
import java.util.Optional;

@Service
public class VisitService implements IVisitService {
    private final VisitHoursService visitHoursService;
    private final VisitRepository visitRepository;
    private final EmailService emailService;
    private final MessageSource messageSource;

    public VisitService(VisitHoursService visitHoursService, VisitRepository visitRepository, EmailService emailService, MessageSource messageSource) {
        this.visitHoursService = visitHoursService;
        this.visitRepository = visitRepository;
        this.emailService = emailService;
        this.messageSource = messageSource;
    }

    @Override
    public Visit getVisit(Long id) {
        return visitRepository.findById(id).orElse(null);
    }

    @Override
    public boolean addVisit(User user, Long visitHoursId, Integer numberInQueue) {
        VisitHours visitHours = visitHoursService.getSingleVisitHours(visitHoursId);
        if (isBookingAvailable(visitHoursId, numberInQueue, user.getId()) &&
                !visitHours.getDoctor().getId().equals(user.getId())) {
            Visit visit = new Visit();
            visit.setNumberInQueue(numberInQueue);
            visit.setPatient(user);
            visit.setVisitHours(visitHours);
            visitRepository.save(visit);
            emailService.sendEmail(visit.getPatient().getEmail(),
                    messageSource.getMessage("email.title.book", null, LocaleContextHolder.getLocale()),
                    messageSource.getMessage("email.content.book", null, LocaleContextHolder.getLocale()) +
                            "\n" +
                            visit.getVisitHours().getDoctor().getFirstName() + " " + visit.getVisitHours().getDoctor().getFirstName() +
                            "\n" +
                            visit.getVisitHours().getStartDate().plusMinutes(visit.getVisitHours().getVisitLength() * visit.getNumberInQueue()) +
                            "\n" +
                            visit.getVisitHours().getDescription() +
                            "\n\n" +
                            "http://localhost:8080/visits/myVisits/1");
            return true;
        }
        return false;
    }

    @Override
    public boolean isBookingAvailable(Long visitHoursId, Integer numberInQueue, Long userId) {
        VisitHours visitHours = visitHoursService.getSingleVisitHours(visitHoursId);
        if (visitHours != null) {
            LocalDateTime visitDate = visitHours.getStartDate().plusMinutes(visitHours.getVisitLength() * (numberInQueue - 1));
            return (!visitRepository.existsByCancelledIsFalseAndVisitHoursIdAndNumberInQueue(visitHoursId, numberInQueue) &&
                    visitDate.isAfter(LocalDateTime.now()) &&
                    !visitRepository.existsByCancelledIsFalseAndVisitHoursIdAndPatientId(visitHoursId, userId));
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
    public Page<Visit> getVisitsPageForUser(Long userId, Pageable pageable) {
        return visitRepository.findAllByPatientIdOrderByVisitHoursStartDate(userId, pageable);
    }

    @Override
    public void cancelVisitForAdmin(Long visitId) {
        Optional<Visit> optVisit = visitRepository.findById(visitId);
        optVisit.ifPresent(visit -> {
            visit.setCancelled(true);
            visitRepository.save(visit);
            this.sendCancelledEmail(visit);
        });
    }

    @Override
    public void cancelVisitForDoctor(Long visitId, Long doctorId) {
        Optional<Visit> optVisit = visitRepository.findByIdAndVisitHoursDoctorIdAndCancelledIsFalse(visitId, doctorId);
        optVisit.ifPresent(visit -> {
            visit.setCancelled(true);
            visitRepository.save(visit);
            this.sendCancelledEmail(visit);
        });
    }

    @Override
    public void cancelVisitForPatient(Long visitId, Long patientId) {
        Optional<Visit> optVisit = visitRepository.findByIdAndPatientIdAndCancelledIsFalse(visitId, patientId);
        optVisit.ifPresent(visit -> {
            visit.setCancelled(true);
            visitRepository.save(visit);
            this.sendCancelledEmail(visit);
        });
    }

    @Override
    public void approveVisitForAdmin(Long visitId) {
        Optional<Visit> optVisit = visitRepository.findById(visitId);
        optVisit.ifPresent(visit -> {
            visit.setApproved(true);
            visitRepository.save(visit);
            this.sendApprovedEmail(visit);
        });
    }

    @Override
    public void approveVisitForDoctor(Long visitId, Long doctorId) {
        Optional<Visit> optVisit = visitRepository.findByIdAndVisitHoursDoctorIdAndCancelledIsFalse(visitId, doctorId);
        optVisit.ifPresent(visit -> {
            visit.setApproved(true);
            visitRepository.save(visit);
            this.sendApprovedEmail(visit);
        });
    }

    @Override
    public void approveVisitsOnEndOfMonth(LocalDateTime now) {
        List<Visit> visits = visitRepository.findAllByCancelledIsFalseAndApprovedIsFalseAndVisitHoursEndDateBefore(LocalDateTime.now());
        visits.forEach(visit -> {
            visit.setApproved(true);
            visitRepository.save(visit);
            System.out.println(visit.getId() + " APPROVED");
        });
    }

    private void sendCancelledEmail(Visit visit) {
        emailService.sendEmail(visit.getPatient().getEmail(),
                messageSource.getMessage("email.title.cancelled", null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("email.content.cancelled", null, LocaleContextHolder.getLocale()) +
                        "\n" +
                        visit.getVisitHours().getDoctor().getFirstName() + " " + visit.getVisitHours().getDoctor().getFirstName() +
                        "\n" +
                        visit.getVisitHours().getStartDate().plusMinutes(visit.getVisitHours().getVisitLength() * visit.getNumberInQueue()) +
                        "\n" +
                        visit.getVisitHours().getDescription());
    }

    private void sendApprovedEmail(Visit visit) {
        emailService.sendEmail(visit.getPatient().getEmail(),
                messageSource.getMessage("email.title.approved", null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("email.content.approved", null, LocaleContextHolder.getLocale()) +
                        "\n" +
                        visit.getVisitHours().getDoctor().getFirstName() + " " + visit.getVisitHours().getDoctor().getFirstName() +
                        "\n" +
                        visit.getVisitHours().getStartDate().plusMinutes(visit.getVisitHours().getVisitLength() * visit.getNumberInQueue()) +
                        "\n" +
                        visit.getVisitHours().getDescription() +
                        "\n\n" +
                        "http://localhost:8080/visits/invoice/" + visit.getId());
    }
}
