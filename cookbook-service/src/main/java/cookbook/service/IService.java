package cookbook.service;

import cookbook.persistence.entity.Cook;
import cookbook.service.dto.RecipeDto;
import cookbook.service.dto.UserDto;

import java.util.List;

public interface IService {

    void login(String input);

    void logout();

    void addRecipe(RecipeDto recipeDto);

    void saveComment(RecipeDto recipe, String input);

    boolean isLoggedIn();

    List<RecipeDto> getRecipes();

    List<RecipeDto> getRecipes(String search, List<String> filter);

    Cook getCurrentUser();

    void deleteRecipe(Long id);

    UserDto getCurrentlyLoggedIn();
}
