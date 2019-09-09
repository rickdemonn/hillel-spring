package hillel.spring.appointments;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentRepo appointmentRepo;

    public Map<Integer, Integer> getScheduleOfDoctor(Integer docId, LocalDate date) {
        return appointmentRepo.findByDocIdAndLocalDate(docId, date).stream()
                .collect(Collectors.toMap(Appointment::getBusyHour, Appointment::getPetId));
    }

    public void makeAPetAppointment(Appointment appointment) {
        appointmentRepo.save(appointment);
    }

    public List<Appointment> findAll() {
        return appointmentRepo.findAll();
    }

    public List<Appointment> findByDocIdAndLocalDate(Integer sickDocId, LocalDate date) {
        return appointmentRepo.findByDocIdAndLocalDate(sickDocId,date);
    }
    @Retryable(StaleObjectStateException.class)
    public void saveAppointments(List<Appointment> appointmentOfSickDoc) {
        appointmentOfSickDoc.forEach(appointmentRepo::save);
    }

    public Optional<Appointment> findByPetId(Integer petId) {
        return appointmentRepo.findByPetId(petId);
    }
}
