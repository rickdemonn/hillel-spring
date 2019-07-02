package HomeWork2;

import lombok.Data;

@Data
public class Dish {
    private final String name;
    private final Integer calories;
    private final Boolean isBio;
    private final DishType type;

}
