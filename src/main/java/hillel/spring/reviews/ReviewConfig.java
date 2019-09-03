package hillel.spring.reviews;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("review.config")
@Data
public class ReviewConfig {
    private List<String> allowList;
}
