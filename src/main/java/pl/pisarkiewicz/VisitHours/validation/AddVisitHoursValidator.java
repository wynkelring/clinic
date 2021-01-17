package pl.pisarkiewicz.VisitHours.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.pisarkiewicz.VisitHours.dto.VisitHoursDTO;

import java.time.LocalDateTime;

public class AddVisitHoursValidator implements Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return VisitHoursDTO.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    ValidationUtils.rejectIfEmpty(errors, "startDate", "error.field.required");
    ValidationUtils.rejectIfEmpty(errors, "visitLength", "error.field.required");
    ValidationUtils.rejectIfEmpty(errors, "visitsCount", "error.field.required");
    ValidationUtils.rejectIfEmpty(errors, "visitCost", "error.field.required");
    ValidationUtils.rejectIfEmpty(errors, "description", "error.field.required");

    if (errors.getErrorCount() == 0) {
      if (((VisitHoursDTO) o).getStartDate().isBefore(LocalDateTime.now())) {
        errors.rejectValue("startDate", "visitHours.error.startDate1");
      }

      if (((VisitHoursDTO) o).getVisitsCount() < 1) {
        errors.rejectValue("visitsCount", "visitHours.error.visitsCount");
      }

      if (((VisitHoursDTO) o).getVisitCost() < 0) {
        errors.rejectValue("visitCost", "visitHours.error.visitCost");
      }

      if (((VisitHoursDTO) o).getVisitLength() != 15
          && ((VisitHoursDTO) o).getVisitLength() != 30
          && ((VisitHoursDTO) o).getVisitLength() != 45
          && ((VisitHoursDTO) o).getVisitLength() != 60) {
        errors.rejectValue("visitLength", "visitHours.error.visitLength");
      }
    }
  }
}
