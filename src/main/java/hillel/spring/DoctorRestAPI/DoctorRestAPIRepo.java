package hillel.spring.DoctorRestAPI;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Repository
public class DoctorRestAPIRepo {
    private final Map<Integer,Doctor> idToDoctor = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger(0);


    public List<Doctor> findDoctors(){
        return new ArrayList<>(idToDoctor.values());
    }

    public Integer createDoctor(Doctor doctor) {
        doctor.setId(this.id.incrementAndGet());
        idToDoctor.put(doctor.getId(), doctor);
        return id.get();
    }

    public Optional<Doctor> findDoctorByID(Integer id) {
        return Optional.ofNullable(idToDoctor.get(id));
    }

    public void upDateDoctor(Integer id, Doctor doctor) {
        doctor.setId(id);
        idToDoctor.replace(id, doctor);
    }

    public void deleteDoctor(Integer id) {
        idToDoctor.remove(id);
    }


    public void deleteAll() {
        idToDoctor.clear();
        id.set(0);
    }

    public void init() {
        createDoctor(new Doctor(1, "AiBolit", "veterinarian"));
        createDoctor(new Doctor(2, "Dr. Chaos", "surgeon"));
        createDoctor(new Doctor(3, "Dr. Mephesto", "geneticist"));
        createDoctor(new Doctor(4, "Dr. Sklifasovskiy", "surgeon"));
    }
}
