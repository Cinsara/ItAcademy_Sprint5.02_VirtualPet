package virtualpet.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import virtualpet.model.Accessory;
import virtualpet.model.Food;
import virtualpet.model.FoodType;
import virtualpet.repositories.AccessoryRepository;
import virtualpet.repositories.FoodRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final FoodRepository foodRepository;
    private final AccessoryRepository accessoryRepository;

    @PostConstruct
    public void init() {
        initFood();
        initAccessories();
    }

    private void initFood() {
        if (foodRepository.count() == 0) {
            foodRepository.save(Food.builder()
                    .name("Apple")
                    .type(FoodType.HEALTHY)
                    .hungerChange(10)
                    .healthChange(15)
                    .happinessChange(5)
                    .weightChange(0.1)
                    .price(5)
                    .description("Fresh apple")
                    .imageUrl("/assets/shop/apple.png")
                    .build());

            foodRepository.save(Food.builder()
                    .name("Salad")
                    .type(FoodType.HEALTHY)
                    .hungerChange(8)
                    .healthChange(20)
                    .happinessChange(3)
                    .weightChange(0.05)
                    .price(6)
                    .description("Light green salad")
                    .imageUrl("/assets/shop/salad.png")
                    .build());

            foodRepository.save(Food.builder()
                    .name("Burger")
                    .type(FoodType.JUNK)
                    .hungerChange(30)
                    .healthChange(-10)
                    .happinessChange(20)
                    .weightChange(0.5)
                    .price(12)
                    .description("Greasy burger")
                    .imageUrl("/assets/shop/burger.png")
                    .build());

            foodRepository.save(Food.builder()
                    .name("Donut")
                    .type(FoodType.JUNK)
                    .hungerChange(15)
                    .healthChange(-5)
                    .happinessChange(25)
                    .weightChange(0.3)
                    .price(4)
                    .description("Sweet donut")
                    .imageUrl("/assets/shop/donut.png")
                    .build());
        }
    }

    private void initAccessories() {
        if (accessoryRepository.count() == 0) {
            accessoryRepository.save(Accessory.builder()
                    .name("Hat")
                    .happinessChange(10)
                    .price(7)
                    .description("A cute little hat")
                    .imageUrl("/assets/shop/hat.png")
                    .build());

            accessoryRepository.save(Accessory.builder()
                    .name("Teddy Bear")
                    .happinessChange(15)
                    .price(10)
                    .description("A soft and cuddly teddy bear")
                    .imageUrl("/assets/shop/bear.png")
                    .build());

            accessoryRepository.save(Accessory.builder()
                    .name("Glasses")
                    .happinessChange(20)
                    .price(5)
                    .description("Some super cool glasses")
                    .imageUrl("/assets/shop/glasses.png")
                    .build());
        }
    }
}
