package cookbook.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Recipe {

    @Id
    private Long id;

    @ManyToOne
    private Cook uploader;

    @Transient
    private List<Ingredient> ingredients;

    @Transient
    private List<Category> categories;

    @OneToMany
    private List<Comment> comments;

    private String name;
    private Integer servings;
    private String preparation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cook getUploader() {
        return uploader;
    }

    public void setUploader(Cook uploader) {
        this.uploader = uploader;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
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
