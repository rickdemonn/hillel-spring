package hillel.spring.sickdoctor.dto;

import hillel.spring.doctor.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class SickDoctorOutputDTO {
    private Doctor doctor;
    private Map<Integer, Integer> hourToPetId;
}
