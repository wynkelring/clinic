package pl.pisarkiewicz.User.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.User.service.IUserService;

import java.util.regex.Pattern;

public class UserValidator implements Validator {
  private final IUserService userService;
  EmailValidator emailValidator = EmailValidator.getInstance();

  public UserValidator(IUserService userService) {
    this.userService = userService;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return User.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    ValidationUtils.rejectIfEmpty(errors, "firstName", "error.field.required");
    ValidationUtils.rejectIfEmpty(errors, "lastName", "error.field.required");
    ValidationUtils.rejectIfEmpty(errors, "pesel", "error.field.required");
    ValidationUtils.rejectIfEmpty(errors, "telephone", "error.field.required");
    ValidationUtils.rejectIfEmpty(errors, "email", "error.field.required");
    ValidationUtils.rejectIfEmpty(errors, "password", "error.field.required");

    if (errors.getErrorCount() == 0) {
      Pattern pattern;

      if (StringUtils.hasText(((User) o).getEmail())
          && userService.userExists(((User) o).getEmail())) {
        errors.rejectValue("email", "error.email.exists");
      }

      if (StringUtils.hasText(((User) o).getEmail())
          && !emailValidator.isValid(((User) o).getEmail())) {
        errors.rejectValue("email", "error.email.invalid");
      }

      if (((User) o).getPesel().toString().length() != 11) {
        errors.rejectValue("pesel", "register.error.pesel");
      }

      String phoneRegex = "(?<!\\w)(\\(?(\\+)?48\\)?)?[ -]?\\d{3}[ -]?\\d{3}[ -]?\\d{3}(?!\\w)";
      pattern = Pattern.compile(phoneRegex);
      if (StringUtils.hasText(((User) o).getTelephone())
          && !pattern.matcher(((User) o).getTelephone()).matches()) {
        errors.rejectValue("telephone", "register.error.telephone");
      }

      String passwordRegex =
          "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=!()])" + "(?=\\S+$).{8,20}$";
      pattern = Pattern.compile(passwordRegex);
      if (StringUtils.hasText(((User) o).getPassword())
          && !pattern.matcher(((User) o).getPassword()).matches()) {
        errors.rejectValue("password", "register.error.password");
      }
    }
  }
}
