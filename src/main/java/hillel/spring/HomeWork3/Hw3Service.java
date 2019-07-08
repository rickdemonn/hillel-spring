package hillel.spring.HomeWork3;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class Hw3Service {
    private final Hw3Repo repo;

    public String getEnGreeting(){
        return repo.getGreetings().get(0);
    }

    public String getFrGreeting(){
        return repo.getGreetings().get(1);
    }

    public String getItGreeting(){
        return repo.getGreetings().get(2);
    }

    public Optional<String> getRandomGreeting() {
        Random r = new Random();
        return repo.getGreetings().stream()
                .skip(r.nextInt(repo.getGreetings().size()))
                .findFirst();
    }
}
