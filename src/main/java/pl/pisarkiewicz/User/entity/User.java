package pl.pisarkiewicz.User.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import pl.pisarkiewicz.Role.entity.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String password;

    private boolean enabled;

    @NotNull
    @Size(min = 2, max = 30, message = "{error.field.size.regexp}")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30, message = "{error.field.size.regexp}")
    private String lastName;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    private String telephone;

    @NotNull
    private Long pesel;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>(0);
}

