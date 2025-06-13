package virtualpet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import virtualpet.model.Game;
import virtualpet.model.Pet;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {
    List<Game> findByChallenger(Pet challenger);
    void deleteByChallenger(Pet pet);
    void deleteByOpponent(Pet pet);

}
