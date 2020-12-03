package pl.pisarkiewicz.Visit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pisarkiewicz.Visit.entity.Visit;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
}
