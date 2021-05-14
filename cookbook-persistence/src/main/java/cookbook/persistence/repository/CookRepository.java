package cookbook.persistence.repository;

import cookbook.persistence.entity.Cook;
import org.springframework.data.repository.CrudRepository;

public interface CookRepository extends CrudRepository<Cook, Long> {
}
