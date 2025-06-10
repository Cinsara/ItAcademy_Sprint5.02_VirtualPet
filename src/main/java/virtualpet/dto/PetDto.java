package virtualpet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import virtualpet.model.Accessory;
import virtualpet.model.Pet;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PetDto {
    private Long id;
    private String name;
    private int happiness;
    private int health;
    private int hunger;
    private int strength;
    private int victories;
    private int defeats;
    private double weight;
    private List<String> equippedAccessories;

    public PetDto(Long id, String name, int happiness, int health, int hunger, int strength,
                  int victories, int defeats, double weight) {
        this.id = id;
        this.name = name;
        this.happiness = happiness;
        this.health = health;
        this.hunger = hunger;
        this.strength = strength;
        this.victories = victories;
        this.defeats = defeats;
        this.weight = Math.round(weight * 10.0) / 10.0;
    }
}
