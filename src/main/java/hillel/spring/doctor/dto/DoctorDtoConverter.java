package hillel.spring.doctor.dto;

import hillel.spring.doctor.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

public interface DoctorDtoConverter {
    Doctor toModel(DoctorInputDto dto);
    Doctor toModel(DoctorInputDto dto, Integer id);
    DoctorOutputDto toDto(Doctor doc);
}