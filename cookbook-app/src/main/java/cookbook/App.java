package cookbook;

import cookbook.domain.Recipe;
import cookbook.persistence.Data;
import cookbook.service.Service;
import cookbook.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class App {

    @Autowired
    private Data data;

    @Autowired
    private View view;

    @Autowired
    private Service service;

    public void start() {
        service.loadData();

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
                listRecipes();
                break;
            case "Q":
            case "q":
                saveAppState();
                System.exit(-1);
            default:
                view.printInvalidInput();
                homeScreen();
        }
    }

    private void saveAppState() {
        service.saveData();
    }

    private void processPostLoginInput() {
        String input = view.getInput();
        switch (input){
            case "1":
                addRecipe();
                homeScreen();
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
        Recipe recipeToAdd = view.readRecipe(service.getCurrentUser());
        recipeToAdd.setId((long) service.getRecipes().size() + 1);
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
                if (service.isLoggedIn()) {
                    view.printUserRecipeOptions();
                } else {
                    view.printGuestRecipeOptions();
                }
                try {
                    processRecipeMenuInput(service.getRecipes().get(Integer.parseInt(input)), view.getInput());
                } catch (Exception e) {
                    view.printInvalidInput();
                    listRecipes();
                }
        }
    }

    private void printRecipe(int id) {
        view.printRecipe(service.getRecipes().get(id));
    }

    private void processRecipeMenuInput(Recipe recipe, String input) {
        switch (input) {
            case "1":
                view.printRecipeComment(recipe);
                if (service.isLoggedIn()) {
                    view.printUserRecipeOptions();
                } else {
                    view.printGuestRecipeOptions();
                }
                break;
            case "2":
                if (service.isLoggedIn()) {
                    newComment(recipe);
                } else {
                    view.printNotAuthenticated();
                    listRecipes();
                }
            case "q":
            case "Q":
                listRecipes();
            default:
                view.printInvalidInput();
        }
        processRecipeMenuInput(recipe, view.getInput());
    }

    private void newComment(Recipe recipe) {
        view.printNewCommentForm();
        String newComment = view.getInput();
        service.saveComment(recipe, newComment);
        view.printSuccessfulComment(recipe, service.getCurrentUser(), newComment);
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
