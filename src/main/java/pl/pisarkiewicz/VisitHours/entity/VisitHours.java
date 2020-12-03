package pl.pisarkiewicz.VisitHours.entity;

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

@Entity
@Table
@Getter
@Setter
@ToString
public class VisitHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    @NotNull
    private Integer visitsCount;

    @NotNull
    private Integer visitLength;

    @NotNull
    private Double visitCost;

    private boolean cancelled = false;

    @OneToMany(mappedBy = "visitHours")
    private Set<Visit> visits;

    @ManyToOne
    private User doctor;
}

