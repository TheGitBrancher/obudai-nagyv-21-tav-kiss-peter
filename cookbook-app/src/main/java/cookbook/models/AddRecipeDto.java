package cookbook.models;

import cookbook.domain.Category;
import cookbook.validators.IngredientConstraint;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

public class AddRecipeDto {

    @IngredientConstraint
    @NotEmpty(message = "Ingredient must not be empty")
    private String ingredients;

    @NotEmpty(message = "Categories must not be empty")
    private List<Category> categories;

    @NotEmpty(message = "Name must not be empty, nor longer than 255 chars.")
    @Size(min = 0, max = 255)
    private String name;

    @Positive(message = "Servings must be positive")
    private Integer servings;

    @NotEmpty(message = "Preparation must not be empty, nor longer than 255 chars.")
    @Size(min = 0, max = 255)
    private String preparation;

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }
}
