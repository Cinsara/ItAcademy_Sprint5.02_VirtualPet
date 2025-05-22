package virtualpet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import virtualpet.model.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
}
