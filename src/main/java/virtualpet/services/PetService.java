package virtualpet.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import virtualpet.dto.requests.PetRequest;
import virtualpet.model.Accessory;
import virtualpet.model.Food;
import virtualpet.model.Pet;
import virtualpet.model.User;
import virtualpet.repositories.AccessoryRepository;
import virtualpet.repositories.FoodRepository;
import virtualpet.repositories.PetRepository;
import virtualpet.repositories.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final AccessoryRepository accessoryRepository;
    private final UserService userService;

    public Pet createPet(PetRequest petRequest, UserDetails userDetails){
        User owner = userService.userFound(userDetails);
        Pet pet = new Pet();
        pet.setName(petRequest.getPetName());
        pet.setType(petRequest.getType());
        pet.setOwner(owner);
        pet.setWeight(owner.getWeight());
        return petRepository.save(pet);
    }

    public Pet showMyPet(UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return petRepository.findByOwner(user)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
    }

    public List<Pet> allPets(){
        return petRepository.findAll();
    }

    public Pet feedPet(User user, int foodId){
        Pet pet = petRepository.findByOwner(user)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Food food = foodRepository.findById((long) foodId)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        pet.setHealth(Math.min(100, Math.max(0, pet.getHealth() + food.getHealthChange())));
        pet.setHappiness(Math.min(100, Math.max(0, pet.getHappiness() + food.getHappinessChange())));
        pet.setHunger(Math.min(100, Math.max(0, pet.getHunger() - food.getHungerChange())));
        pet.setWeight(Math.max(0, pet.getWeight() + food.getWeightChange()));

        return petRepository.save(pet);
    }

    public Pet giveAccessory(User user, int accessoryId) {
        Pet pet = petRepository.findByOwner(user)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        Accessory accessory = accessoryRepository.findById((long) accessoryId)
                .orElseThrow(() -> new RuntimeException("Accessory not found"));

        pet.setHappiness(Math.min(100, Math.max(0, pet.getHappiness() + accessory.getHappinessChange())));

        return petRepository.save(pet);
    }

    public Pet trainPet(User user, int durationInSeconds) {
        Pet pet = petRepository.findByOwner(user)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        int minutes = durationInSeconds / 60;

        pet.setStrength(pet.getStrength() + minutes);
        pet.setHappiness(Math.max(0, pet.getHappiness() - minutes));
        pet.setHealth(Math.max(0, pet.getHealth() - (minutes / 2)));
        pet.setHunger(Math.min(100, pet.getHunger() + minutes));

        return petRepository.save(pet);
    }

    public void deletePet(User user){
        Pet deletedPet = petRepository.findByOwner(user)
                .orElseThrow(() -> new RuntimeException("Pet not found for this user"));
        petRepository.delete(deletedPet);
    }

    public Pet petFound(UserDetails userDetails){
        User user = userService.userFound(userDetails);
        return petRepository.findByOwner(user)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
    }
}
