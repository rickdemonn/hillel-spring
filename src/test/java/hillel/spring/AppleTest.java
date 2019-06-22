package hillel.spring;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppleTest {
    @Test
    public void lombokWorks() throws Exception{
        Apple apple = new Apple(100, "Green");

        Assertions.assertThat(apple.getColor()).isEqualTo("Green");

        Assertions.assertThat(apple).isEqualTo(new Apple(100, "Green"));
    }
}