package hillel.spring.DoctorRestAPI.dto;

import lombok.Data;

@Data
public class DoctorOutputDto {
    private Integer id;
    private String name;
    private String specialization;
}
