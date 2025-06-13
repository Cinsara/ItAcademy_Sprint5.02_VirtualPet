package virtualpet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "opponent_pet_id")
    private Pet opponent;

    @ManyToOne
    @JoinColumn(name = "challenger_pet_id")
    private Pet challenger;

    private GameResult gameResult;
    private int coinsAwarded;
}
