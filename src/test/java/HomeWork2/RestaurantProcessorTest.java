package HomeWork2;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


public class RestaurantProcessorTest {

    List<Dish> dishList = Arrays.asList(
            new Dish("Cesar", 100, true, DishType.CHICKEN),
            new Dish("Meat", 200, true, DishType.BEEF),
            new Dish("Herb", 50, false, DishType.VEGETABLES),
            new Dish("ChickenRoll", 175, true, DishType.CHICKEN),
            new Dish("FreshSalad", 75, false, DishType.VEGETABLES),
            new Dish("Steak", 250, true, DishType.BEEF),
            new Dish("Potato", 100, true, DishType.VEGETABLES)
    );
    Restaurant restaurant = new Restaurant(dishList);

    @Test
    public void printNamesOfDishTest() throws Exception{
        RestaurantProcessor.printNamesOfDish(restaurant);

        assertThat(restaurant.getMenu()).hasSize(7);
    }

    @Test
    public void pickLowCaloriesTest() throws Exception{
        Optional<Restaurant> restaurant = RestaurantProcessor.pickLowCalories(this.restaurant);

        assertThat(restaurant.get().getMenu()).hasSize(4);
    }

    @Test
    public void pickTopCaloriesTest() throws Exception{
        Restaurant restaurant = RestaurantProcessor.pickTopCalories(this.restaurant);

        assertThat(restaurant.getMenu()).hasSize(3);
    }

    @Test
    public void printSortByTypeAndByNameTest() throws Exception{
        RestaurantProcessor.printSortByTypeAndByName(restaurant);

        assertThat(restaurant.getMenu()).hasSize(7);
    }

    @Test
    public void getAvgByTypeTest() throws Exception {
        Map<DishType, Double> map = RestaurantProcessor.getAvgByType(restaurant);

        assertThat(map).hasSize(3);
    }

    @Test
    public void groupByTypeTest() throws Exception {
        Map<DishType, List<Dish>> map = RestaurantProcessor.groupByType(restaurant);

        assertThat(map).hasSize(3);

    }

    @Test
    public void groupByBioName() throws Exception {
        Map<DishType, List<String>> map = RestaurantProcessor.groupByBio(restaurant);

        assertThat(map).hasSize(3);
    }
}
