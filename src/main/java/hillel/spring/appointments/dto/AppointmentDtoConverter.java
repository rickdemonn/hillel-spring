package hillel.spring.appointments.dto;

import hillel.spring.appointments.Appointment;

import java.time.LocalDate;

import java.util.Map;

//@Mapper
public interface AppointmentDtoConverter {
//    @Mapping(target = "id", ignore = true)
    Appointment toModel(AppointmentInputDto dto, Integer docId, LocalDate localDate, Integer busyHour);
    AppointmentOutputDto toDto(Map<Integer ,Integer> hourToPetId);
}

