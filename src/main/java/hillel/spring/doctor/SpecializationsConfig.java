package hillel.spring.doctor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Component
@ConfigurationProperties("doctor-specializations")
@Validated
public class SpecializationsConfig {
    private List<String> specializations;
}
