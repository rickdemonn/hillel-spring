package hillel.spring;


import java.util.Objects;

public class Apple {
    private final Integer weigth;
    private final String color;

    public Apple(Integer weigth, String color) {
        this.weigth = weigth;
        this.color = color;
    }

    public Integer getWeigth() {
        return weigth;
    }

    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apple apple = (Apple) o;
        return Objects.equals(weigth, apple.weigth) &&
                Objects.equals(color, apple.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weigth, color);
    }

    @Override
    public String toString() {
        return "Apple{" +
                "weight=" + weigth +
                ", color='" + color + '\'' +
                '}';
    }
}
