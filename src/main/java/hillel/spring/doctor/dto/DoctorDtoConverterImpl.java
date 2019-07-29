package hillel.spring.doctor.dto;

import hillel.spring.doctor.Doctor;
import org.springframework.stereotype.Component;


@Component
public class DoctorDtoConverterImpl implements DoctorDtoConverter {

    @Override
    public Doctor toModel(DoctorInputDto dto) {
        if ( dto == null ) {
            return null;
        }

        Doctor doctor = new Doctor();

        doctor.setName( dto.getName() );
        doctor.setSpecialization( dto.getSpecialization() );

        return doctor;
    }

    @Override
    public DoctorOutputDto toDto(Doctor doc) {
        if ( doc == null ) {
            return null;
        }

        DoctorOutputDto doctorOutputDto = new DoctorOutputDto();

        doctorOutputDto.setId( doc.getId() );
        doctorOutputDto.setName( doc.getName() );
        doctorOutputDto.setSpecialization( doc.getSpecialization() );

        return doctorOutputDto;
    }
}
