package pl.pisarkiewicz.User.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.pisarkiewicz.User.entity.User;

public class UserValidator implements Validator {
    EmailValidator emailValidator = EmailValidator.getInstance();

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "firstName", "error.field.required");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "error.field.required");
        ValidationUtils.rejectIfEmpty(errors, "telephone", "error.field.required");
        ValidationUtils.rejectIfEmpty(errors, "email", "error.field.required");

        if (errors.getErrorCount() == 0) {
            if (StringUtils.hasText(((User) o).getEmail()) && !emailValidator.isValid(((User) o).getEmail())) {
                errors.rejectValue("email", "error.email.invalid");
            }
        }
    }
}
