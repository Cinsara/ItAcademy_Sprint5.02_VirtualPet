package virtualpet.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import virtualpet.dto.PetWithAccessoriesDto;
import virtualpet.dto.requests.BuyRequestAccessory;
import virtualpet.dto.requests.BuyRequestFood;
import virtualpet.dto.PetDto;
import virtualpet.model.Accessory;
import virtualpet.model.Food;
import virtualpet.model.User;
import virtualpet.repositories.AccessoryRepository;
import virtualpet.repositories.FoodRepository;
import virtualpet.services.PetService;
import virtualpet.services.ShopService;
import virtualpet.services.UserService;

import java.util.List;
import java.util.Map;

@SecurityRequirement(name = "bearerAuth")
@RestController
@AllArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private final UserService userService;
    private final AccessoryRepository accessoryRepository;
    private final FoodRepository foodRepository;
    private final ShopService shopService;

    @PostMapping("/buyFood")
    public ResponseEntity<PetDto> buyFood(@RequestBody BuyRequestFood request,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.userFound(userDetails);
        PetDto petDto = shopService.buyFood(user, request);
        return ResponseEntity.ok(petDto);
    }

    @PostMapping("/buyAccessory")
    public ResponseEntity<PetWithAccessoriesDto> buyAccessory(@RequestBody BuyRequestAccessory request,
                                                              @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.userFound(userDetails);
        PetWithAccessoriesDto dto = shopService.buyAccessory(user, request);
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

