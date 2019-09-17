package hillel.spring.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewOutputDto {
    private Double serviceAvg;
    private Double equipmentAvg;
    private Double specialistQualificationAvg;
    private Double effectivenessOfTheTreatmentAvg;
    private Double overallRatingAvg;
    private Map<LocalDateTime, String> dateToComment;
}
