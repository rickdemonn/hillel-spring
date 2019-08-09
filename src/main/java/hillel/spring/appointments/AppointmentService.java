package hillel.spring.appointments;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

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
}
