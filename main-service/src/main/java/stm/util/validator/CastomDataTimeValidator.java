package stm.util.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import stm.util.Constants;

import java.time.LocalDateTime;

public class CastomDataTimeValidator implements ConstraintValidator<CastomDataTime, Object> {
    public int delay;

    @Override
    public void initialize(final CastomDataTime constraintAnnotation) {
        this.delay = constraintAnnotation.delay();
    }

    @Override
    public boolean isValid(Object date, ConstraintValidatorContext context) {
        if (date == null) {
            return true;
        }
        return LocalDateTime.parse((String) date, Constants.formatterDateTime)
                .isAfter(LocalDateTime.now().plusHours(delay));
    }
}
