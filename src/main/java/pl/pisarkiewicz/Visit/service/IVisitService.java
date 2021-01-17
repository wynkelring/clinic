package pl.pisarkiewicz.Visit.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.Visit.entity.Visit;

import java.time.LocalDateTime;

public interface IVisitService {
  Visit getVisit(Long id);

  boolean addVisit(User user, Long visitHoursId, Integer numberInQueue);

  boolean isBookingAvailable(Long visitHoursId, Integer numberInQueue, Long userId);

  Page<Visit> getVisitsPageForAdmin(Pageable pageable);

  Page<Visit> getVisitsPageForDoctor(Long doctorId, Pageable pageable);

  Page<Visit> getVisitsPageForUser(Long userId, Pageable pageable);

  void cancelVisitForAdmin(Long visitId);

  void cancelVisitForDoctor(Long visitId, Long doctorId);

  void cancelVisitForPatient(Long visitId, Long patientId);

  void approveVisitForAdmin(Long visitId);

  void approveVisitForDoctor(Long visitId, Long doctorId);

  void approveVisitsOnEndOfMonth(LocalDateTime now);
}
