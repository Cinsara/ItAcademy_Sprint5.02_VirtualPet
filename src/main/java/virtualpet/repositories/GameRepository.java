package virtualpet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import virtualpet.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {
}
