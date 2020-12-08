package pl.pisarkiewicz.VisitHours.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchVisitHoursDTO {
    private Long id;
    private Long doctorId;
}

