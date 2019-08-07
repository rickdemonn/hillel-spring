package hillel.spring.appointments.dto;

import hillel.spring.appointments.Appointments;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@Component
public class AppointmentsDtoConverterImpl implements AppointmentsDtoConverter {

    @Override
    public Appointments toModel(AppointmentsInputDto dto, Integer docId, LocalDate localDate, Integer busyHour) {
        if ( dto == null && docId == null && localDate == null && busyHour == null ) {
            return null;
        }

        Appointments appointments = new Appointments();

        if ( dto != null ) {
            appointments.setPetId( dto.getPetId() );
        }
        if ( docId != null ) {
            appointments.setDocId( docId );
        }
        if ( localDate != null ) {
            appointments.setLocalDate( localDate );
        }
        if ( busyHour != null ) {
            appointments.setBusyHour( busyHour );
        }

        return appointments;
    }

    @Override
    public AppointmentsOutputDto toDto(Map<Integer, Integer> hourToPetId) {
        if ( hourToPetId == null ) {
            return null;
        }

        AppointmentsOutputDto appointmentsOutputDto = new AppointmentsOutputDto();

        Map<Integer, Integer> map = hourToPetId;
        if ( map != null ) {
            appointmentsOutputDto.setHourToPetId( new HashMap<Integer, Integer>( map ) );
        }

        return appointmentsOutputDto;
    }
}
