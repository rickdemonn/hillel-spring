package hillel.spring.appointments;

import hillel.spring.appointments.dto.AppointmentsDtoConverter;
import hillel.spring.appointments.dto.AppointmentsInputDto;
import hillel.spring.appointments.dto.AppointmentsOutputDto;
import hillel.spring.doctor.DoctorController;
import hillel.spring.doctor.DoctorNotFoundException;
import hillel.spring.doctor.DoctorService;
import hillel.spring.pet.PetService;
import hillel.spring.pet.dto.PetNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AppointmentsController {
    private final AppointmentsService appointmentsService;
    private final AppointmentsDtoConverter appointmentsDtoConverter;
    private final DoctorService doctorService;
    private final PetService petService;
    private final Logger logger = LoggerFactory.getLogger(AppointmentsController.class);

    @GetMapping("/doctors/{docId}/schedule/{date}")
    public AppointmentsOutputDto getScheduleOfDoctor(@PathVariable Integer docId,
                                                     @PathVariable String date) {
        doctorService.findDoctorByID(docId).orElseThrow(DoctorNotFoundException::new);
        return appointmentsDtoConverter.toDto(appointmentsService.getScheduleOfDoctor(docId, LocalDate.parse(date)));

    }

    @GetMapping("/appointments")
    public List<Appointments> findAll() {
        return appointmentsService.findAll();
    }

    @PostMapping("/doctors/{docId}/schedule/{date}/{busyHour}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void makeAPetAppointment(@PathVariable Integer docId,
                                    @PathVariable String date,
                                    @PathVariable Integer busyHour,
                                    @RequestBody AppointmentsInputDto dto) {
        doctorService.findDoctorByID(docId).orElseThrow(DoctorNotFoundException::new);
        petService.findPet(dto.getPetId()).orElseThrow(PetNotFoundException::new);
        if(appointmentsService.getScheduleOfDoctor(docId ,LocalDate.parse(date)).containsKey(busyHour)){
           throw new DoctorBusyException();
        }
        if (busyHour >= 8 && busyHour <= 17) {
            appointmentsService.makeAPetAppointment(appointmentsDtoConverter.toModel(dto, docId, LocalDate.parse(date), busyHour));
        } else {
            logger.error("working hours must be between 8 and 17");
            throw new NotWorkingTimeException();
        }

    }

}
