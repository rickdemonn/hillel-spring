package hillel.spring.doctor;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorService {
    private final DoctorRepo doctorRepo;

    public List<Doctor> findDoctors (Optional<String> name,
                                     Optional<List<String>> specializations){
        if (specializations.isPresent() && name.isPresent()) {
            return doctorRepo.findDistinctBySpecializationsInAndNameStartsWithIgnoreCase(specializations.get(), name.get());
        }
        if (name.isPresent()) {
            return doctorRepo.findByNameStartsWithIgnoreCase(name.get());
        }
        if (specializations.isPresent()) {
            return doctorRepo.findDistinctBySpecializationsIn(specializations.get());
        }
        return doctorRepo.findAll();
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepo.save(doctor);
    }

    public Optional<Doctor> findDoctorByID(Integer id) {
        return doctorRepo.findById(id);
    }

    public void upDateDoctor(Doctor doctor) {
        doctorRepo.save(doctor);
    }

    public void deleteDoctor(Integer id) {
        doctorRepo.deleteById(id);
    }
}
