package hillel.spring.doctor.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DoctorOutputDto {
    private Integer id;
    private String name;
    private Boolean isSick;
    private List<String> specializations;
    private String university;
    private LocalDate universityGradationDate;
    private Integer docInfoId;
}
