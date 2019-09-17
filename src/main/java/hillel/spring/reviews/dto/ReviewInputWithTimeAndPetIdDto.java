package hillel.spring.reviews.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class ReviewInputWithTimeAndPetIdDto{
    private Integer petId;
    private LocalDateTime date;
    @Min(1)
    @Max(5)
    private Integer service;
    @Min(1)
    @Max(5)
    private Integer equipment;
    @Min(1)
    @Max(5)
    private Integer specialistQualification;
    @Min(1)
    @Max(5)
    private Integer effectivenessOfTheTreatment;
    @Min(1)
    @Max(5)
    private Integer overallRating;
    private String comment;

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

    public Optional<Integer> getPetId(){
        return Optional.ofNullable(petId);
    }
    public Optional<LocalDateTime> getDate(){
        return Optional.ofNullable(date);
    }
}
