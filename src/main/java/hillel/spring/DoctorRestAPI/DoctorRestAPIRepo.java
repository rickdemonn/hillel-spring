package hillel.spring.DoctorRestAPI;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class DoctorRestAPIRepo {
    private final List<Doctor> doctors = Collections.synchronizedList(new ArrayList<>());
    private AtomicInteger id = new AtomicInteger(4);
    {
        doctors.add(new Doctor(1, "AiBolit", "veterinarian"));
        doctors.add(new Doctor(2, "Dr. Chaos", "surgeon"));
        doctors.add(new Doctor(3, "Dr. Mephesto", "geneticist"));
        doctors.add(new Doctor(4, "Dr. Sklifasovskiy", "surgeon"));
    }

    public List<Doctor> findAllDoctors(){
        return doctors;
    }

    public Integer createDoctor(Doctor doctor) {
        this.id.getAndIncrement();
        doctors.add(new Doctor(id.get(), doctor.getName(), doctor.getSpecialization()));
        return id.get();
    }

    public Optional<Doctor> findDoctorByID(Integer id) {
        return doctors.stream()
                .filter(doc -> doc.getId().equals(id))
                .findFirst();
    }

    public List<Doctor> findDoctorBySpecialization(String specialization) {
        return doctors.stream()
                .filter(doc -> doc.getSpecialization().equals(specialization))
                .collect(Collectors.toList());
    }

    public List<Doctor> findDoctorsByFirstLetter(String name) {
        return doctors.stream()
                .filter(doc -> doc.getName().startsWith(name))
                .collect(Collectors.toList());
    }

    public void upDateDoctor(Integer id, Doctor doctor) {
         doctors.stream()
                            .filter(doc -> doc.getId().equals(id))
                            .forEach(doc -> {
                                doc.setName(doctor.getName());
                                doc.setSpecialization(doctor.getSpecialization());
                            });
    }

    public void deleteDoctor(Integer id) {
        doctors.removeIf(doc -> doc.getId().equals(id));
    }


}
