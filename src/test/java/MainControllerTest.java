import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.pisarkiewicz.Global.configuration.HibernatePersistenceConfiguration;
import pl.pisarkiewicz.Global.configuration.Spring5Configuration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@ContextConfiguration(
    classes = {Spring5Configuration.class, HibernatePersistenceConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainControllerTest {
  @Autowired private WebApplicationContext wac;
  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void isMainPageAvailable() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk());
  }

  @Test
  public void testMainControllerLoginPageViewTitle() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/login"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("login"));
  }
}
