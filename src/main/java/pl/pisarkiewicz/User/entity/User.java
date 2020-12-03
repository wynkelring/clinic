package pl.pisarkiewicz.User.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import pl.pisarkiewicz.Role.entity.Role;
import pl.pisarkiewicz.Visit.entity.Visit;
import pl.pisarkiewicz.VisitHours.entity.VisitHours;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String password;

    private boolean enabled;

    private boolean deleted = false;

    @NotNull
    @Size(min = 2, max = 30, message = "{error.field.size.regexp}")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30, message = "{error.field.size.regexp}")
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String telephone;

    @NotNull
    private Long pesel;

    @NotNull
    private String activationToken;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>(0);

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient")
    private Set<Visit> visit = new HashSet<>(0);

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "doctor")
    private Set<VisitHours> visitHours = new HashSet<>(0);

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

