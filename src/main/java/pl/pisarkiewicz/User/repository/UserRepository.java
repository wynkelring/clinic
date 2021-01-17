package pl.pisarkiewicz.User.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pisarkiewicz.Role.entity.Role;
import pl.pisarkiewicz.User.entity.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByEmail(String email);

  Optional<User> findByIdAndIdIsNot(Long id, Long userId);

  Optional<User> findByEmail(String email);

  Optional<User> findByActivationTokenTokenAndDeletedIsFalse(String token);

  Page<User> findAllByIdIsNot(Long id, Pageable pageable);

  List<User> findAllByRoles(Role role);
}
