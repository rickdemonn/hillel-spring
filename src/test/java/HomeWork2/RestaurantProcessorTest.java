package HomeWork2;

import org.junit.Test;

import java.util.*;


import static org.assertj.core.api.Assertions.*;


public class RestaurantProcessorTest {

    private List<Dish> dishList = Arrays.asList(
            new Dish("Cesar", 100, true, DishType.CHICKEN),
            new Dish("Meat", 200, true, DishType.BEEF),
            new Dish("Herb", 50, false, DishType.VEGETABLES),
            new Dish("ChickenRoll", 175, true, DishType.CHICKEN),
            new Dish("FreshSalad", 75, false, DishType.VEGETABLES),
            new Dish("Steak", 250, true, DishType.BEEF),
            new Dish("Potato", 100, true, DishType.VEGETABLES)
    );
    private Restaurant restaurant = new Restaurant(dishList);

    @Test
    public void printNamesOfDishTest() throws Exception{
        RestaurantProcessor.printNamesOfDish(restaurant);

        assertThat(restaurant.getMenu()).hasSize(7);
    }

    @Test
    public void pickLowCaloriesTest() throws Exception{
        Optional<Restaurant> restaurant = RestaurantProcessor.pickLowCalories(this.restaurant);
        if (restaurant.isPresent()) {
            assertThat(restaurant.get().getMenu()).hasSize(4);
        } else {
            fail("Restaurant should be present");
        }

    }

    @Test
    public void pickTopCaloriesTest() throws Exception{
        Restaurant restaurant = RestaurantProcessor.pickTopCalories(this.restaurant);

        assertThat(restaurant.getMenu().get(1).getCalories()).isEqualTo(200);
    }

    @Test
    public void printSortByTypeAndByNameTest() throws Exception{
        RestaurantProcessor.printSortByTypeAndByName(restaurant);

        assertThat(restaurant.getMenu()).hasSize(7);
    }

    @Test
    public void getAvgByTypeTest() {
        Map<DishType, Double> map = RestaurantProcessor.getAvgByType(restaurant);

        assertThat(map).hasSize(3);
    }

    @Test
    public void groupByTypeTest() {
        Map<DishType, List<Dish>> map = RestaurantProcessor.groupByType(restaurant);

        assertThat(map).hasSize(3);

    }

    @Test
    public void groupByBioToNameTest() {
        Map<DishType, List<String>> map = RestaurantProcessor.groupByBioToName(restaurant);

        Map<DishType, List<String>> mapToGo = new HashMap<>();
        mapToGo.put(DishType.BEEF, Arrays.asList("Meat", "Steak"));
        mapToGo.put(DishType.CHICKEN, Arrays.asList("Cesar", "ChickenRoll"));
        mapToGo.put(DishType.VEGETABLES, Collections.singletonList( "Potato"));

        assertThat(map).isEqualTo(mapToGo);
    }
}
