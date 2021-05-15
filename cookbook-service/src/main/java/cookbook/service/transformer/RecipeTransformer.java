package cookbook.service.transformer;

import cookbook.persistence.entity.Recipe;
import cookbook.service.dto.RecipeDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
