package hillel.spring.appointments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {

    List<Appointment> findByDocIdAndLocalDate(Integer docId, LocalDate date);

    Optional<Appointment> findByPetId(Integer petId);
}
