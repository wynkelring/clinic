package pl.pisarkiewicz.ActivationToken.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pisarkiewicz.ActivationToken.entity.ActivationToken;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Long> {
}
