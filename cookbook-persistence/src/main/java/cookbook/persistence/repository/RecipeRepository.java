package cookbook.persistence.repository;

import cookbook.persistence.entity.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findAll();
    Optional<Recipe> findById(Long id);
}
