package hillel.spring.reviews.dto;

import java.util.Optional;


import lombok.Data;

@Data
public class ReviewInputDto {
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
}
