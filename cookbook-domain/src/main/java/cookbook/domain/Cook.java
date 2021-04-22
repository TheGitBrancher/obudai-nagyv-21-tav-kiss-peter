package cookbook.domain;

import java.util.List;

public class Cook extends User {
    private List<Recipe> ownRecipes;
    private List<Comment> comments;

    public List<Recipe> getOwnRecipes() {
        return ownRecipes;
    }

    public void setOwnRecipes(List<Recipe> ownRecipes) {
        this.ownRecipes = ownRecipes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
