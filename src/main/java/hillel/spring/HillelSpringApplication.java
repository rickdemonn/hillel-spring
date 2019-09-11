package hillel.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
@EnableRetry
public class HillelSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HillelSpringApplication.class, args);
		log.info("Hello my friend:)");
	}

	@Bean
	public RestTemplate resTemplate() {
		return new RestTemplate();
	}

}
