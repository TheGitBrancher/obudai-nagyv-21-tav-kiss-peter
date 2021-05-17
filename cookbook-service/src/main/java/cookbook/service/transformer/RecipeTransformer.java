package cookbook.service.transformer;

import cookbook.domain.Unit;
import cookbook.persistence.entity.Ingredient;
import cookbook.persistence.entity.Recipe;
import cookbook.service.dto.IngredientDto;
import cookbook.service.dto.RecipeDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeTransformer {

    private final ModelMapper modelMapper = new ModelMapper();

    public RecipeDto convertToRecipeDto(Recipe recipe) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper.map(recipe, RecipeDto.class);
    }

    public Recipe convertDtoToRecipe(RecipeDto recipeDto) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper.map(recipeDto, Recipe.class);
    }

    public List<IngredientDto> toIngredients(String ingredients) {

        return Arrays.stream(ingredients.split(System.lineSeparator()))
                .map(this::toIngredient).collect(Collectors.toList());
    }

    private IngredientDto toIngredient(String line) {
        IngredientDto ingredientToAdd = new IngredientDto();
        String[] ingredientParts = line.split(" ");
        ingredientToAdd.setAmount(Integer.parseInt(ingredientParts[0]));
        ingredientToAdd.setUnit(Unit.valueOf(ingredientParts[1]));
        ingredientToAdd.setName(ingredientParts[2]);
        return ingredientToAdd;
    }
}
