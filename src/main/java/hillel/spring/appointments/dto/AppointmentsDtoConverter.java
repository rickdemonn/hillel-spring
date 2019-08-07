package hillel.spring.appointments.dto;

import hillel.spring.appointments.Appointments;

import java.time.LocalDate;

import java.util.Map;

//@Mapper
public interface AppointmentsDtoConverter {
//    @Mapping(target = "id", ignore = true)
    Appointments toModel(AppointmentsInputDto dto, Integer docId, LocalDate localDate, Integer busyHour);
    AppointmentsOutputDto toDto(Map<Integer ,Integer> hourToPetId);
}

