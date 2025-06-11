package virtualpet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import virtualpet.model.Game;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {
    private PetDto challenger;
    private PetDto opponent;
    private String gameResult;
    private int coinsAwarded;

    public static GameDto from(Game game) {
        PetDto challengerDto = new PetDto(game.getChallenger());
        PetDto opponentDto = new PetDto(game.getOpponent());
        String result = game.getGameResult().name();

        return new GameDto(challengerDto, opponentDto, result, game.getCoinsAwarded());
    }
}
