package cookbook.service;

import cookbook.persistence.entity.Cook;
import cookbook.service.dto.RecipeDto;
import cookbook.service.dto.UserDto;

import java.util.List;

public interface IService {

    List<RecipeDto> getMyRecipes();

    List<String> getCategories();

    void addRecipe(RecipeDto recipeDto);

    void saveComment(RecipeDto recipe, String input);

    List<RecipeDto> getRecipes(String search, List<String> filter);

    Cook getCurrentUser();

    void deleteRecipe(Long id);
}
