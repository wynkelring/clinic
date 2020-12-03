package pl.pisarkiewicz.VisitHours.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pisarkiewicz.User.service.UserService;
import pl.pisarkiewicz.VisitHours.dto.VisitHoursDTO;
import pl.pisarkiewicz.VisitHours.entity.VisitHours;
import pl.pisarkiewicz.VisitHours.repository.VisitHoursRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VisitHoursService implements IVisitHoursService {
    private final VisitHoursRepository visitHoursRepository;
    private final UserService userService;

    public VisitHoursService(VisitHoursRepository visitHoursRepository, UserService userService) {
        this.visitHoursRepository = visitHoursRepository;
        this.userService = userService;
    }

    @Override
    public void addVisitHours(VisitHoursDTO visitHoursDTO) {
        VisitHours visitHours = new VisitHours();
        visitHours.setStartDate(visitHoursDTO.getStartDate());
        visitHours.setVisitCost(visitHoursDTO.getVisitCost());
        visitHours.setVisitLength(visitHoursDTO.getVisitLength());
        visitHours.setVisitsCount(visitHoursDTO.getVisitsCount());
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
            visitHoursRepository.save(hours);
        });
    }

    @Override
    public void cancelVisitHoursForDoctor(Long doctorId, Long id) {
        Optional<VisitHours> visitHours = visitHoursRepository.findByIdAndDoctorId(id, doctorId);
        visitHours.ifPresent(hours -> {
            hours.setCancelled(true);
            visitHoursRepository.save(hours);
        });
    }

    @Override
    public boolean hasDoctorVisitingHours(Long doctorId, LocalDateTime startDate, LocalDateTime endDate) {
        return visitHoursRepository.existsByCancelledIsFalseAndDoctorIdAndStartDateIsBetweenOrEndDateIsBetween(
                doctorId,
                startDate,
                endDate,
                startDate,
                endDate);
    }

    @Override
    public Page<VisitHours> getVisitHoursNotCancelledForAdmin(Pageable pageable) {
        return visitHoursRepository.findAllByCancelledIsFalseOrderByStartDateDesc(pageable);
    }

    @Override
    public Page<VisitHours> getVisitHoursNotCancelledForDoctor(Long doctorId, Pageable pageable) {
        return visitHoursRepository.findAllByCancelledIsFalseAndDoctorIdOrderByStartDateDesc(doctorId, pageable);
    }
}
