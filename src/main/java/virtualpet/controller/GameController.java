package virtualpet.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import virtualpet.dto.GameDto;
import virtualpet.model.Game;
import virtualpet.model.Pet;
import virtualpet.model.User;
import virtualpet.repositories.GameRepository;
import virtualpet.repositories.PetRepository;
import virtualpet.services.GameService;
import virtualpet.services.UserService;

import java.util.List;
import java.util.Optional;

@SecurityRequirement(name = "bearerAuth")
@RestController
@AllArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;
    private final UserService userService;
    private final GameRepository gameRepository;
    private final PetRepository petRepository;

    @PostMapping("/newGame")
    public ResponseEntity<GameDto> newGame(@AuthenticationPrincipal UserDetails userDetails){
        User user = userService.userFound(userDetails);
        GameDto newBattle = gameService.startBattle(user);
        return ResponseEntity.ok(newBattle);
    }

    @GetMapping("/gamesPlayed")
    public ResponseEntity<?> userGames(@AuthenticationPrincipal UserDetails userDetails){
        User user = userService.userFound(userDetails);
        Optional<Pet> optionalPet = petRepository.findByOwner(user);

        if (optionalPet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("This user doesn't have a pet. They need a pet and games to view their history.");
        }

        Pet pet = optionalPet.get();
        List<Game> gameList = gameRepository.findByChallenger(pet);
        return ResponseEntity.ok(gameList);
    }
}
