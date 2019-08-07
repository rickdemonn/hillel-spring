package hillel.spring.doctor.dto;

import hillel.spring.doctor.Doctor;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class DoctorDtoConverterImpl implements DoctorDtoConverter {

    @Override
    public Doctor toModel(DoctorInputDto dto) {
        if ( dto == null ) {
            return null;
        }

        Doctor doctor = new Doctor();

        doctor.setName( dto.getName() );
        List<String> list = dto.getSpecializations();
        if ( list != null ) {
            doctor.setSpecializations( new ArrayList<String>( list ) );
        }

        return doctor;
    }

    @Override
    public Doctor toModel(DoctorInputDto dto, Integer id) {
        if ( dto == null && id == null ) {
            return null;
        }

        Doctor doctor = new Doctor();

        if ( dto != null ) {
            doctor.setName( dto.getName() );
            List<String> list = dto.getSpecializations();
            if ( list != null ) {
                doctor.setSpecializations( new ArrayList<String>( list ) );
            }
        }
        if ( id != null ) {
            doctor.setId( id );
        }

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
        List<String> list = doc.getSpecializations();
        if ( list != null ) {
            doctorOutputDto.setSpecializations( new ArrayList<String>( list ) );
        }

        return doctorOutputDto;
    }
}
