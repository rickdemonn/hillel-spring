package hillel.spring.doctor.dto;

import hillel.spring.doctor.Doctor;

//@Mapper
public interface DoctorDtoConverter {
//    @Mapping(target = "id", ignore = true)
    Doctor toModel(DoctorInputDto dto);

    DoctorOutputDto toDto(Doctor doc);
}