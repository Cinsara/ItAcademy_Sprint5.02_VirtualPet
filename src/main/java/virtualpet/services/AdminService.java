package virtualpet.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import virtualpet.model.User;
import virtualpet.model.Pet;
import virtualpet.model.enums.UserRol;
import virtualpet.repositories.GameRepository;
import virtualpet.repositories.UserRepository;
import virtualpet.repositories.PetRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final GameRepository gameRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public void deletePet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        gameRepository.deleteByChallenger(pet);
        gameRepository.deleteByOpponent(pet);

        petRepository.delete(pet);
    }

    public User promoteUserToAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRol(UserRol.ADMIN);
        return userRepository.save(user);
    }

    public User updateUserDiamonds(Long userId, int newDiamonds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setDiamonds(newDiamonds);
        return userRepository.save(user);
    }

    public Pet updatePetStats(Long petId, Pet newStats) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        pet.setHealth(newStats.getHealth());
        pet.setHappiness(newStats.getHappiness());
        pet.setHunger(newStats.getHunger());
        pet.setStrength(newStats.getStrength());
        pet.setWeight(newStats.getWeight());

        return petRepository.save(pet);
    }
}
