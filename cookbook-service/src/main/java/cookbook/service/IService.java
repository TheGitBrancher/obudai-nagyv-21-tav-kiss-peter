package cookbook.service;

import cookbook.domain.Category;
import cookbook.persistence.entity.Cook;
import cookbook.persistence.entity.Recipe;
import cookbook.persistence.entity.User;
import dto.CookDto;
import dto.RecipeDto;
import dto.UserDto;

import java.util.List;
import java.util.Set;

public interface IService {

    void login(String input);

    void logout();

    void addRecipe(RecipeDto recipe);

    void saveComment(RecipeDto recipe, String input);

    boolean isLoggedIn();

    List<RecipeDto> getRecipes();

    Set<Category> getCategories();

    Cook getCurrentUser();

    void deleteRecipe(String input);

    UserDto getCurrentlyLoggedIn();
}
