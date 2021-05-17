package cookbook.validators;

import cookbook.domain.Unit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IngredientValidator implements ConstraintValidator<IngredientConstraint, String> {

    @Override
    public boolean isValid(String ingredientField, ConstraintValidatorContext context) {

        String[] splitted = ingredientField.split(System.lineSeparator());
        for (String line : splitted) {
            String[] part = line.split(" ");
            if (part.length != 3) {
                return false;
            }
            try {
                Double.parseDouble(part[0]);
            } catch (NumberFormatException e) {
                return false;
            }
            try {
                Unit.valueOf(part[1]);
            } catch (IllegalArgumentException e) {
                return false;
            }
            if (part[2].isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
