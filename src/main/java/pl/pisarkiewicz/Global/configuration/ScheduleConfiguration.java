package pl.pisarkiewicz.Global.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.pisarkiewicz.Visit.service.VisitService;

import java.time.LocalDateTime;
import java.util.Calendar;

@Configuration
@EnableScheduling
public class ScheduleConfiguration {
  private final VisitService visitService;

  public ScheduleConfiguration(VisitService visitService) {
    this.visitService = visitService;
  }

  @Scheduled(cron = "0 0 23 28-31 * *")
  public void myLastDayOfMonthJob() {
    System.out.println("SCHEDULE " + LocalDateTime.now());
    final Calendar c = Calendar.getInstance();
    if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE)) {
      visitService.approveVisitsOnEndOfMonth(LocalDateTime.now());
    }
  }

  @Scheduled(cron = "0 0/2 * * * *")
  public void every2MinutesJob() {
    System.out.println("SCHEDULE " + LocalDateTime.now());
    visitService.approveVisitsOnEndOfMonth(LocalDateTime.now());
  }
}
