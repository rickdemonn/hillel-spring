package hillel.spring.HomeWork3;


import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Data
@Repository
public class Hw3Repo {
    private final List<String> greetings = Arrays.asList("Hello", "Salut","Benvenuto");
}
