package hillel.spring.DoctorRestAPI.dto;


import lombok.Data;

@Data
public class DoctorInputDto {
    private String name;
    private String specialization;
}
