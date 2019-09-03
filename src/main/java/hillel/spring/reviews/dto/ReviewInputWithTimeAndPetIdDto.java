package hillel.spring.reviews.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class ReviewInputWithTimeAndPetIdDto{
    private Integer petId;
    private LocalDateTime date;
    private String service;
    private String equipment;
    private String specialistQualification;
    private String effectivenessOfTheTreatment;
    private String overallRating;
    private String comment;

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

    public Optional<Integer> getPetId(){
        return Optional.ofNullable(petId);
    }
    public Optional<LocalDateTime> getDate(){
        return Optional.ofNullable(date);
    }
}
