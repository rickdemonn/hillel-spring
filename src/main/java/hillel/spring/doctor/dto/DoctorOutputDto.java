package hillel.spring.doctor.dto;

import lombok.Data;

import java.util.List;

@Data
public class DoctorOutputDto {
    private Integer id;
    private String name;
    private Boolean isSick;
    private List<String> specializations;
}
