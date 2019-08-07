package hillel.spring.doctor.dto;


import lombok.Data;

import java.util.List;

@Data
public class DoctorInputDto {
    private String name;
    private List<String> specializations;
}
