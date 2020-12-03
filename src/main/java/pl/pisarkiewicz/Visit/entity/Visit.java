package pl.pisarkiewicz.Visit.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.VisitHours.entity.VisitHours;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@ToString
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDate;

    private boolean cancelled;

    private boolean ended;

    private String userDescription;

    private String doctorDescription;

    @ManyToOne
    private VisitHours visitHours;

    @ManyToOne
    private User patient;
}

