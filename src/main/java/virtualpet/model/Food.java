package virtualpet.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "app_food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double calories;
    private int healthChange;
    private int happinessChange;
    private int hungerChange;
    private double weightChange;
    private int price;
    private String description;
}
