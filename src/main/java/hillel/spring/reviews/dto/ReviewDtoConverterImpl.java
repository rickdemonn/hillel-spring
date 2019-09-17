package hillel.spring.reviews.dto;

import hillel.spring.reviews.Review;
import hillel.spring.reviews.ReviewReport;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-09-02T18:38:05+0300",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 12.0.2 (Oracle Corporation)"
)
@Component
public class ReviewDtoConverterImpl implements ReviewDtoConverter {

    @Override
    public Review toModel(ReviewInputDto dto, Integer petId) {
        if ( dto == null && petId == null ) {
            return null;
        }

        Review review = new Review();

        if ( dto != null ) {
            review.setService( unpack( dto.getService() ) );
            review.setEquipment( unpack( dto.getEquipment() ) );
            review.setSpecialistQualification( unpack( dto.getSpecialistQualification() ) );
            review.setEffectivenessOfTheTreatment( unpack( dto.getEffectivenessOfTheTreatment() ) );
            review.setOverallRating( unpack( dto.getOverallRating() ) );
            review.setComment( unpack( dto.getComment() ) );
        }
        if ( petId != null ) {
            review.setPetId( petId );
        }

        return review;
    }

    @Override
    public Review toModel(Integer id, ReviewInputWithTimeAndPetIdDto dto) {
        if ( id == null && dto == null ) {
            return null;
        }

        Review review = new Review();

        if ( id != null ) {
            review.setId( id );
        }
        if ( dto != null ) {
            review.setPetId( unpack( dto.getPetId() ) );
            review.setService( unpack( dto.getService() ) );
            review.setEquipment( unpack( dto.getEquipment() ) );
            review.setSpecialistQualification( unpack( dto.getSpecialistQualification() ) );
            review.setEffectivenessOfTheTreatment( unpack( dto.getEffectivenessOfTheTreatment() ) );
            review.setOverallRating( unpack( dto.getOverallRating() ) );
            review.setComment( unpack( dto.getComment() ) );
            review.setDate( unpack( dto.getDate() ) );
        }

        return review;
    }

    @Override
    public ReviewOutputDto toDto(ReviewReport reviewReport) {
        if ( reviewReport == null ) {
            return null;
        }

        ReviewOutputDto reviewOutputDto = new ReviewOutputDto();

        reviewOutputDto.setServiceAvg( reviewReport.getServiceAvg() );
        reviewOutputDto.setEquipmentAvg( reviewReport.getEquipmentAvg() );
        reviewOutputDto.setSpecialistQualificationAvg( reviewReport.getSpecialistQualificationAvg() );
        reviewOutputDto.setEffectivenessOfTheTreatmentAvg( reviewReport.getEffectivenessOfTheTreatmentAvg() );
        reviewOutputDto.setOverallRatingAvg( reviewReport.getOverallRatingAvg() );
        reviewOutputDto.setDateToComment( localDateTimeOptionalMapToLocalDateTimeStringMap( reviewReport.getDateToComment() ) );

        return reviewOutputDto;
    }

    @Override
    public void update(Review review, ReviewInputWithTimeAndPetIdDto dto) {
        if ( dto == null ) {
            return;
        }

        if (dto.getPetId().isPresent()) {
            review.setPetId( unpack( dto.getPetId() ) );
        }
        if (dto.getService().isPresent()) {
            review.setService( unpack( dto.getService() ) );
        }
        if (dto.getEquipment().isPresent()) {
            review.setEquipment( unpack( dto.getEquipment() ) );
        }
        if (dto.getSpecialistQualification().isPresent()) {
            review.setSpecialistQualification( unpack( dto.getSpecialistQualification() ) );
        }
        if (dto.getEffectivenessOfTheTreatment().isPresent()) {
            review.setEffectivenessOfTheTreatment( unpack( dto.getEffectivenessOfTheTreatment() ) );
        }
        if (dto.getOverallRating().isPresent()) {
            review.setOverallRating( unpack( dto.getOverallRating() ) );
        }
        if (dto.getComment().isPresent()) {
            review.setComment( unpack( dto.getComment() ) );
        }
        if (dto.getDate().isPresent()) {
            review.setDate( unpack( dto.getDate() ) );
        }
    }

    protected Map<LocalDateTime, String> localDateTimeOptionalMapToLocalDateTimeStringMap(Map<LocalDateTime, Optional<String>> map) {
        if ( map == null ) {
            return null;
        }

        Map<LocalDateTime, String> map1 = new HashMap<LocalDateTime, String>( Math.max( (int) ( map.size() / .75f ) + 1, 16 ) );

        for ( Map.Entry<LocalDateTime, Optional<String>> entry : map.entrySet() ) {
            LocalDateTime key = entry.getKey();
            String value = unpack( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }
}
