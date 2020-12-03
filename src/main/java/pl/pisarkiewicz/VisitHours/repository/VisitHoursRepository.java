package pl.pisarkiewicz.VisitHours.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pisarkiewicz.VisitHours.entity.VisitHours;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Transactional
@Repository
public interface VisitHoursRepository extends JpaRepository<VisitHours, Long> {
    boolean existsByCancelledIsFalseAndDoctorIdAndStartDateIsBetweenOrEndDateIsBetween(Long doctor_id, LocalDateTime startDate, LocalDateTime startDate2, LocalDateTime endDate, LocalDateTime endDate2);
    //AndEndDateLessThanEqual -- , LocalDateTime before,
}
