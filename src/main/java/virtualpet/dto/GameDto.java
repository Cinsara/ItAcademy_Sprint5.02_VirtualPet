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
        GameDto dto = new GameDto();
        dto.setChallenger(new PetDto(game.getChallenger()));
        dto.setOpponent(new PetDto(game.getOpponent()));
        dto.setGameResult(String.valueOf(game.getGameResult()));
        dto.setCoinsAwarded(game.getCoinsAwarded());
        return dto;
    }
}
