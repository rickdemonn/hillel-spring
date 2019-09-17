package hillel.spring.doctor.dto;

import hillel.spring.doctor.DocInfo;
import hillel.spring.doctor.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

//@Mapper
public interface DoctorDtoConverter {
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "version", ignore = true)
//    @Mapping(target = "specializations", ignore = true)
//    @Mapping(target = "university", ignore = true)
//    @Mapping(target = "universityGradationDate", ignore = true)
    Doctor toModel(DoctorInputDto dto);

//    @Mapping(target = "specializations", ignore = true)
//    @Mapping(target = "university", ignore = true)
//    @Mapping(target = "universityGradationDate", ignore = true)
//    @Mapping(target = "version", ignore = true)
    Doctor toModel(DoctorInputDto dto, Integer id);

    DoctorOutputDto toDto(Doctor doc);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "version", ignore = true)
//    @Mapping(target = "isSick", ignore = true)
//    @Mapping(target = "name", ignore = true)
    Doctor createInfo(@MappingTarget Doctor doctor, DocInfo docInfo);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "version", ignore = true)
//    @Mapping(target = "specializations", ignore = true)
//    @Mapping(target = "university", ignore = true)
//    @Mapping(target = "universityGradationDate", ignore = true)
    Doctor updateDoc(@MappingTarget Doctor newDoc, Doctor doctor);
}