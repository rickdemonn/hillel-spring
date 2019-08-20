package hillel.spring.appointments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {

    List<Appointment> findByDocIdAndLocalDate(Integer docId, LocalDate date);

    @Modifying
    @Query("UPDATE Appointment SET docId = :docId WHERE docId = :sickDocId and localDate = :date")
    void reWriteSchedulesOfDoctors(@Param("date") LocalDate date,
                                   @Param("sickDocId") Integer sickDocId,
                                   @Param("docId") Integer docId);
}
