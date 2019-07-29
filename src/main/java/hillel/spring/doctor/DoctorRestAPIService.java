package hillel.spring.doctor;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorRestAPIService {
    private final DoctorRestAPIRepo doctorRestAPIRepo;

    public List<Doctor> findDoctors (Optional<String> specialization,
                                     Optional<String> name,
                                     Optional<List<String>> specializations){
        if (specialization.isPresent() && name.isPresent()) {
            return doctorRestAPIRepo.findBySpecializationAndName(specialization.get(), name.get());
        }
        if (specialization.isPresent()) {
            return doctorRestAPIRepo.findBySpecialization(specialization.get());
        }
        if (name.isPresent()) {
            return doctorRestAPIRepo.findByName(name.get());
        }
        if (specializations.isPresent()) {
            return doctorRestAPIRepo.findBySpecializations(specializations.get());
        }
        return doctorRestAPIRepo.findAll();
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRestAPIRepo.save(doctor);
    }

    public Optional<Doctor> findDoctorByID(Integer id) {
        return doctorRestAPIRepo.findById(id);
    }

    public void upDateDoctor(Doctor doctor) {
        doctorRestAPIRepo.save(doctor);
    }

    public void deleteDoctor(Integer id) {
        doctorRestAPIRepo.deleteById(id);
    }
}
