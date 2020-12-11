package pl.pisarkiewicz.Visit.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    private Integer numberInQueue;

    private boolean cancelled;

    private boolean approved;

    @ManyToOne
    @JsonBackReference
    private VisitHours visitHours;

    @ManyToOne
    @JsonBackReference
    private User patient;
}

