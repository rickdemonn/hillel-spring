package hillel.spring.appointments.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AppointmentInputDto {
    @NotNull
    private Integer petId;
}
