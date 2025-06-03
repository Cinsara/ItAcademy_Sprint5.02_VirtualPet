package virtualpet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private FoodType type;

    @ManyToOne
    @JoinColumn(name = "app_shop_id")
    private Shop shop;
}
