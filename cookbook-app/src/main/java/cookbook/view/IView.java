package cookbook.view;

import cookbook.service.dto.RecipeDto;
import cookbook.service.dto.UserDto;

import java.util.List;

public interface IView {

    RecipeDto readRecipe();

    void printWelcome();

    void printUserOptions();

    void printGuestOptions();

    void printRecipe(RecipeDto recipe);

    void printUserRecipeOptions();

    void printGuestRecipeOptions();

    void printRecipeComment(RecipeDto recipe);

    void printRecipes(List<RecipeDto> recipes);

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

    void printSuccessfulComment(RecipeDto recipe, UserDto currentUser, String newComment);

    void printSuccessfulDelete(String toDelete);

    void printRecipeNoDetail(RecipeDto recipe);
}
