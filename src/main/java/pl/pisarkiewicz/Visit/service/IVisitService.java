package pl.pisarkiewicz.Visit.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.Visit.entity.Visit;

public interface IVisitService {
    boolean addVisit(User user, Long visitHoursId, Integer numberInQueue);
    boolean isBookingAvailable(Long visitHoursId, Integer numberInQueue);
    Page<Visit> getVisitsPageForAdmin(Pageable pageable);
    Page<Visit> getVisitsPageForDoctor(Long doctorId, Pageable pageable);
    void cancelVisitForAdmin(Long visitId);
    void cancelVisitForDoctor(Long visitId, Long doctorId);
    void approveVisitForAdmin(Long visitId);
    void approveVisitForDoctor(Long visitId, Long doctorId);
}
