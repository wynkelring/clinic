package pl.pisarkiewicz.VisitHours.service;

import pl.pisarkiewicz.VisitHours.dto.VisitHoursDTO;

import java.time.LocalDateTime;

public interface IVisitHoursService {
    void addVisitHours(VisitHoursDTO visitHours);
    boolean hasDoctorVisitingHours(Long doctorId, LocalDateTime startDate, LocalDateTime endDate);
}
