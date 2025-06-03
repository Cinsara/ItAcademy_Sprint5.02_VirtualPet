package virtualpet.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import virtualpet.dto.BuyRequestAccessory;
import virtualpet.dto.BuyRequestFood;
import virtualpet.dto.PetDto;
import virtualpet.model.Pet;
import virtualpet.model.User;
import virtualpet.services.PetService;
import virtualpet.services.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private final PetService petService;
    private final UserService userService;

    @PostMapping("/buyFood")
    public ResponseEntity<PetDto> buyFood(@RequestBody BuyRequestFood request,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.userFound(userDetails);
        Pet updated = petService.feedPet(user, request.getFoodId()); // usa tu método feed
        return ResponseEntity.ok(new PetDto(
                (long) updated.getId(),
                updated.getName(),
                updated.getHappiness(),
                updated.getHealth(),
                updated.getHunger(),
                updated.getStrength(),
                updated.getVictories(),
                updated.getDefeats(),
                updated.getWeight()
        ));
    }

    @PostMapping("/buyAccessory")
    public ResponseEntity<PetDto> buyAccessory(@RequestBody BuyRequestAccessory request,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.userFound(userDetails);
        Pet updated = petService.giveAccessory(user, request.getAccessoryId());
        return ResponseEntity.ok(new PetDto(
                (long) updated.getId(),
                updated.getName(),
                updated.getHappiness(),
                updated.getHealth(),
                updated.getHunger(),
                updated.getStrength(),
                updated.getVictories(),
                updated.getDefeats(),
                updated.getWeight()
        ));
    }
}

