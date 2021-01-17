import org.junit.jupiter.api.Test;
import pl.pisarkiewicz.User.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserEntityTest {

  @Test
  void shouldUserEquals() {
    User user = new User();
    user.setFirstName("Kacper");
    user.setLastName("Pisarkiewicz");

    assertEquals("Kacper Pisarkiewicz", user.toString());
  }
}
