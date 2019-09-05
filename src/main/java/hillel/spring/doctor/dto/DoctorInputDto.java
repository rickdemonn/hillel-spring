package hillel.spring.doctor.dto;


import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class DoctorInputDto {
    @NotEmpty
    private String name;
    @NotNull
    private Boolean isSick;
    private List<@Size(min = 1) @AllowSpecializations(message = "{allow.specializations}") String> specializations;
}
