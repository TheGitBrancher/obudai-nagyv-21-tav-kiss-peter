package cookbook.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Cook extends User {

    @OneToMany(mappedBy = "uploader")
    private List<Recipe> ownRecipes;

    @OneToMany(mappedBy = "owner")
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
