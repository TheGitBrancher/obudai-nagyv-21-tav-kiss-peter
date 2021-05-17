package cookbook.service;

import cookbook.domain.Category;
import cookbook.domain.Unit;
import cookbook.persistence.entity.*;
import cookbook.persistence.repository.*;
import cookbook.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import cookbook.service.transformer.CookTransformer;
import cookbook.service.transformer.RecipeTransformer;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    private final RecipeTransformer recipeTransformer = new RecipeTransformer();
    private final CookTransformer cookTransformer = new CookTransformer();

    private CookDto currentlyLoggedIn;

    @Override
    @Transactional
    public void login(String input) {

/*        String[] splitted = input.split("-");
        UserDto userDto = new UserDto();
        userDto.setUsername(splitted[0]);
        userDto.setPassword(splitted[1]);

        if (authenticate(userDto)) {
            List<Cook> cooks = cookRepository.findAll();
            Cook toLogin = cooks.stream().filter(y-> y.getUsername().equals(userDto.getUsername())).findFirst().get();
            toLogin.getComments().size();
            toLogin.getOwnRecipes().size();
            currentlyLoggedIn = cookTransformer.convertToCookDto(toLogin);
        } else {
            throw new NoSuchElementException();
        }*/
    }

/*    public boolean authenticate(UserDto userDto) {
        List<User> users = userRepository.findAll();
        User userToLogin = users.stream().filter(y -> y.getUsername().equals(userDto.getUsername())).findFirst().get();

        if (userToLogin.getUsername() != null) {
            return userToLogin.getPassword().equals(userDto.getPassword());
        }
        else {
            return false;
        }
    }*/

    @Override
    public void logout() {
        currentlyLoggedIn = null;
    }

    @Override
    public void addRecipe(AddRecipeDto addRecipeDto) {
        Recipe recipeToAdd = new Recipe();
        recipeToAdd.setName(addRecipeDto.getName());
        recipeToAdd.setCategories(addRecipeDto.getCategories());
        recipeToAdd.setPreparation(addRecipeDto.getPreparation());
        recipeToAdd.setUploader(getCurrentUser());
        recipeToAdd.setServings(addRecipeDto.getServings());
        recipeToAdd.setIngredients(ingredientFactory(addRecipeDto.getIngredients()));

        recipeRepository.save(recipeToAdd);
    }

    private List<Ingredient> ingredientFactory(String ingredients) {

        return Arrays.stream(ingredients.split(System.lineSeparator()))
                .map(this::toIngredient).collect(Collectors.toList());
    }

    private Ingredient toIngredient(String line) {
        Ingredient ingredientToAdd = new Ingredient();
        String[] ingredientParts = line.split(" ");
        ingredientToAdd.setAmount(Integer.parseInt(ingredientParts[0]));
        ingredientToAdd.setUnit(Unit.valueOf(ingredientParts[1]));
        ingredientToAdd.setName(ingredientParts[2]);
        return ingredientToAdd;
    }

    @Override
    @Transactional
    public void saveComment(RecipeDto recipeDto, String input) {
        Recipe recipeToAddCommentTo = recipeRepository.findById(recipeDto.getId()).stream().findFirst().get();

        Comment comment = new Comment();
        Cook owner = getCurrentUser();
        comment.setRecipe_id(recipeDto.getId());
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
    @Transactional
    public List<RecipeDto> getRecipes() {
        List<Recipe> recipeList = recipeRepository.findAll();
        //FetchType.EAGER
        recipeList.forEach(y -> y.getIngredients().size());
        recipeList.forEach(y -> y.getComments().size());
        recipeList.forEach(y -> y.getCategories().size());

        List<RecipeDto> recipeDtoList = new ArrayList<>();

        for (Recipe recipe : recipeList) {
            recipeDtoList.add(recipeTransformer.convertToRecipeDto(recipe));
        }

        return recipeDtoList;
    }

    @Transactional
    public RecipeDto getRecipeById(Long id) {
        List<RecipeDto> recipes = getRecipes();

        return recipes.stream().filter(y -> y.getId().equals(id)).findFirst().get();
    }

    public List<RecipeDto> getMyRecipes() {
        List<Recipe> myRecipes = recipeRepository.findAll();
        List<RecipeDto> myRecipeDtos = new ArrayList<>();

        for (Recipe recipe : myRecipes) {
            if (recipe.getUploader().getUsername().equals(getCurrentUsername())) {
                myRecipeDtos.add(recipeTransformer.convertToRecipeDto(recipe));
            }
        }

        return myRecipeDtos;
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public Cook getCurrentUser() {
        List<Cook> cooks = cookRepository.findAll();
        return cooks.stream().filter(y -> y.getUsername().equals(getCurrentUsername())).findFirst().get();
    }

    public List<String> getCategories() {
        return Arrays.stream(Category.values()).map(Enum::toString).collect(Collectors.toList());
    }

    @Override
    public void deleteRecipe(Long id) {
        Recipe recipeToDelete = recipeRepository.findById(id).get();
        recipeRepository.delete(recipeToDelete);
    }

    @Override
    public UserDto getCurrentlyLoggedIn() {
        return currentlyLoggedIn;
    }
}
