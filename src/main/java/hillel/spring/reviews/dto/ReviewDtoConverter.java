package hillel.spring.reviews.dto;

import hillel.spring.reviews.Review;

import hillel.spring.reviews.ReviewReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


import java.util.Optional;

//@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReviewDtoConverter {

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "version", ignore = true)
//    @Mapping(target = "date", ignore = true)
    Review toModel(ReviewInputDto dto, Integer petId);

//    @Mapping(target = "version", ignore = true)
    Review toModel(Integer id, ReviewInputWithTimeAndPetIdDto dto);

    ReviewOutputDto toDto(ReviewReport reviewReport);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "version", ignore = true)
    void update(@MappingTarget Review review, ReviewInputWithTimeAndPetIdDto dto);

    default <T> T unpack(Optional<T> maybe){
        return maybe.orElse(null);
    }
}
