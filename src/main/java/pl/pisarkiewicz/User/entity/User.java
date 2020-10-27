package pl.pisarkiewicz.User.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @Size(min = 2, max = 30, message = "{error.field.size}")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30, message = "{error.field.size}")
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 9, max = 9, message = "{error.field.size}")
    private String telephone;
}

