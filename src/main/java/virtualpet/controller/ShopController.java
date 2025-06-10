package virtualpet.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import virtualpet.dto.AccessoryDto;
import virtualpet.dto.PetWithAccessoriesDto;
import virtualpet.dto.requests.BuyRequestAccessory;
import virtualpet.dto.requests.BuyRequestFood;
import virtualpet.dto.PetDto;
import virtualpet.model.Accessory;
import virtualpet.model.Food;
import virtualpet.model.Pet;
import virtualpet.model.User;
import virtualpet.repositories.AccessoryRepository;
import virtualpet.repositories.FoodRepository;
import virtualpet.services.PetService;
import virtualpet.services.UserService;

import java.util.List;
import java.util.Map;

@SecurityRequirement(name = "bearerAuth")
@RestController
@AllArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private final PetService petService;
    private final UserService userService;
    private final AccessoryRepository accessoryRepository;
    private final FoodRepository foodRepository;

    @PostMapping("/buyFood")
    public ResponseEntity<PetDto> buyFood(@RequestBody BuyRequestFood request,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.userFound(userDetails);
        Pet updated = petService.feedPet(user, request.getFoodId());
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

   /* @PostMapping("/buyAccessory")
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
    } */

    @PostMapping("/buyAccessory")
    public ResponseEntity<PetWithAccessoriesDto> buyAccessory(@RequestBody BuyRequestAccessory request,
                                                              @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.userFound(userDetails);
        Pet updated = petService.giveAccessory(user, request.getAccessoryId());

        List<AccessoryDto> accessories = updated.getAccessories().stream()
                .map(acc -> new AccessoryDto(acc.getId(), acc.getName(), acc.getImageUrl()))
                .toList();

        PetWithAccessoriesDto dto = new PetWithAccessoriesDto(
                updated.getName(),
                updated.getType(),
                updated.getHappiness(),
                updated.getHealth(),
                updated.getHunger(),
                updated.getStrength(),
                updated.getVictories(),
                updated.getDefeats(),
                updated.getWeight(),
                accessories
        );

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/items")
    public Map<String, List<?>> getAllItems() {
        List<Food> foodList = foodRepository.findAll();
        List<Accessory> accessoryList = accessoryRepository.findAll();
        return Map.of(
                "food", foodList,
                "accessories", accessoryList
        );
    }
}

