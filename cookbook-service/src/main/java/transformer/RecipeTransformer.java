package transformer;

import cookbook.persistence.entity.Recipe;
import dto.RecipeDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;

public class RecipeTransformer {

    private ModelMapper modelMapper;

    public RecipeDto convertToRecipeDto(Recipe recipe) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper.map(recipe, RecipeDto.class);
    }

    public Recipe convertDtoToRecipe(RecipeDto recipeDto) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper.map(recipeDto, Recipe.class);
    }
}
