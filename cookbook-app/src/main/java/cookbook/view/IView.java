package cookbook.view;

import cookbook.persistence.entity.Cook;
import cookbook.persistence.entity.Recipe;

import java.util.List;

public interface IView {

    Recipe readRecipe(Cook cook);

    void printWelcome();

    void printUserOptions();

    void printGuestOptions();

    void printRecipe(Recipe recipe);

    void printUserRecipeOptions();

    void printGuestRecipeOptions();

    void printRecipeComment(Recipe recipe);

    void printRecipes(List<Recipe> recipes);

    void printNewCommentForm();

    void printNotAuthenticated();

    void printLogout(String username);

    String getInput();

    String readUser();

    void printIncorrectCredentials();

    String readRecipeId();

    void printSuccessfulLogin(String username);

    String confirm();

    void printInvalidInput();

    void printSuccessfulComment(Recipe recipe, Cook currentUser, String newComment);

    void printSuccessfulDelete(String toDelete);

    void printRecipeNoDetail(Recipe recipe);
}
