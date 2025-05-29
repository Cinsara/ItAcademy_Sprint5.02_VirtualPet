package virtualpet.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import virtualpet.model.Food;
import virtualpet.model.FoodType;
import virtualpet.repositories.FoodRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final FoodRepository foodRepository;

    @PostConstruct
    public void init() {
        if (foodRepository.count() == 0) {
            // HEALTHY FOODS
            foodRepository.save(Food.builder()
                    .name("Apple")
                    .type(FoodType.HEALTHY)
                    .hungerChange(10)
                    .healthChange(15)
                    .happinessChange(5)
                    .weightChange(0.1)
                    .price(5)
                    .description("Fresh apple")
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
                    .build());

            // JUNK FOODS
            foodRepository.save(Food.builder()
                    .name("Burger")
                    .type(FoodType.JUNK)
                    .hungerChange(30)
                    .healthChange(-10)
                    .happinessChange(20)
                    .weightChange(0.5)
                    .price(12)
                    .description("Greasy burger")
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
                    .build());
        }
    }
}
