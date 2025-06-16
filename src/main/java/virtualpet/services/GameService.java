package virtualpet.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import virtualpet.dto.GameDto;
import virtualpet.model.Game;
import virtualpet.model.enums.GameResult;
import virtualpet.model.Pet;
import virtualpet.model.User;
import virtualpet.repositories.GameRepository;
import virtualpet.repositories.PetRepository;
import virtualpet.repositories.UserRepository;

@Service
@AllArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public GameDto startBattle(User user){
        Pet challenger = getChallenger(user);
        Pet opponent = getRandomOpponent(challenger);

        GameResult result = determineBattleResult(challenger,opponent);
        int diamonds = getCoinsForResult(result);
        user.setDiamonds(user.getDiamonds() + diamonds);
        userRepository.save(user);

        updatePetStats(challenger,result);
        petRepository.save(challenger);

        challenger = petRepository.findById((long) challenger.getId())
                .orElseThrow(() -> new RuntimeException("Challenger not found"));

        Game battle = createGameRecord(challenger,opponent,result,diamonds);
        return GameDto.from(gameRepository.save(battle));
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
            case CHALLENGER_WINS -> 10;
            case DRAW -> 5;
            case OPPONENT_WINS -> 0;
        };
    }

    private void updatePetStats(Pet pet, GameResult result) {
        switch (result) {
            case CHALLENGER_WINS -> {
                pet.setVictories(pet.getVictories() + 1);
                pet.setHappiness(Math.min(pet.getHappiness() + 10, 100));
                pet.setHealth(Math.min(pet.getHealth() + 5, 100));
                pet.setHunger(Math.min(pet.getHunger() + 8, 100));
            }

            case OPPONENT_WINS -> {
                pet.setDefeats(pet.getDefeats() + 1);
                pet.setHappiness(Math.max(pet.getHappiness() - 5, 0));
                pet.setHealth(Math.max(pet.getHealth() - 5, 0));
                pet.setHunger(Math.min(pet.getHunger() + 5, 100));
            }

            case DRAW -> {
                pet.setHappiness(Math.min(pet.getHappiness() + 2, 100));
                pet.setHunger(Math.min(pet.getHunger() + 5, 100));
            }
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
        int score = pet.getStrength() * 2 + pet.getHealth() + pet.getHappiness() - pet.getHunger();
        int scoreRandom = (int)(Math.random() * score) + 1;
        System.out.println("Pet name: " + pet.getName() + " | Score: " + score + " | Score Random: " + scoreRandom);

        return scoreRandom;
    }
}
