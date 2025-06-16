package virtualpet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import virtualpet.model.Accessory;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory,Long> {
}
