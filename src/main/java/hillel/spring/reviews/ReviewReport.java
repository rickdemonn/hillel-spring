package hillel.spring.reviews;


import lombok.Data;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Data
@Component
public class ReviewReport {
    private Double serviceAvg;
    private Double equipmentAvg;
    private Double specialistQualificationAvg;
    private Double effectivenessOfTheTreatmentAvg;
    private Double overallRatingAvg;
    private Map<LocalDateTime, Optional<String>> dateToComment;
}
