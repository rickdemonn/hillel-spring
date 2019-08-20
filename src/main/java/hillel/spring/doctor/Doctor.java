package hillel.spring.doctor;

import hillel.spring.BooleanToStringConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Convert(converter = BooleanToStringConverter.class)
    private Boolean isSick;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> specializations;
}
