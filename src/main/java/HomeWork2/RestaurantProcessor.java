package HomeWork2;

import java.util.*;

import java.util.stream.Collectors;

public class RestaurantProcessor {

    public static void printNamesOfDish(Restaurant restaurant) {
        /*for (Dish menu: restaurant.getMenu()) {
            System.out.println(menu.getName());
        }*/

        restaurant.getMenu()
                .forEach(a -> System.out.println(a.getName()));
    }


    public static Optional<Restaurant> pickLowCalories(Restaurant restaurant) {
       /* List<Dish> dishList = restaurant.getMenu();
        List<Dish> newList = new ArrayList<>();

        for (Dish dish: dishList) {
            if(dish.getCalories() < 175){
                newList.add(dish);
            }
        }
        for (Dish dish: newList) {
            System.out.println(dish.getName());
        }

        Restaurant newRes = new Restaurant(newList);
        return Optional.of(newRes);*/


        List<Dish> newDish = restaurant.getMenu().stream()
                .filter(dish -> dish.getCalories() < 175)
                .peek(System.out::println)
                .collect(Collectors.toList());
        return Optional.of(new Restaurant(newDish));
    }

    public static Restaurant pickTopCalories(Restaurant restaurant) {
       /* List<Dish> dishList = restaurant.getMenu();

        dishList.sort(getCompByCalories());

        List<Dish> newList = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            newList.add(dishList.get(i));
            System.out.println(newList.get(i).getName());
        }*/

        return new Restaurant(restaurant.getMenu().stream()
                .sorted(getCompByCaloriesReverse())
                .limit(3)
                .peek(System.out::println)
                .collect(Collectors.toList()));


    }

    public static void printSortByTypeAndByName(Restaurant restaurant) {
        /*List<Dish> dishList = restaurant.getMenu();

        dishList.sort(getCompByType());

        for (Dish dish: dishList) {
            System.out.println(dish.getName());
        }

        dishList.sort(getCompByName());

        for (Dish dish: dishList) {
            System.out.println(dish.getName());
        }*/

        restaurant.getMenu().stream()
                .sorted(getCompByType())
                .peek(System.out::println)
                .sorted(getCompByName())
                .forEach(System.out::println);
    }

    public static Map<DishType, Double> getAvgByType(Restaurant restaurant) {
        /*List<Dish> dishList = restaurant.getMenu();
        
        Map<DishType, Double> mapOfAvg = new HashMap<>();

        List<Integer> beef = new ArrayList<>();
        List<Integer> chicken = new ArrayList<>();
        List<Integer> vegetables = new ArrayList<>();
        for (Dish dish: dishList) {
            if(dish.getType() == DishType.BEEF){
                beef.add(dish.getCalories());
            } else if (dish.getType() == DishType.CHICKEN) {
                chicken.add(dish.getCalories());
            } else {
                vegetables.add(dish.getCalories());
            }
        }

        mapOfAvg.put(DishType.BEEF, calculateAverage(beef));
        mapOfAvg.put(DishType.CHICKEN, calculateAverage(chicken));
        mapOfAvg.put(DishType.VEGETABLES, calculateAverage(vegetables));

        for (Map.Entry<DishType, Double> entry : mapOfAvg.entrySet()) {
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
        return mapOfAvg;*/

        Map<DishType, Double> typeToAvg = restaurant.getMenu().stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.averagingInt(Dish::getCalories)));

        typeToAvg.entrySet().forEach(System.out::println);

        return typeToAvg;

    }

    public static Map<DishType, List<Dish>> groupByType(Restaurant restaurant) {
        /*Map<DishType, List<Dish>> typeToDishList = new HashMap<>();
        List<Dish> beef = new ArrayList<>();
        List<Dish> chicken = new ArrayList<>();
        List<Dish> vegetables = new ArrayList<>();
        for (Dish dish: restaurant.getMenu()) {
            if(dish.getType() == DishType.BEEF){
                beef.add(dish);
            } else if (dish.getType() == DishType.CHICKEN) {
                chicken.add(dish);
            } else {
                vegetables.add(dish);
            }
        }

        typeToDishList.put(DishType.BEEF, beef);
        typeToDishList.put(DishType.CHICKEN, chicken);
        typeToDishList.put(DishType.VEGETABLES, vegetables);

        for (Map.Entry<DishType, List<Dish>> entry : typeToDishList.entrySet()) {
            System.out.println(entry.getKey()+" : "+entry.getValue().toString());
        }
        return typeToDishList;*/


        Map<DishType, List<Dish>> typeToDishList = restaurant.getMenu().stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.toList()));

        typeToDishList.entrySet().forEach(System.out::println);

        return typeToDishList;
    }

    public static Map<DishType, List<String>> groupByBioToName(Restaurant restaurant) {
        /*Map<DishType, List<String>> typeToName = new HashMap<>();

        List<String> beef = new ArrayList<>();
        List<String> chicken = new ArrayList<>();
        List<String> vegetables = new ArrayList<>();
        for (Dish dish: restaurant.getMenu()) {
            if(dish.getType() == DishType.BEEF && dish.getIsBio()){
                beef.add(dish.getName());
            } else if (dish.getType() == DishType.CHICKEN && dish.getIsBio()) {
                chicken.add(dish.getName());
            } else if(dish.getIsBio()){
                vegetables.add(dish.getName());
            }
        }

        typeToName.put(DishType.BEEF, beef);
        typeToName.put(DishType.CHICKEN, chicken);
        typeToName.put(DishType.VEGETABLES, vegetables);

        for (Map.Entry<DishType, List<String>> entry : typeToName.entrySet()) {
            System.out.println(entry.getKey()+" : "+entry.getValue().toString());
        }

        return typeToName;*/

        Map<DishType, List<String>> typeToName = restaurant.getMenu().stream()
                .filter(Dish::getIsBio)
                .collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(Dish::getName, Collectors.toList())));

        typeToName.entrySet().forEach(System.out::println);

        return typeToName;
    }

    private static Comparator<Dish> getCompByCaloriesReverse()
    {
        return (d1, d2) -> d2.getCalories().compareTo(d1.getCalories());
    }

    private static Comparator<Dish> getCompByType()
    {
        return Comparator.comparing(Dish::getType);
    }

    private static Comparator<Dish> getCompByName()
    {
        return Comparator.comparing(Dish::getName);
    }

    private static double calculateAverage(List<Integer> marks) {
        if (marks == null || marks.isEmpty()) {
            return 0;
        }

        double sum = 0;
        for (Integer mark : marks) {
            sum += mark;
        }

        return sum / marks.size();
    }
}
