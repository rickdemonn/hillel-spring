package hillel.spring.sickdoctor;

import hillel.spring.appointments.AppointmentService;
import hillel.spring.appointments.DoctorBusyException;
import hillel.spring.doctor.DoctorNotFoundException;
import hillel.spring.doctor.DoctorService;
import hillel.spring.sickdoctor.dto.SickDoctorOutputDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
public class SickDoctorController {

    private DoctorService doctorService;
    private SickDoctorService sickDoctorService;
    private AppointmentService appointmentService;


    @PostMapping("/sickdoctor/{sickDocId}/{date}/{docId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SickDoctorOutputDTO distributeDoctorRecords(@PathVariable Integer sickDocId,
                                                       @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                       @PathVariable Integer docId) {
        val mayBeSickDoctor = doctorService.findDoctorByID(sickDocId).orElseThrow(DoctorNotFoundException::new);
        val mayBeDoctor = doctorService.findDoctorByID(docId).orElseThrow(DoctorNotFoundException::new);

        if(sickDocId.equals(docId)) {
            log.error("there was an attempt to rewrite the same doctor. Id's: " + sickDocId + "" +
                    " and "+ docId);
            throw new DoctorsIdsIsEqualException();
        }

        if (!mayBeSickDoctor.getIsSick()) {
            log.error("the doctor you want to take notes from: " + mayBeSickDoctor + " is not sick");
            throw new DoctorIsNotSickException();
        }

        if (mayBeDoctor.getIsSick()) {
            log.error("doctor to whom you want to give notes :" + mayBeDoctor + " is sick");
            throw new DoctorIsSickException();
        }

        Map<Integer, Integer> scheduleOfSickDoctor = appointmentService.getScheduleOfDoctor(sickDocId, date);
        Map<Integer, Integer> scheduleOfDoctor = appointmentService.getScheduleOfDoctor(docId, date);

        if(scheduleOfSickDoctor.keySet().stream().anyMatch(scheduleOfDoctor.keySet()::contains)){
            log.error("Sorry, but the doctor you want to translate records: "+ mayBeDoctor + "is busy");
            throw new DoctorBusyException();
        }

        Map<Integer, Integer> newScheduleOfDoctor = sickDoctorService.reWriteSchedulesOfDoctors(date, sickDocId, docId);

        return new SickDoctorOutputDTO(mayBeDoctor, newScheduleOfDoctor);
    }
}
