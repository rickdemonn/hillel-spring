package hillel.spring.doctor.dto;

import hillel.spring.doctor.DocInfo;
import hillel.spring.doctor.Doctor;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-09-10T12:01:11+0300",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 12.0.2 (Oracle Corporation)"
)
@Component
public class DoctorDtoConverterImpl implements DoctorDtoConverter {

    @Override
    public Doctor toModel(DoctorInputDto dto) {
        if ( dto == null ) {
            return null;
        }

        Doctor doctor = new Doctor();

        doctor.setName( dto.getName() );
        doctor.setIsSick( dto.getIsSick() );
        doctor.setDocInfoId( dto.getDocInfoId() );

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
            doctor.setIsSick( dto.getIsSick() );
            doctor.setDocInfoId( dto.getDocInfoId() );
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
        doctorOutputDto.setIsSick( doc.getIsSick() );
        List<String> list = doc.getSpecializations();
        if ( list != null ) {
            doctorOutputDto.setSpecializations( new ArrayList<String>( list ) );
        }
        doctorOutputDto.setUniversity( doc.getUniversity() );
        doctorOutputDto.setUniversityGradationDate( doc.getUniversityGradationDate() );
        doctorOutputDto.setDocInfoId( doc.getDocInfoId() );

        return doctorOutputDto;
    }

    @Override
    public Doctor createInfo(Doctor doctor, DocInfo docInfo) {
        if ( docInfo == null ) {
            return null;
        }

        if ( doctor.getSpecializations() != null ) {
            List<String> list = docInfo.getSpecializations();
            if ( list != null ) {
                doctor.getSpecializations().clear();
                doctor.getSpecializations().addAll( list );
            }
            else {
                doctor.setSpecializations( null );
            }
        }
        else {
            List<String> list = docInfo.getSpecializations();
            if ( list != null ) {
                doctor.setSpecializations( new ArrayList<String>( list ) );
            }
        }
        doctor.setUniversity( docInfo.getUniversity() );
        doctor.setUniversityGradationDate( docInfo.getUniversityGradationDate() );
        doctor.setDocInfoId( docInfo.getDocInfoId() );

        return doctor;
    }

    @Override
    public Doctor updateDoc(Doctor newDoc, Doctor doctor) {
        if ( doctor == null ) {
            return null;
        }

        newDoc.setName( doctor.getName() );
        newDoc.setIsSick( doctor.getIsSick() );
        newDoc.setDocInfoId( doctor.getDocInfoId() );

        return newDoc;
    }
}
