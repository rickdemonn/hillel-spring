package hillel.spring.doctor;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DocInfo {
    private final Integer docInfoId;
    private final String university;
    private final List<String> specializations;
    private final LocalDate universityGradationDate;
}