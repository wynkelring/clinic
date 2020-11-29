package pl.pisarkiewicz.User.validator;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.pisarkiewicz.User.dto.UserEditDTO;

import java.util.regex.Pattern;

public class UserEditValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserEditDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "firstName", "error.field.required");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "error.field.required");
        ValidationUtils.rejectIfEmpty(errors, "pesel", "error.field.required");
        ValidationUtils.rejectIfEmpty(errors, "telephone", "error.field.required");

        if (errors.getErrorCount() == 0) {
            Pattern pattern;

            if (((UserEditDTO) o).getFirstName().length() < 2) {
                errors.rejectValue("firstName", "error.field.size.regexp");
            }

            if (((UserEditDTO) o).getLastName().length() < 2) {
                errors.rejectValue("lastName", "error.field.size.regexp");
            }

            if (((UserEditDTO) o).getPesel().toString().length() != 11) {
                errors.rejectValue("pesel", "editProfile.error.pesel");
            }

            String phoneRegex = "(?<!\\w)(\\(?(\\+)?48\\)?)?[ -]?\\d{3}[ -]?\\d{3}[ -]?\\d{3}(?!\\w)";
            pattern = Pattern.compile(phoneRegex);
            if (StringUtils.hasText(((UserEditDTO) o).getTelephone()) && !pattern.matcher(((UserEditDTO) o).getTelephone()).matches()) {
                errors.rejectValue("telephone", "editProfile.error.telephone");
            }
        }
    }
}
