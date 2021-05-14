package cookbook;

import cookbook.persistence.entity.Recipe;
import cookbook.service.Service;
import cookbook.view.View;
import dto.RecipeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class App {
    @Autowired
    private View view;

    @Autowired
    private Service service;

    public void start() {
        view.printWelcome();
        homeScreen();
    }

    private void homeScreen() {
        if (service.isLoggedIn()) {
            view.printUserOptions();
            processPostLoginInput();
        } else {
            view.printGuestOptions();
            processMainMenuInput(view.getInput());
        }
    }

    private void processMainMenuInput(String input) {
        switch (input) {
            case "1":
                login();
                break;
            case "2":
                try {
                    listRecipes();
                    break;
                } catch (Exception e){
                    view.printInvalidInput();
                    homeScreen();
                    break;
                }
            case "Q":
            case "q":
                System.exit(-1);
            default:
                view.printInvalidInput();
                homeScreen();
        }
    }

    private void processPostLoginInput() {
        String input = view.getInput();
        switch (input){
            case "1":
                try {
                    addRecipe();
                } catch (Exception e) {
                    view.printInvalidInput();
                } finally {
                    homeScreen();
                }
                break;
            case "2":
                listRecipes();
                break;
            case "3":
                deleteRecipe();
                homeScreen();
                break;
            case "4":
                logout();
                homeScreen();
                break;
            default:
                view.printInvalidInput();
                homeScreen();
        }
    }

    private void addRecipe() {
        RecipeDto recipeToAdd = view.readRecipe();
        view.printRecipeNoDetail(recipeToAdd);
        service.addRecipe(recipeToAdd);
    }

    private void logout() {
        view.printLogout(service.getCurrentlyLoggedIn().getUsername());
        service.logout();
    }

    private void login() {
        try {
            service.login(view.readUser());
        } catch (Exception e) {
            view.printIncorrectCredentials();
        } finally {
            if (service.isLoggedIn()){
                view.printSuccessfulLogin(service.getCurrentlyLoggedIn().getUsername());
            }
            homeScreen();
        }
    }

    private String readCredentials() {
        return view.getInput();
    }

    private void listRecipes() {

        view.printRecipes(service.getRecipes());

        String input = view.getInput();
        switch (input) {
            case "q":
            case "Q":
                homeScreen();
            default:
                printRecipe(Integer.parseInt(input));
                printRecipeOptions();
                try {
                    processRecipeMenuInput(service.getRecipeById(Long.parseLong(input)), view.getInput());
                } catch (Exception e) {
                    view.printInvalidInput();
                    listRecipes();
                }
        }
    }

    private void printRecipeOptions() {
        if (service.isLoggedIn()) {
            view.printUserRecipeOptions();
        } else {
            view.printGuestRecipeOptions();
        }
    }

    private void printRecipe(int id) {
        view.printRecipe(service.getRecipeById((long)id));
    }

    private void processRecipeMenuInput(RecipeDto recipe, String input) {
        switch (input) {
            case "1":
                view.printRecipeComment(recipe);
                break;
            case "2":
                if (service.isLoggedIn()) {
                    newComment(recipe);
                    break;
                } else {
                    view.printNotAuthenticated();
                    listRecipes();
                    break;
                }
            case "q":
            case "Q":
                listRecipes();
            default:
                view.printInvalidInput();
        }
        printRecipeOptions();
        processRecipeMenuInput(recipe, view.getInput());
    }

    private void newComment(RecipeDto recipe) {
        view.printNewCommentForm();
        String newComment = view.getInput();
        service.saveComment(recipe, newComment);
        view.printSuccessfulComment(recipe, service.getCurrentlyLoggedIn(), newComment);
    }

    private void deleteRecipe() {
        String toDelete = view.readRecipeId();
        if (view.confirm().equalsIgnoreCase("y")) {
            try {
                service.deleteRecipe(toDelete);
                view.printSuccessfulDelete(toDelete);
            } catch (Exception e) {
                view.printInvalidInput();
            }
        }
    }
}
