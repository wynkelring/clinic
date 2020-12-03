package pl.pisarkiewicz.VisitHours.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.pisarkiewicz.VisitHours.dto.VisitHoursDTO;
import pl.pisarkiewicz.VisitHours.entity.VisitHours;

import java.time.LocalDateTime;

public interface IVisitHoursService {
    void addVisitHours(VisitHoursDTO visitHours);
    void cancelVisitHoursForAdmin(Long id);
    void cancelVisitHoursForDoctor(Long doctorId, Long id);
    boolean hasDoctorVisitingHours(Long doctorId, LocalDateTime startDate, LocalDateTime endDate);
    Page<VisitHours> getVisitHoursNotCancelledForAdmin(Pageable pageable);
    Page<VisitHours> getVisitHoursNotCancelledForDoctor(Long doctorId, Pageable pageable);
}
