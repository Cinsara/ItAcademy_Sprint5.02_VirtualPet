package virtualpet.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import virtualpet.model.Game;
import virtualpet.model.Pet;
import virtualpet.model.User;
import virtualpet.repositories.GameRepository;
import virtualpet.services.GameService;
import virtualpet.services.PetService;
import virtualpet.services.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;
    private final UserService userService;
    private final PetService petService;
    private final GameRepository gameRepository;

    @PostMapping("/newGame")
    public ResponseEntity<Game> newGame(@AuthenticationPrincipal UserDetails userDetails){
        User user = userService.userFound(userDetails);
        Game newBattle = gameService.startBattle(user);
        return ResponseEntity.ok(newBattle);
    }

    @GetMapping("/gamesPlayed")
    public ResponseEntity<List<Game>> userGames(@AuthenticationPrincipal UserDetails userDetails){
        User user = userService.userFound(userDetails);
        Pet pet = petService.petFound(userDetails);
        List<Game> gameList = gameRepository.findByChallenger(pet);
        return ResponseEntity.ok(gameList);
    }
}
