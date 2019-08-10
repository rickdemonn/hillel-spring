package hillel.spring.appointments.dto;

import hillel.spring.appointments.Appointment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@Component
public class AppointmentDtoConverterImpl implements AppointmentDtoConverter {

    @Override
    public Appointment toModel(AppointmentInputDto dto, Integer docId, LocalDate localDate, Integer busyHour) {
        if ( dto == null && docId == null && localDate == null && busyHour == null ) {
            return null;
        }

        Appointment appointment = new Appointment();

        if ( dto != null ) {
            appointment.setPetId( dto.getPetId() );
        }
        if ( docId != null ) {
            appointment.setDocId( docId );
        }
        if ( localDate != null ) {
            appointment.setLocalDate( localDate );
        }
        if ( busyHour != null ) {
            appointment.setBusyHour( busyHour );
        }

        return appointment;
    }

    @Override
    public AppointmentOutputDto toDto(Map<Integer, Integer> hourToPetId) {
        if ( hourToPetId == null ) {
            return null;
        }

        AppointmentOutputDto appointmentOutputDto = new AppointmentOutputDto();

        Map<Integer, Integer> map = hourToPetId;
        if ( map != null ) {
            appointmentOutputDto.setHourToPetId( new HashMap<Integer, Integer>( map ) );
        }

        return appointmentOutputDto;
    }
}
