package virtualpet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import virtualpet.model.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food,Long> {
}
