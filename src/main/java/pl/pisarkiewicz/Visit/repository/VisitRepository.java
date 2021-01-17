package pl.pisarkiewicz.Visit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pisarkiewicz.Visit.entity.Visit;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
  Optional<Visit> findByIdAndVisitHoursDoctorIdAndCancelledIsFalse(Long id, Long doctorId);

  Optional<Visit> findByIdAndPatientIdAndCancelledIsFalse(Long id, Long patientId);

  Page<Visit> findAllByOrderByVisitHoursStartDate(Pageable pageable);

  Page<Visit> findAllByVisitHoursDoctorIdOrderByVisitHoursStartDate(
      Long doctorId, Pageable pageable);

  Page<Visit> findAllByPatientIdOrderByVisitHoursStartDate(Long patientId, Pageable pageable);

  List<Visit> findAllByCancelledIsFalseAndApprovedIsFalseAndVisitHoursEndDateBefore(
      LocalDateTime lastDayOfMonth);

  boolean existsByCancelledIsFalseAndVisitHoursIdAndNumberInQueue(
      Long visitHoursId, Integer numberInQueue);

  boolean existsByCancelledIsFalseAndVisitHoursIdAndPatientId(Long visitHoursId, Long patientId);
}
