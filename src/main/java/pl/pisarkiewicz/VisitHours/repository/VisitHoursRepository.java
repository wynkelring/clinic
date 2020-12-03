package pl.pisarkiewicz.VisitHours.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pisarkiewicz.VisitHours.entity.VisitHours;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface VisitHoursRepository extends JpaRepository<VisitHours, Long> {
}
