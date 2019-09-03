package hillel.spring.reviews;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Version
    private Integer version;
    private Integer petId;
    private String service;
    private String equipment;
    private String specialistQualification;
    private String effectivenessOfTheTreatment;
    private String overallRating;
    private String comment;
    private LocalDateTime date;

    public Optional<String> getService() {
        return Optional.ofNullable(service);
    }

    public Optional<String> getEquipment() {
        return Optional.ofNullable(equipment);
    }

    public Optional<String> getSpecialistQualification() {
        return Optional.ofNullable(specialistQualification);
    }

    public Optional<String> getEffectivenessOfTheTreatment() {
        return Optional.ofNullable(effectivenessOfTheTreatment);
    }

    public Optional<String> getOverallRating() {
        return Optional.ofNullable(overallRating);
    }

    public Optional<String> getComment() {
        return Optional.ofNullable(comment);
    }
}
