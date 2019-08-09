package hillel.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class HillelSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HillelSpringApplication.class, args);
		log.info("Hello my friend:)");
	}

}
