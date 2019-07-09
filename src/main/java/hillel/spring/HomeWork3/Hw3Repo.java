package hillel.spring.HomeWork3;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;


import java.util.Map;


@Repository
public class Hw3Repo {

    private final Map<Languages, String> greetings = Map.of(Languages.ENGLISH,"Hello",
                                                        Languages.FRENCH, "Salut",
                                                        Languages.ITALIAN, "Benvenuto");

    public Map<Languages, String> getGreetings() {
        return greetings;
    }
}
