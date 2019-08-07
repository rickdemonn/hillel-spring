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
public class AppointmentsService {
    private final AppointmentsRepo appointmentsRepo;
    private final Logger logger = LoggerFactory.getLogger(AppointmentsService.class);

    public Map<Integer, Integer> getScheduleOfDoctor(Integer docId, LocalDate date) {
        return appointmentsRepo.findByDocIdAndLocalDate(docId, date).stream()
                .collect(Collectors.toMap(Appointments::getBusyHour, Appointments::getPetId));
    }

    public void makeAPetAppointment(Appointments appointments) {
        appointmentsRepo.save(appointments);
    }

    public List<Appointments> findAll() {
       return appointmentsRepo.findAll();
    }
}
