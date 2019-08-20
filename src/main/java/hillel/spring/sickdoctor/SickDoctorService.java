package hillel.spring.sickdoctor;

import hillel.spring.appointments.Appointment;
import hillel.spring.appointments.AppointmentRepo;
import hillel.spring.appointments.AppointmentService;
import hillel.spring.appointments.DoctorBusyException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Map;


@Service
@AllArgsConstructor
public class SickDoctorService {

    private final AppointmentService appointmentService;

    @Transactional
    public Map<Integer, Integer> reWriteSchedulesOfDoctors(LocalDate date, Integer sickDocId, Integer docId){
        appointmentService.reWriteSchedulesOfDoctors(date, sickDocId, docId);
        return appointmentService.getScheduleOfDoctor(docId, date);
    }
}
