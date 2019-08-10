package hillel.spring.appointments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment,Integer> {

    List<Appointment> findByDocIdAndLocalDate(Integer docId, LocalDate date);
}
