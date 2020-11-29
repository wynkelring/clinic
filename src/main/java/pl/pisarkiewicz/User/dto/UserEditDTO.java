package pl.pisarkiewicz.User.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditDTO {
    private String firstName;
    private String lastName;
    private String telephone;
    private Long pesel;
}
