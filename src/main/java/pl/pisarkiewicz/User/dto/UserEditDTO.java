package pl.pisarkiewicz.User.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserEditDTO {
  private String firstName;
  private String lastName;
  private String telephone;
  private Long pesel;
  private Set<String> roles = new HashSet<>(0);
}
