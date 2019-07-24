package hillel.spring.DoctorRestAPI;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorRestAPIService {
    private final DoctorRestAPIRepo doctorRestAPIRepo;

    public List<Doctor> findDoctors (Predicate<Doctor> predicate){
        return doctorRestAPIRepo.findDoctors().stream()
                                .filter(predicate)
                                .collect(Collectors.toList());
    }

    public Integer createDoctor(Doctor doctor) {
        return doctorRestAPIRepo.createDoctor(doctor);
    }

    public Optional<Doctor> findDoctorByID(Integer id) {
        return doctorRestAPIRepo.findDoctorByID(id);
    }

    public void upDateDoctor(Integer id, Doctor doctor) {
        doctorRestAPIRepo.upDateDoctor(id,doctor);
    }

    public void deleteDoctor(Integer id) {
        doctorRestAPIRepo.deleteDoctor(id);
    }
}
