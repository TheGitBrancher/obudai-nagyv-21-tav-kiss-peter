package cookbook.controller;

import cookbook.service.Service;
import cookbook.models.AddRecipeDto;
import cookbook.service.dto.RecipeDto;
import cookbook.service.transformer.RecipeTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    Service service;

    @Autowired
    RecipeTransformer recipeTransformer;

    @GetMapping("/")
    public String getRecipes(@RequestParam(required = false) String search, @RequestParam(required = false) List<String> filter, Model model) {
        model.addAttribute("recipes", service.getRecipes(search, filter));
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
    public String newRecipe(AddRecipeDto addRecipeDto, Model model) {
        model.addAttribute("categoryOptions", service.getCategories());
        model.addAttribute("addRecipeDto", new AddRecipeDto());
        return "newRecipe";
    }


    @PostMapping("/addRecipe")
    public String addRecipe(@Valid AddRecipeDto addRecipeDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryOptions", service.getCategories());
            model.addAttribute("addRecipeDto", addRecipeDto);
            return "newRecipe";
        }

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setCategories(addRecipeDto.getCategories());
        recipeDto.setName(addRecipeDto.getName());
        recipeDto.setIngredients(recipeTransformer.toIngredients(addRecipeDto.getIngredients()));
        recipeDto.setPreparation(addRecipeDto.getPreparation());
        recipeDto.setServings(addRecipeDto.getServings());
        service.addRecipe(recipeDto);
        return "redirect:/myRecipes";
    }

    @PostMapping("/delete")
    public String delete(Long recipeId) {
        service.deleteRecipe(recipeId);
        return "redirect:/myRecipes";
    }
}
