package cookbook.view;

import cookbook.domain.*;
import cookbook.persistence.entity.Comment;
import cookbook.persistence.entity.Cook;
import cookbook.persistence.entity.Ingredient;
import cookbook.persistence.entity.Recipe;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class View implements IView{

    private final Scanner in = new Scanner(System.in);

    @Override
    public Recipe readRecipe(Cook cook) {
        Recipe recipe = new Recipe();
        recipe.setIngredients(new ArrayList<>());
        recipe.setCategories(new ArrayList<>());
        recipe.setComments(new ArrayList<>());
        recipe.setUploader(cook);

        System.out.println("What's the name of your dish?");
        recipe.setName(getInput());

        System.out.println("How many people does this dish serve?");
        recipe.setServings(Integer.parseInt(getInput()));

        System.out.println("What kind of ingredients do you need?");
        do {
            recipe.getIngredients().add(addIngredient());
            System.out.println("Add another? (Y/N)");
        } while (!"n".equalsIgnoreCase(getInput()));

        System.out.println("How do you make this dish? (type 'C' to continue)");
        String prep = getInput();
        if (!prep.equalsIgnoreCase("c")) {
            recipe.setPreparation(prep + "\n");
        } else {
            recipe.setPreparation("\n");
        }

        System.out.println("How would you categorize this dish? (type 'C' to continue)");
        List<Category> categories = Arrays.stream(Category.values()).collect(Collectors.toList());
        while (!categories.isEmpty()) {
            printAvailableCategory(categories);
            String input = getInput();
            if ("c".equalsIgnoreCase(input)) {
                break;
            } else {
                recipe.getCategories().add(categories.get(Integer.parseInt(input)));
                categories.remove(Integer.parseInt(input));
            }
        }
        System.out.println("-- Recipe created with the following informations: --");
        return recipe;
    }

    @Override
    public void printRecipeNoDetail(Recipe recipe) {
        System.out.printf("Name:\t%s%n", recipe.getName());
        System.out.printf("Recipe ID:\t%d%n", recipe.getId());
        System.out.printf("Servings:\t%d%n", recipe.getServings());
        System.out.printf("Uploader:\t%s%n", recipe.getUploader().getUsername());
    }

    private void printAvailableCategory(List<Category> categories) {
        for (int i = 0; i < categories.size(); i++){
            System.out.printf("%d: %s%n", i, categories.get(i).toString());
        }
    }

    private Ingredient addIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setAmount(Double.parseDouble(getInput()));
        ingredient.setUnit(Unit.valueOf(getInput().toUpperCase()));
        ingredient.setName(getInput());
        return ingredient;
    }

    @Override
    public void printWelcome() {
        System.out.println("-- Application started --");
        System.out.println("-- Welcome to the Cookbook application! --");
    }

    @Override
    public void printUserOptions() {
        System.out.println("1: Create new recipe.");
        System.out.println("2: List existing recipes.");
        System.out.println("3: Delete recipe.");
        System.out.println("4: Log out.");
    }

    @Override
    public void printGuestOptions() {
        System.out.println("1: Log in.");
        System.out.println("2: Browse existing recipes.");
        System.out.println("Q: Exit the application.");
    }

    @Override
    public void printRecipe(Recipe recipe) {
        System.out.printf("\t-- Recipe: %s --%n", recipe.getName());
        System.out.printf("Recipe ID:\t%s%n", recipe.getId());
        System.out.printf("Uploader:\t%s%n", recipe.getUploader().getUsername());
        System.out.printf("Servings:\t%s%n", recipe.getServings());
        System.out.println("Ingredients:");
        for (Ingredient ingredient : recipe.getIngredients()){
            System.out.printf("\t%f %s %s%n", ingredient.getAmount(), ingredient.getUnit(), ingredient.getName());
        }
        System.out.printf("Preparation: %n\t%s", recipe.getPreparation());
        System.out.println("Categories:");
        for (Category category : recipe.getCategories())
        {
            System.out.printf("\t%s%n", category);
        }
        System.out.println();
    }

    @Override
    public void printUserRecipeOptions() {
        System.out.println("1: See comments");
        System.out.println("2: Write comment");
        System.out.println("Q: Go back");
    }

    @Override
    public void printGuestRecipeOptions() {
        System.out.println("1: See comments");
        System.out.println("2: -- Log in to write comments --");
        System.out.println("Q: Go back");
    }

    @Override
    public void printRecipeComment(Recipe recipe) {
        for (Comment comment : recipe.getComments()){
            System.out.printf("%d:\t%s%n", comment.getId(), comment.getTimestamp().toString());
            System.out.printf("%s%n%n", comment.getDescription());
        }
    }

    @Override
    public void printRecipes(List<Recipe> recipes) {
        for (int i = 0; i < recipes.size(); i++){
            System.out.printf("%d: %s%n", i, recipes.get(i).getName());
        }
        System.out.println("Q: Go back");
    }

    @Override
    public void printNewCommentForm() {
        System.out.println("Write your comment (single-line):");
    }

    @Override
    public void printNotAuthenticated() {
        System.out.println("You need to log in first!");
    }

    @Override
    public void printLogout(String username) {
        System.out.printf("-- %s user logged out. --%n", username);
    }

    @Override
    public String getInput() {
        return in.nextLine();
    }

    @Override
    public String readUser() {
        System.out.println("Give me your username:");
        String username = getInput();
        System.out.println("Give me your password:");
        String password = getInput();
        return username + "-" + password;
    }

    @Override
    public void printSuccessfulLogin(String username) {
        System.out.printf("-- %s user logged in. --%n", username);
    }

    @Override
    public void printIncorrectCredentials() {
        System.out.println("Incorrect credentials, please try again!");
    }

    @Override
    public String readRecipeId() {
        System.out.println("Enter the id of the recipe you want to delete:");
        return getInput();
    }

    @Override
    public String confirm() {
        System.out.println("Are you sure? (Y/N)");
        return getInput();
    }

    @Override
    public void printInvalidInput() {
        System.out.println("Invalid input, please try again!");
    }

    @Override
    public void printSuccessfulComment(Recipe recipe, Cook currentUser, String newComment) {
        System.out.printf("-- %s user created comment (%s) saved for %s recipe --%n", currentUser.getUsername(), newComment, recipe.getName());
    }

    @Override
    public void printSuccessfulDelete(String toDelete) {
        System.out.printf("Recipe with account ID: %s deleted. --%n", toDelete);
    }
}
