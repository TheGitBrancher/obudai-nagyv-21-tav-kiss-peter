package cookbook.service.dto;

import java.util.List;

public class CookDto extends UserDto {

    private List<RecipeDto> ownRecipes;

    private List<CommentDto> comments;

    public List<RecipeDto> getOwnRecipes() {
        return ownRecipes;
    }

    public void setOwnRecipes(List<RecipeDto> ownRecipes) {
        this.ownRecipes = ownRecipes;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }
}
