package pl.pisarkiewicz.User.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import pl.pisarkiewicz.ActivationToken.entity.ActivationToken;
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
    @JsonIgnore
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

    @OneToOne
    @JsonIgnore
    private ActivationToken activationToken;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>(0);

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient")
    @JsonManagedReference
    private Set<Visit> visit = new HashSet<>(0);

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "doctor")
    @JsonManagedReference
    private Set<VisitHours> visitHours = new HashSet<>(0);

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

