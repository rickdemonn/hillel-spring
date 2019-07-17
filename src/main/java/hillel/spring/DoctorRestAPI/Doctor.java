package hillel.spring.DoctorRestAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    private Integer id;
    private String name;
    private String specialization;
}
