package virtualpet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetWithAccessoriesDto {
    private String name;
    private String type;
    private int happiness;
    private int health;
    private int hunger;
    private int strength;
    private int victories;
    private int defeats;
    private double weight;
    private List<AccessoryDto> accessories;
}