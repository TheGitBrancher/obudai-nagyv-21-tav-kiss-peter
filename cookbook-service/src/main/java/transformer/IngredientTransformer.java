package transformer;

import cookbook.persistence.entity.Ingredient;
import dto.IngredientDto;

public class IngredientTransformer {
    public static IngredientDto getDto(Ingredient toTransform) {

        IngredientDto dto = new IngredientDto();

        dto.setName(toTransform.getName());
        dto.setUnit(toTransform.getUnit());
        dto.setName(toTransform.getName());

        return dto;
    }
}
