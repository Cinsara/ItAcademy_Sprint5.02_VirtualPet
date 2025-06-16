package virtualpet.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import virtualpet.model.Pet;
import virtualpet.model.enums.TrainingType;
import virtualpet.model.User;
import virtualpet.repositories.PetRepository;

@Service
@RequiredArgsConstructor
public class TrainService {
    private final PetRepository petRepository;

    public Pet train(User user, TrainingType type, int durationInSeconds) {
        Pet pet = petRepository.findByOwner(user)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        int minutes = durationInSeconds / 10;

        double totalWeightLoss = calculateWeightLoss(user, type, minutes);
        pet.setWeight(Math.max(0, pet.getWeight() - totalWeightLoss));

        applyTrainingEffects(pet, type, minutes);

        return petRepository.save(pet);
    }

    private double calculateWeightLoss(User user, TrainingType type, int minutes) {
        double bodyFactor = switch (user.getBodyType()) {
            case ECTOMORPH -> 0.5;
            case MESOMORPH -> 0.35;
            case ENDOMORPH -> 0.2;
            default -> 0.3;
        };

        double trainingMultiplier = switch (type) {
            case CARDIO -> 1.0;
            case STRENGTH -> 0.5;
            case YOGA -> 0.3;
            case HIIT -> 1.5;
            case FUN -> 0.7;
        };

        return bodyFactor * trainingMultiplier * minutes;
    }

    private void applyTrainingEffects(Pet pet, TrainingType type, int minutes) {
        switch (type) {
            case CARDIO -> applyCardioEffects(pet, minutes);
            case STRENGTH -> applyStrengthEffects(pet, minutes);
            case YOGA -> applyYogaEffects(pet, minutes);
            case HIIT -> applyHiitEffects(pet, minutes);
            case FUN -> applyFunEffects(pet, minutes);
        }
    }

    private void applyCardioEffects(Pet pet, int minutes) {
        pet.setStrength(pet.getStrength() + (minutes / 2));
        pet.setHappiness(pet.getHappiness() - minutes);
        pet.setHealth(pet.getHealth() - (minutes / 2));
        pet.setHunger(Math.min(100, pet.getHunger() + (minutes + 2)));
    }

    private void applyStrengthEffects(Pet pet, int minutes) {
        pet.setStrength(pet.getStrength() + minutes);
        pet.setHealth(pet.getHealth() - (minutes / 2));
        pet.setHappiness(pet.getHappiness() - (minutes / 2));
        pet.setHunger(Math.min(100, pet.getHunger() + minutes));
    }

    private void applyYogaEffects(Pet pet, int minutes) {
        pet.setHappiness(pet.getHappiness() + minutes);
        pet.setHealth(pet.getHealth() + (minutes / 2));
        pet.setHunger(Math.min(100, pet.getHunger() + (minutes / 2)));
    }

    private void applyHiitEffects(Pet pet, int minutes) {
        pet.setStrength(pet.getStrength() + (minutes * 2));
        pet.setHealth(pet.getHealth() - minutes);
        pet.setHappiness(pet.getHappiness() + (minutes / 2));
        pet.setHunger(Math.min(100, pet.getHunger() + (minutes * 2)));
    }

    private void applyFunEffects(Pet pet, int minutes) {
        pet.setHappiness(pet.getHappiness() + (minutes * 2));
        pet.setStrength(pet.getStrength() + (minutes / 2));
        pet.setHunger(Math.min(100, pet.getHunger() + minutes));
    }
}
