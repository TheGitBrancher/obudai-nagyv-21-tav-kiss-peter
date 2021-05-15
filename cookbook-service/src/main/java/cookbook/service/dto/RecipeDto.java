package cookbook.service.dto;

import cookbook.domain.Category;

import java.util.List;

public class RecipeDto {

    private Long id;

    private CookDto uploader;

    private List<IngredientDto> ingredients;
    private List<Category> categories;
    private List<CommentDto> comments;
    private String name;

    private Integer servings;
    private String preparation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CookDto getUploader() {
        return uploader;
    }

    public void setUploader(CookDto uploader) {
        this.uploader = uploader;
    }

    public List<IngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDto> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
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
