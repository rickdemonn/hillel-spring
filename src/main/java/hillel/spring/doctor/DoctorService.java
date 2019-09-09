package hillel.spring.doctor;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorService {
    private final DoctorRepo doctorRepo;

    public Page<Doctor> findDoctors (Optional<String> name,
                                     Optional<List<String>> specializations,
                                     Pageable pageable){
        if (specializations.isPresent() && name.isPresent()) {
            return doctorRepo.findDistinctBySpecializationsInAndNameStartsWithIgnoreCase(specializations.get(), name.get(), pageable);
        }
        if (name.isPresent()) {
            return doctorRepo.findByNameStartsWithIgnoreCase(name.get(), pageable);
        }
        if (specializations.isPresent()) {
            return doctorRepo.findDistinctBySpecializationsIn(specializations.get(), pageable);
        }
        return doctorRepo.findAll(pageable);
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
