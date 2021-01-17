import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.pisarkiewicz.Global.configuration.Spring5Configuration;

import java.util.Locale;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@ContextConfiguration(classes = {Spring5Configuration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InternationalizationTest {

  @Autowired private MessageSource messageSource;

  @ParameterizedTest
  @CsvSource(
      value = {"pl:Strona główna", "en:Home page", "de:Homepage"},
      delimiter = ':')
  public void shouldPageTitleEquals(String lang, String expected) {
    Locale.setDefault(new Locale(lang));
    String title = messageSource.getMessage("page.hello", null, Locale.getDefault());
    Assertions.assertEquals(expected, title);
  }

  @Test
  public void shouldNotEqualsLoginPageTitle() {
    Locale.setDefault(new Locale("pl"));
    String title = messageSource.getMessage("page.hello", null, Locale.getDefault());
    Assertions.assertNotEquals(title, "page.login");
  }
}
