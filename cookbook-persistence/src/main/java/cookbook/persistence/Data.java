package cookbook.persistence;

import cookbook.domain.*;
import cookbook.persistence.entity.Comment;
import cookbook.persistence.entity.Cook;
import cookbook.persistence.entity.Ingredient;
import cookbook.persistence.entity.Recipe;
import cookbook.persistence.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Data {
    private List<Cook> cooks;
    private List<Recipe> recipes;
    private List<Comment> comments;

    @Autowired
    private RecipeRepository recipeRepository;

    public List<Cook> getCooks() {
        return cooks;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void loadData() {
        cooks = readAllLines("cooks").stream().map(this::getCook).collect(Collectors.toList());

        recipes = getRecipes("recipes");

        comments = readAllLines("comments").stream().map(this::getComment).collect(Collectors.toList());
    }

    private List<Recipe> getRecipes(String filename){
        List<Recipe> recipes = new ArrayList<>();
        List<String> recipeStrings = readAllLines(filename);
        int i = 0;
        while (i < recipeStrings.size()) {
            Recipe recipe = new Recipe();
            recipe.setComments(new ArrayList<>());
            for (; i < recipeStrings.size(); i++) {
                switch (recipeStrings.get(i)) {
                    case "[id]":
                        i++;
                        recipe.setId(Long.parseLong(recipeStrings.get(i)));
                        break;
                    case "[user_id]":
                        i++;
                        recipe.setUploader(findCookById(Long.parseLong(recipeStrings.get(i))));
                        break;
                    case "[name]":
                        i++;
                        recipe.setName(recipeStrings.get(i));
                        break;
                    case "[ingredients]":
                        i++;
                        List<Ingredient> ingredients = new ArrayList<>();
                        while (!recipeStrings.get(i).startsWith("[")) {
                            String[] splitted = recipeStrings.get(i).split(" ");
                            Ingredient ingredient = new Ingredient();
                            ingredient.setAmount(Double.parseDouble(splitted[0]));
                            ingredient.setUnit(Unit.valueOf(splitted[1]));
                            ingredient.setName(splitted[2]);
                            ingredients.add(ingredient);
                            i++;
                        }
                        recipe.setIngredients(ingredients);
                        i--;
                        break;
                    case "[preparation]":
                        i++;
                        StringBuilder preparation = new StringBuilder();
                        while (!recipeStrings.get(i).startsWith("[")) {
                            preparation.append(recipeStrings.get(i)).append("\n");
                            i++;
                        }
                        recipe.setPreparation(preparation.toString());
                        i--;
                        break;
                    case "[servings]":
                        i++;
                        recipe.setServings(Integer.parseInt(recipeStrings.get(i)));
                        break;
                    case "[categories]":
                        i++;
                        List<Category> categories = new ArrayList<>();
                        while (i < recipeStrings.size() && !recipeStrings.get(i).startsWith("[")) {
                            categories.add(Category.valueOf(recipeStrings.get(i)));
                            i++;
                        }
                        recipe.setCategories(categories);
                        break;
                }
                if (i < recipeStrings.size() && recipeStrings.get(i).equals("[id]")){
                    i--;
                    break;
                }
            }
            recipes.add(recipe);
            recipeRepository.save(recipe);
        }
        return recipes;
    }

    private Cook getCook(String line) {
        String[] splitted = line.split(" ");
        Cook cook = new Cook();
        cook.setComments(new ArrayList<>());
        cook.setOwnRecipes(new ArrayList<>());
        cook.setId(Long.parseLong(splitted[0]));
        cook.setUsername(splitted[1]);
        cook.setPassword(splitted[2]);
        return cook;
    }

    private Comment getComment(String line) {
        String[] splitted = line.split(" ");
        Comment comment = new Comment();
        comment.setId(Long.parseLong(splitted[0]));
        comment.setRecipeId(Long.parseLong(splitted[1]));
        comment.setOwner(findCookById(Long.parseLong(splitted[2])));
        comment.setTimestamp(LocalDateTime.parse(splitted[3]));
        comment.setDescription(String.join(" ", Arrays.copyOfRange(splitted, 4, splitted.length)));
        findRecipeById(Long.parseLong(splitted[1])).getComments().add(comment);
        findCookById(Long.parseLong(splitted[2])).getComments().add(comment);
        return comment;
    }

    public Recipe findRecipeById(Long id) {
        return recipes.stream().filter(y -> id.equals(y.getId())).findFirst().get();
    }

    public Cook findCookById(Long id) {
        return cooks.stream().filter(x -> x.getId().equals(id)).findFirst().get();
    }

    private List<String> readAllLines(String fileName) {
        try {
            return Files.readAllLines(ResourceUtils.getFile(String.format("classpath:%s.txt", fileName)).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveCurrentRecipes() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("cookbook-persistence/src/main/resources/recipes.txt"));
            for (Recipe recipe : recipes) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[id]").append("\n")
                        .append(recipe.getId()).append("\n")
                        .append("[user_id]").append("\n")
                        .append(recipe.getUploader().getId()).append("\n")
                        .append("[name]").append("\n")
                        .append(recipe.getName()).append("\n")
                        .append("[ingredients]").append("\n");
                for (Ingredient ingredient : recipe.getIngredients()) {
                    stringBuilder.append(ingredient.getAmount()).append(" ")
                            .append(ingredient.getUnit()).append(" ")
                            .append(ingredient.getName()).append("\n");
                }
                stringBuilder.append("[preparation]").append("\n")
                        .append(recipe.getPreparation())
                        .append("[servings]").append("\n")
                        .append(recipe.getServings()).append("\n")
                        .append("[categories]").append("\n");
                for (Category category : recipe.getCategories()) {
                    stringBuilder.append(category).append("\n");
                }
                pw.write(stringBuilder.toString());
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCurrentComments() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("cookbook-persistence/src/main/resources/comments.txt"));
            for (Comment comment : comments) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(comment.getId()).append(" ")
                        .append(comment.getRecipeId()).append(" ")
                        .append(comment.getOwner().getId()).append(" ")
                        .append(comment.getTimestamp()).append(" ")
                        .append(comment.getDescription()).append("\n");
                pw.write(stringBuilder.toString());
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}