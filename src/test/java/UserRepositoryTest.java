import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.pisarkiewicz.Global.configuration.HibernatePersistenceConfiguration;
import pl.pisarkiewicz.Global.configuration.Spring5Configuration;
import pl.pisarkiewicz.User.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@ContextConfiguration(
    classes = {Spring5Configuration.class, HibernatePersistenceConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {
  @Autowired UserRepository userRepository;

  @Test
  public void nullCheck() {
    assertNotNull(userRepository);
  }

  @ParameterizedTest
  @ValueSource(strings = {"admin@mc.pl", "doctor@mc.pl"})
  void userExists(String mail) {
    Boolean userExists = userRepository.existsByEmail(mail);
    assertEquals(userExists, true);
  }
}
