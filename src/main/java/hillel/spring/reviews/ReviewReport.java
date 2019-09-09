package hillel.spring.reviews;


import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Data
@AllArgsConstructor
public class ReviewReport {
    private Double serviceAvg;
    private Double equipmentAvg;
    private Double specialistQualificationAvg;
    private Double effectivenessOfTheTreatmentAvg;
    private Double overallRatingAvg;
    private Map<LocalDateTime, Optional<String>> dateToComment;
}
