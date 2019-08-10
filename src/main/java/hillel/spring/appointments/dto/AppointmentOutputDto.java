package hillel.spring.appointments.dto;

import lombok.Data;

import java.util.Map;

@Data
public class AppointmentOutputDto {
    private Map<Integer, Integer> hourToPetId;
}
