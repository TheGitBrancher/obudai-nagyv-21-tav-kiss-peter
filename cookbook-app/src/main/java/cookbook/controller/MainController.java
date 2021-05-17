package cookbook.controller;

import cookbook.service.Service;
import cookbook.service.dto.RecipeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    Service service;

    @GetMapping("/")
    public String getRecipes(Model model) {
        model.addAttribute("recipes", service.getRecipes());
        return "listRecipes";
    }

    @GetMapping("/recipe/{id}")
    public String recipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", service.getRecipeById(id));
        return "recipeById";
    }

    @GetMapping("/myRecipes")
    public String myRecipes(Model model) {
        model.addAttribute("myRecipes", service.getMyRecipes());
        return "myRecipes";
    }

    @PostMapping("/addComment")
    public String addComment(Long recipeId, String description) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipeId);
        service.saveComment(recipeDto, description);
        return "redirect:/recipe/" + recipeId;
    }

    @GetMapping("/newRecipe")
    public String newRecipe(Model model) {
        model.addAttribute("categoryOptions", service.getCategoires());
        return "newRecipe";
    }

    @PostMapping("/delete")
    public String delete(Long recipeId) {
        service.deleteRecipe(recipeId);
        return "redirect:/myRecipes";
    }
}
