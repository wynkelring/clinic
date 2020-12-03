package pl.pisarkiewicz.VisitHours.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pisarkiewicz.VisitHours.entity.VisitHours;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@Repository
public interface VisitHoursRepository extends JpaRepository<VisitHours, Long> {
    Optional<VisitHours> findByIdAndDoctorId(Long id, Long doctorId);
    boolean existsByCancelledIsFalseAndDoctorIdAndStartDateIsBetweenOrEndDateIsBetween(Long doctor_id, LocalDateTime startDate, LocalDateTime startDate2, LocalDateTime endDate, LocalDateTime endDate2);
    Page<VisitHours> findAllByCancelledIsFalseAndDoctorIdOrderByStartDateDesc(Long doctorId, Pageable pageable);
    Page<VisitHours> findAllByCancelledIsFalseOrderByStartDateDesc(Pageable pageable);
}
