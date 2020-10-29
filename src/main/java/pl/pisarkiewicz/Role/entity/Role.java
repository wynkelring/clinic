package pl.pisarkiewicz.Role.entity;

import lombok.Getter;
import lombok.Setter;
import pl.pisarkiewicz.User.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String role;
}
