package hillel.spring.HomeWork3;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@AllArgsConstructor
@RestController
public class Hw3Controller {

    private final Hw3Service service;


    @GetMapping("/greeting/en")
    public String greetingEn(){
        return service.getEnGreeting();
    }

    @GetMapping("/greeting/fr")
    public String greetingFr(){
        return service.getFrGreeting();
    }

    @GetMapping("/greeting/it")
    public String greetingIt(){
        return service.getItGreeting();
    }

    @GetMapping("/greeting/random")
    public Optional<String> greetingRandom(){
        return service.getRandomGreeting();
    }
}
