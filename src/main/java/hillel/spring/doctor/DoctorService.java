package hillel.spring.doctor;

import hillel.spring.doctor.dto.DoctorDtoConverter;
import hillel.spring.doctor.dto.DoctorInputDto;
import lombok.AllArgsConstructor;
import lombok.val;
import org.hibernate.StaleObjectStateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorService {
    private final DoctorRepo doctorRepo;
    private final DoctorDtoConverter doctorDtoConverter;

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

    public Integer createDoctor(Doctor doctor) {
        return doctorRepo.save(doctor).getId();
    }

    public Optional<Doctor> findDoctorByID(Integer id) {
        return doctorRepo.findById(id);
    }

    @Retryable(StaleObjectStateException.class)
    public void upDateDoctor(Doctor newDoctor, DocInfo docInfo) {
        val doctor = doctorRepo.findById(newDoctor.getId()).orElseThrow(DoctorNotFoundException::new);

        doctorDtoConverter.updateDoc(doctor, newDoctor);

        doctorDtoConverter.createInfo(doctor ,docInfo);

        doctorRepo.save(doctor);
    }

    public void deleteDoctor(Integer id) {
        doctorRepo.deleteById(id);
    }
}
