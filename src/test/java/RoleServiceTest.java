import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pisarkiewicz.Role.entity.Role;
import pl.pisarkiewicz.Role.repository.RoleRepository;
import pl.pisarkiewicz.Role.service.RoleService;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
  @Mock RoleRepository roleRepository;
  @InjectMocks RoleService roleService;

  @Test
  public void shouldReturnGoodNumberInQueue() {
    Role role = new Role();
    role.setRole("NOWA_ROLA");

    when(roleRepository.findByRole(role.getRole())).thenReturn(Optional.of(role));

    Assertions.assertEquals(role.getRole(), roleService.getByRoleName(role.getRole()).getRole());
  }
}
