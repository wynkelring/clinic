import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import pl.pisarkiewicz.Global.service.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
  @Rule static GreenMail greenMail = new GreenMail(ServerSetup.SMTP);

  @Autowired EmailService emailService;

  @BeforeEach
  public void startMailServer() {
    greenMail.start();
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost("127.0.0.1");
    javaMailSender.setPort(25);
    emailService = new EmailService(javaMailSender);
  }

  @AfterEach
  public void stopMailServer() {
    greenMail.stop();
  }

  @Test
  public void shouldSendEmail() throws MessagingException, IOException {
    // Given
    String to = "v1nKeSports@gmail.com";
    String title = "Title";
    String content = "Content";
    // When
    emailService.sendEmail(to, title, content);
    // Then
    assertTrue(greenMail.waitForIncomingEmail(5000, 1));
    MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
    assertEquals(receivedMessages.length, 1);

    MimeMessage receivedMessage = receivedMessages[0];
    assertEquals(receivedMessage.getAllRecipients()[0].toString(), to);
    assertEquals(receivedMessage.getSubject(), title);
    assertTrue(receivedMessage.getContent().toString().contains(content));
  }
}
