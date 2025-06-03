package virtualpet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import virtualpet.model.Game;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {
    private Long id;
    private String opponentName;
    private String challengerName;
    private String result;
    private int coinsAwarded;

    public static GameDto from(Game game) {
        String result = switch (game.getGameResult()) {
            case CHALLENGER_WINS -> "VICTORY";
            case OPPONENT_WINS -> "DEFEAT";
            case DRAW -> "DRAW";
        };

        return new GameDto(
                (long) game.getId(),
                game.getOpponent().getName(),
                game.getChallenger().getName(),
                result,
                game.getCoinsAwarded()
        );
    }
}
