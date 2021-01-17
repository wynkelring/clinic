import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import pl.pisarkiewicz.Global.service.EmailService;
import pl.pisarkiewicz.Visit.entity.Visit;
import pl.pisarkiewicz.Visit.repository.VisitRepository;
import pl.pisarkiewicz.Visit.service.VisitService;
import pl.pisarkiewicz.VisitHours.service.VisitHoursService;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {
  @Mock VisitHoursService visitHoursService;
  @Mock VisitRepository visitRepository;
  @Mock EmailService emailService;
  @Mock MessageSource messageSource;
  @InjectMocks VisitService visitService;

  @Test
  public void shouldReturnGoodNumberInQueue() {
    Visit visit = new Visit();
    visit.setApproved(true);
    visit.setNumberInQueue(2);
    when(visitRepository.findById(1L)).thenReturn(Optional.of(visit));

    Assertions.assertEquals(visit.getNumberInQueue(), visitService.getVisit(1L).getNumberInQueue());
  }
}
