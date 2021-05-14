package cookbook.service;

import cookbook.domain.*;
import cookbook.persistence.entity.*;
import cookbook.persistence.repository.*;
import dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import transformer.CookTransformer;
import transformer.RecipeTransformer;
import transformer.UserTransformer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Component
public class Service implements IService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CookRepository cookRepository;

    @Autowired
    private UserRepository userRepository;

    RecipeTransformer recipeTransformer = new RecipeTransformer();
    CookTransformer cookTransformer = new CookTransformer();

    private CookDto currentlyLoggedIn;

    @Override
    public void login(String input) {
        String[] splitted = input.split("-");
        UserDto userDto = new UserDto();
        userDto.setUsername(splitted[0]);
        userDto.setPassword(splitted[1]);

        if (authenticate(userDto)) {
            List<Cook> cooks = (List<Cook>)cookRepository.findAll();
            Cook toLogin = cooks.stream().filter(y-> y.getUsername().equals(userDto.getUsername())).findFirst().get();
            currentlyLoggedIn = cookTransformer.convertToCookDto(toLogin);
        } else {
            throw new NoSuchElementException();
        }
    }

    public boolean authenticate(UserDto user) {
        List<User> users = (List<User>) userRepository.findAll();
        User userToLogin = users.stream().filter(y -> y.getUsername().equals(user.getUsername())).findFirst().get();

        if (userToLogin.getUsername() != null) {
            return userToLogin.getPassword().equals(user.getPassword());
        }
        else {
            return false;
        }
    }

    @Override
    public void logout() {
        currentlyLoggedIn = null;
    }

    @Override
    public void addRecipe(RecipeDto recipe) {
        recipe.setUploader(currentlyLoggedIn);
        Recipe recipeToSave = recipeTransformer.convertDtoToRecipe(recipe);
        recipeRepository.save(recipeToSave);
    }

    @Override
    public void saveComment(RecipeDto recipeDto, String input) {
        Recipe recipeToAddCommentTo = recipeRepository.findById(recipeDto.getId()).stream().findFirst().get();

        Comment comment = new Comment();
        Cook owner = getCurrentUser();
        comment.setOwner(owner);
        comment.setTimestamp(LocalDateTime.now());
        comment.setDescription(input);

        recipeToAddCommentTo.getComments().add(comment);
        commentRepository.save(comment);
    }

    @Override
    public boolean isLoggedIn() {
        return currentlyLoggedIn != null;
    }

    @Override
    public List<RecipeDto> getRecipes() {
        List<Recipe> recipeList = (List<Recipe>)recipeRepository.findAll();
        List<RecipeDto> recipeDtoList = new ArrayList<>();

        for (Recipe recipe : recipeList) {
            recipeDtoList.add(recipeTransformer.convertToRecipeDto(recipe));
        }

        return recipeDtoList;
    }

    public RecipeDto getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).get();
        return recipeTransformer.convertToRecipeDto(recipe);
    }

    @Override
    public Set<Category> getCategories() {
        return null;
    }

    @Override
    public Cook getCurrentUser() {
        List<Cook> cooks = (List<Cook>)cookRepository.findAll();
        return cooks.stream().filter(y -> y.getId().equals(getCurrentlyLoggedIn().getId())).findFirst().get();
    }

    @Override
    public void deleteRecipe(String input) {
        Recipe recipeToDelete = recipeRepository.findById(Long.parseLong(input)).stream().findFirst().get();
        //commentRepository.deleteAll(recipeToDelete.getComments());
        recipeRepository.delete(recipeToDelete);
        //getCurrentUser().getOwnRecipes().remove(recipeToDelete);
    }

    @Override
    public UserDto getCurrentlyLoggedIn() {
        return currentlyLoggedIn;
    }
}
