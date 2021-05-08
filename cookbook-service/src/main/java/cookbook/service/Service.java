package cookbook.service;

import cookbook.domain.*;
import cookbook.persistence.Data;
import cookbook.persistence.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Service implements IService {

    @Autowired
    private Data data;

    @Autowired
    private RecipeRepository recipeRepository;

    private User currentlyLoggedIn;

    public Iterable<Recipe> findAll()  {
        return recipeRepository.findAll();
    }

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
        comment.setId(getCommentId());
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
        Recipe recipeToDelete = data.getRecipes().stream().filter(y -> y.getId().equals(Long.parseLong(input))).findAny().get();
        data.getRecipes().remove(recipeToDelete);
        List<Comment> commentsToDelete = data.getComments().stream().filter(y->y.getRecipeId().equals(recipeToDelete.getId())).collect(Collectors.toList());
        data.getComments().removeAll(commentsToDelete);
        getCurrentUser().getOwnRecipes().remove(recipeToDelete);
    }

    @Override
    public User getCurrentlyLoggedIn() {
        return currentlyLoggedIn;
    }

    @Override
    public void setCurrentlyLoggedIn(User currentlyLoggedIn) {
        this.currentlyLoggedIn = currentlyLoggedIn;
    }

    @Override
    public void saveData() {
        data.saveCurrentRecipes();
        data.saveCurrentComments();
    }

    @Override
    public void loadData() {
        data.loadData();
    }

    @Override
    public Long getRecipeId() {
        int lastIndex = data.getRecipes().size() - 1;
        return data.getRecipes().get(lastIndex).getId() + 1;
    }

    private Long getCommentId() {
        int lastIndex = data.getComments().size() - 1;
        return data.getComments().get(lastIndex).getId() + 1;
    }
}
