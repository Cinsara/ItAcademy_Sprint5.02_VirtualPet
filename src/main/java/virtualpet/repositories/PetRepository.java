package virtualpet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import virtualpet.model.Pet;
import virtualpet.model.User;

import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
    Optional<Pet> findByOwner(User owner);

    @Query(value = "SELECT * FROM app_pet WHERE id != :excludedId ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Pet> findRandomPetExcluding(@Param("excludedId") int excludedId);

}
