package pl.pisarkiewicz.User.entity;

import lombok.Getter;
import lombok.Setter;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    @Size(min = 2, max = 30, message = "{error.field.size.regexp}")
    private String login;

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
    private String email;

    @NotNull
    @Size(min = 9, max = 9, message = "{error.field.size.regexp}")
    private String telephone;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>(0);
}

