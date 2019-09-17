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
    private Integer service;
    private Integer equipment;
    private Integer specialistQualification;
    private Integer effectivenessOfTheTreatment;
    private Integer overallRating;
    private String comment;
    private LocalDateTime date;

    public Optional<Integer> getService() {
        return Optional.ofNullable(service);
    }

    public Optional<Integer> getEquipment() {
        return Optional.ofNullable(equipment);
    }

    public Optional<Integer> getSpecialistQualification() {
        return Optional.ofNullable(specialistQualification);
    }

    public Optional<Integer> getEffectivenessOfTheTreatment() {
        return Optional.ofNullable(effectivenessOfTheTreatment);
    }

    public Optional<Integer> getOverallRating() {
        return Optional.ofNullable(overallRating);
    }

    public Optional<String> getComment() {
        return Optional.ofNullable(comment);
    }
}
