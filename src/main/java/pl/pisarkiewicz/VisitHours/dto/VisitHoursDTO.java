package pl.pisarkiewicz.VisitHours.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class VisitHoursDTO {
    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;
    private Integer visitsCount;
    private Integer visitLength;
    private Double visitCost;
    private String description;
    private boolean cancelled = false;
    private Long doctorId;
}

