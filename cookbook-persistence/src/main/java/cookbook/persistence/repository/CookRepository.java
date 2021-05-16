package cookbook.persistence.repository;

import cookbook.persistence.entity.Cook;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CookRepository extends CrudRepository<Cook, Long> {
    List<Cook> findAll();
}
