package virtualpet.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import virtualpet.model.Game;
import virtualpet.model.GameResult;
import virtualpet.model.Pet;
import virtualpet.model.User;
import virtualpet.repositories.GameRepository;
import virtualpet.repositories.PetRepository;
import virtualpet.repositories.UserRepository;

import java.util.Random;

@Service
@AllArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public Game startBattle(User user){
        Pet challenger = getChallenger(user);
        Pet opponent = getRandomOpponent(challenger);

        GameResult result = determineBattleResult(challenger,opponent);
        int coins = getCoinsForResult(result);

        updatePetStats(challenger,result);
        Game battle = createGameRecord(challenger,opponent,result,coins);

        petRepository.save(challenger);
        return gameRepository.save(battle);
    }


    private Pet getChallenger(User user) {
        return petRepository.findByOwner(user)
                .orElseThrow(() -> new RuntimeException("You don't have a pet"));
    }

    private Pet getRandomOpponent(Pet challenger) {
        return petRepository.findRandomPetExcluding(challenger.getId())
                .orElseThrow(() -> new RuntimeException("No opponents found"));
    }

    private GameResult determineBattleResult(Pet challenger, Pet opponent) {
        int challengerScore = calculateScore(challenger);
        int opponentScore = calculateScore(opponent);

        if (challengerScore > opponentScore) return GameResult.CHALLENGER_WINS;
        if (challengerScore < opponentScore) return GameResult.OPPONENT_WINS;
        return GameResult.DRAW;
    }

    private int getCoinsForResult(GameResult result) {
        return switch (result) {
            case CHALLENGER_WINS -> 50;
            case DRAW -> 20;
            case OPPONENT_WINS -> 10;
        };
    }

    private void updatePetStats(Pet pet, GameResult result) {
        switch (result) {
            case CHALLENGER_WINS -> pet.setVictories(pet.getVictories() + 1);
            case OPPONENT_WINS -> pet.setDefeats(pet.getDefeats() + 1);
        }
    }

    private Game createGameRecord(Pet challenger, Pet opponent, GameResult result, int coins) {
        Game game = new Game();
        game.setChallenger(challenger);
        game.setOpponent(opponent);
        game.setGameResult(result);
        game.setCoinsAwarded(coins);
        return game;
    }

    private int calculateScore(Pet pet) {
        int random = new Random().nextInt(21) - 10;
        return pet.getStrength() * 2 + pet.getHealth() + pet.getHappiness() - pet.getHunger() + random;
    }
}
