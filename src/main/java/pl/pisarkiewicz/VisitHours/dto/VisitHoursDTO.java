package pl.pisarkiewicz.VisitHours.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.Visit.entity.Visit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

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
    private boolean cancelled = false;
    private Long doctorId;
}

