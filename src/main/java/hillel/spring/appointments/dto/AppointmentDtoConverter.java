package hillel.spring.appointments.dto;

import hillel.spring.appointments.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;

import java.util.Map;

//@Mapper
public interface AppointmentDtoConverter {
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "version", ignore = true)
    Appointment toModel(AppointmentInputDto dto, Integer docId, LocalDate localDate, Integer busyHour);
    AppointmentOutputDto toDto(Map<Integer, Integer> hourToPetId);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "version", ignore = true)
    void updateModel(@MappingTarget Appointment appointment, AppointmentInputDto dto, Integer docId, LocalDate localDate, Integer busyHour);
}
