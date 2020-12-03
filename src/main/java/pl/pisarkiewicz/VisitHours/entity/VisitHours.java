package pl.pisarkiewicz.VisitHours.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.Visit.entity.Visit;

import javax.persistence.*;
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

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer visitsCount;

    private Integer visitLength;

    private Double visitCost;
    
    private boolean cancelled;

    @OneToMany(mappedBy = "visitHours")
    private Set<Visit> visits;

    @ManyToOne
    private User doctor;
}

