package cookbook.service;

import cookbook.domain.*;
import cookbook.persistence.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Component
public class Service implements IService {

    @Autowired
    private Data data;

    private User currentlyLoggedIn;

    @Override
    public void login(String input) {
        String[] splitted = input.split("-");
        User user = data.getCooks().stream().filter(y -> splitted[0].equals(y.getUsername())).findFirst().get();
        if (splitted[1].equals(user.getPassword())) {
            currentlyLoggedIn = user;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void logout() {
        currentlyLoggedIn = null;
    }

    @Override
    public void addRecipe(Recipe recipe) {
        data.getRecipes().add(recipe);
        getCurrentUser().getOwnRecipes().add(recipe);
    }

    @Override
    public void saveComment(Recipe recipe, String input) {
        Comment comment = new Comment();
        comment.setId((long) data.getComments().size() + 1);
        comment.setRecipeId(recipe.getId());
        comment.setOwner(getCurrentUser());
        comment.setTimestamp(LocalDateTime.now());
        comment.setDescription(input);
        data.findRecipeById(recipe.getId()).getComments().add(comment);
        data.getComments().add(comment);
    }

    @Override
    public boolean isLoggedIn() {
        return (currentlyLoggedIn != null);
    }

    @Override
    public List<Recipe> getRecipes() {
        return data.getRecipes();
    }

    @Override
    public Set<Category> getCategories() {
        return null;
    }

    @Override
    public Cook getCurrentUser() {
        return (Cook) currentlyLoggedIn;
    }

    @Override
    public void deleteRecipe(String input) {
        data.getRecipes().remove(Integer.parseInt(input) + 1);
        getCurrentUser().getOwnRecipes().remove(Integer.parseInt(input) + 1);
    }

    public User getCurrentlyLoggedIn() {
        return currentlyLoggedIn;
    }

    public void setCurrentlyLoggedIn(User currentlyLoggedIn) {
        this.currentlyLoggedIn = currentlyLoggedIn;
    }

    public void saveData() {
        data.saveCurrentRecipes();
        data.saveCurrentComments();
    }

    public void loadData() {
        data.loadData();
    }
}
