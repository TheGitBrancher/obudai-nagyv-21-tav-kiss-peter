package cookbook.service;

import cookbook.domain.Category;
import cookbook.persistence.entity.Cook;
import cookbook.service.dto.AddRecipeDto;
import cookbook.service.dto.RecipeDto;
import cookbook.service.dto.UserDto;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IService {

    void login(String input);

    void logout();

    void addRecipe(AddRecipeDto addRecipeDto);

    void saveComment(RecipeDto recipe, String input);

    boolean isLoggedIn();

    List<RecipeDto> getRecipes();

    Cook getCurrentUser();

    void deleteRecipe(Long id);

    UserDto getCurrentlyLoggedIn();
}
