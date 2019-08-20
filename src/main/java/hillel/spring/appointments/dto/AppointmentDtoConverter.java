package hillel.spring.appointments.dto;

import hillel.spring.appointments.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

import java.util.Map;

//@Mapper
public interface AppointmentDtoConverter {
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "version", ignore = true)
    Appointment toModel(AppointmentInputDto dto, Integer docId, LocalDate localDate, Integer busyHour);
    AppointmentOutputDto toDto(Map<Integer, Integer> hourToPetId);
}

