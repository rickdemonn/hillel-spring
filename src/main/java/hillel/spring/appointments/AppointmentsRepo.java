package hillel.spring.appointments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentsRepo extends JpaRepository<Appointments,Integer> {

    List<Appointments> findByDocIdAndLocalDate(Integer docId, LocalDate date);
}
