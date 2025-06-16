package virtualpet.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import virtualpet.dto.AccessoryDto;
import virtualpet.dto.PetDto;
import virtualpet.dto.PetWithAccessoriesDto;
import virtualpet.dto.requests.BuyRequestAccessory;
import virtualpet.dto.requests.BuyRequestFood;
import virtualpet.model.Accessory;
import virtualpet.model.Food;
import virtualpet.model.Pet;
import virtualpet.model.User;
import virtualpet.repositories.AccessoryRepository;
import virtualpet.repositories.FoodRepository;
import virtualpet.repositories.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ShopService {
    private final UserService userService;
    private final FoodRepository foodRepository;
    private final AccessoryRepository accessoryRepository;
    private final UserRepository userRepository;
    private final PetService petService;

    public PetDto buyFood(User user, BuyRequestFood request){
        Food food = foodRepository.findById((long) request.getFoodId())
                .orElseThrow(() -> new RuntimeException("Food not found"));

        if (user.getDiamonds() < food.getPrice()) {
            throw new RuntimeException("Not enough diamonds");
        }

        user.setDiamonds(user.getDiamonds() - food.getPrice());
        userRepository.save(user);

        Pet updated = petService.feedPet(user, request.getFoodId());

        return new PetDto(
                (long) updated.getId(),
                updated.getName(),
                updated.getHappiness(),
                updated.getHealth(),
                updated.getHunger(),
                updated.getStrength(),
                updated.getVictories(),
                updated.getDefeats(),
                updated.getWeight()
        );
    }

    public PetWithAccessoriesDto buyAccessory(User user, BuyRequestAccessory request){

        Accessory accessory = accessoryRepository.findById((long) request.getAccessoryId())
                .orElseThrow(() -> new RuntimeException("Accessory not found"));

        if (user.getDiamonds() < accessory.getPrice()) {
            throw new RuntimeException("Not enough diamonds");
        }

        user.setDiamonds(user.getDiamonds() - accessory.getPrice());
        userRepository.save(user);

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

        return dto;
    }
}
