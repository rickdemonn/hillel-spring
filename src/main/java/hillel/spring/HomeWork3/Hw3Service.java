package hillel.spring.HomeWork3;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class Hw3Service {

    private final Hw3Repo repo;

    @Bean
    private Random getRandom(){
        return new Random();
    }

    public String getEnGreeting(){
        return repo.getGreetings().get(Languages.ENGLISH);
    }

    public String getFrGreeting(){
        return repo.getGreetings().get(Languages.FRENCH);
    }

    public String getItGreeting(){
        return repo.getGreetings().get(Languages.ITALIAN);
    }

    public String getRandomGreeting() {
        int random = getRandom().nextInt(3) + 1;
        switch (random){
            case (1) :
                return repo.getGreetings().get(Languages.ENGLISH);
            case (2) :
                return repo.getGreetings().get(Languages.FRENCH);
            case (3) :
                return repo.getGreetings().get(Languages.ITALIAN);

        }
        throw new RuntimeException("Oops, random is broken :(");
    }

}
