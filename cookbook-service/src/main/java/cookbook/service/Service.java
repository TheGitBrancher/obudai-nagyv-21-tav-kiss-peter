package cookbook.service;

import cookbook.domain.Category;
import cookbook.domain.Unit;
import cookbook.persistence.entity.*;
import cookbook.persistence.repository.*;
import cookbook.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import cookbook.service.transformer.CookTransformer;
import cookbook.service.transformer.RecipeTransformer;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiPredicate;
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
    public void addRecipe(RecipeDto recipeDto) {
        Recipe recipeToAdd = recipeTransformer.convertDtoToRecipe(recipeDto);
        recipeToAdd.setUploader(getCurrentUser());

        recipeRepository.save(recipeToAdd);
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
    public List<RecipeDto> getRecipes() {
        return null;
    }

    @Override
    @Transactional
    public List<RecipeDto> getRecipes(String search, List<String> filter) {
        List<Recipe> recipeList = recipeRepository.findAll();
        //FetchType.EAGER
        recipeList.forEach(y -> y.getIngredients().size());
        recipeList.forEach(y -> y.getComments().size());
        recipeList.forEach(y -> y.getCategories().size());

        List<RecipeDto> recipeDtos = recipeList.stream().map(recipeTransformer::convertToRecipeDto).collect(Collectors.toList());
        if (search == null || search.isEmpty()) {
            return recipeDtos;
        }
        BiPredicate<RecipeDto, String> filterPredicate = createPredicate(filter);
        return recipeDtos.stream().filter(y -> filterPredicate.test(y, search)).collect(Collectors.toList());
    }

    private BiPredicate<RecipeDto, String> createPredicate(List<String> filter) {
        if (filter.isEmpty()) {
            return allPredicate;
        }

        BiPredicate<RecipeDto, String> predicate = falsePredicate;

        if (filter.contains("name")){
            predicate = predicate.or(namePredicate);
        }
        if (filter.contains("category")){
            predicate = predicate.or(categoryPredicate);
        }
        if (filter.contains("ingredient")){
            predicate = predicate.or(ingredientPredicate);
        }
        if (filter.contains("uploader")){
            predicate = predicate.or(uploaderPredicate);
        }
        return predicate;
    }

    BiPredicate<RecipeDto, String> falsePredicate = (recipeDto, searchString) -> false;
    BiPredicate<RecipeDto, String> namePredicate = (recipeDto, searchString) -> recipeDto.getName().contains(searchString);
    BiPredicate<RecipeDto, String> categoryPredicate = (recipeDto, searchString) -> recipeDto.getCategories().stream().anyMatch(y -> y.toString().contains(searchString));
    BiPredicate<RecipeDto, String> ingredientPredicate = (recipeDto, searchString) -> recipeDto.getIngredients().stream().anyMatch(y -> y.getName().contains(searchString));
    BiPredicate<RecipeDto, String> uploaderPredicate = (recipeDto, searchString) -> recipeDto.getUploader().getUsername().contains(searchString);
    BiPredicate<RecipeDto, String> allPredicate = (recipeDto, searchString) -> namePredicate.or(categoryPredicate).or(ingredientPredicate).or(uploaderPredicate).test(recipeDto, searchString);

    @Transactional
    public RecipeDto getRecipeById(Long id) {
        List<Recipe> recipes = recipeRepository.findAll();

        return recipeTransformer.convertToRecipeDto(recipes.stream().filter(y -> y.getId().equals(id)).findFirst().get());
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
