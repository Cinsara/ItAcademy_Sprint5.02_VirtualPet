package virtualpet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
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
}
