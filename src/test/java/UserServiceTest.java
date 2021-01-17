import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.pisarkiewicz.Global.configuration.HibernatePersistenceConfiguration;
import pl.pisarkiewicz.Global.configuration.Spring5Configuration;
import pl.pisarkiewicz.User.dto.UserEditDTO;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.User.service.UserService;

import javax.validation.ConstraintViolationException;

import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@ContextConfiguration(
    classes = {Spring5Configuration.class, HibernatePersistenceConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
  User mockitoUser;
  @Autowired UserService userService;

  @BeforeEach
  public void setup() {
    mockitoUser = new User();
    mockitoUser.setFirstName("Kacper");
    mockitoUser.setLastName("Pisarkiewicz");
    mockitoUser.setEmail("niematakiegomaila@email.com");
  }

  @Test
  public void shouldGetUserByEmailReturnKacper() {
    userService = mock(UserService.class);

    Mockito.when(userService.getUserByEmail("admin@mc.pl")).thenReturn(mockitoUser);
    String testName = userService.getUserByEmail("admin@mc.pl").toString();

    Assertions.assertEquals("Kacper Pisarkiewicz", testName);
  }

  @Test
  public void shouldGetUserByEmailReturnNull() {
    User user = userService.getUserByEmail("niematakiegomajla");
    Assertions.assertNull(user);
  }

  @Test
  public void shouldThrowExceptionDuringAddUserCausedOfNullPeselAndTelephone() {
    UserEditDTO userEditDTO = new UserEditDTO();
    userEditDTO.setLastName("NIEPisarkiewicz");
    mockitoUser.setPassword("costam");
    Assertions.assertThrows(
        ConstraintViolationException.class, () -> userService.addUser(mockitoUser));
  }
}
