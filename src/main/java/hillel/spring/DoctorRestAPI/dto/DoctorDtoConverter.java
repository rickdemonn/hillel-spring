package hillel.spring.DoctorRestAPI.dto;

import hillel.spring.DoctorRestAPI.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DoctorDtoConverter {
    @Mapping(target = "id", ignore = true)
    Doctor toModel(DoctorInputDto dto);

    DoctorOutputDto toDto(Doctor doc);
}