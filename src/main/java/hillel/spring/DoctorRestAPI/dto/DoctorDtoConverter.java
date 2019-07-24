package hillel.spring.DoctorRestAPI.dto;

import hillel.spring.DoctorRestAPI.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface DoctorDtoConverter {
    @Mapping(target = "id", ignore = true)
    Doctor toModel(DoctorInputDto dto);

    DoctorOutputDto toModel(Doctor doc);

    List<DoctorOutputDto> toModel(List<Doctor> docs);
}
