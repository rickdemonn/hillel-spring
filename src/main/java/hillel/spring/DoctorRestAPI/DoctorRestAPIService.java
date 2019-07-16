package hillel.spring.DoctorRestAPI;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorRestAPIService {
    private final DoctorRestAPIRepo doctorRestAPIRepo;

    public List<Doctor> findAllDoctors (){
        return doctorRestAPIRepo.findAllDoctors();
    }

    public Integer createDoctor(Doctor doctor) {
        return doctorRestAPIRepo.createDoctor(doctor);
    }

    public Optional<Doctor> findDoctorByID(Integer id) {
        return doctorRestAPIRepo.findDoctorByID(id);
    }

    public List<Doctor> findDoctorBySpecialization(String specialization) {
        return doctorRestAPIRepo.findDoctorBySpecialization(specialization);
    }

    public List<Doctor> findDoctorsByFirstLetter(String name) {
        return doctorRestAPIRepo.findDoctorsByFirstLetter(name);
    }

    public void upDateDoctor(Integer id, Doctor doctor) {
        doctorRestAPIRepo.upDateDoctor(id,doctor);
    }

    public void deleteDoctor(Integer id) {
        doctorRestAPIRepo.deleteDoctor(id);
    }
}
