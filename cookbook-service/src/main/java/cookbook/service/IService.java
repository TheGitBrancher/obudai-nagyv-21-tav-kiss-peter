package cookbook.service;

import cookbook.domain.Category;
import cookbook.domain.Cook;
import cookbook.domain.Recipe;
import cookbook.domain.User;

import java.util.List;
import java.util.Set;

public interface IService {

    void login(String input);

    void logout();

    void addRecipe(Recipe recipe);

    void saveComment(Recipe recipe, String input);

    boolean isLoggedIn();

    List<Recipe> getRecipes();

    Set<Category> getCategories();

    Cook getCurrentUser();

    void deleteRecipe(String input);

    User getCurrentlyLoggedIn();

    void setCurrentlyLoggedIn(User currentlyLoggedIn);

    void saveData();

    void loadData();

    Long getRecipeId();
}
