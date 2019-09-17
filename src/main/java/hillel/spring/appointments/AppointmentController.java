package hillel.spring.appointments;

import hillel.spring.appointments.dto.AppointmentDtoConverter;
import hillel.spring.appointments.dto.AppointmentInputDto;
import hillel.spring.appointments.dto.AppointmentOutputDto;
import hillel.spring.doctor.DoctorNotFoundException;
import hillel.spring.doctor.DoctorService;
import hillel.spring.pet.PetService;
import hillel.spring.pet.dto.PetNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.*;

@RestController
@Slf4j
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final AppointmentDtoConverter appointmentDtoConverter;
    private final DoctorService doctorService;
    private final PetService petService;
    private final UriComponentsBuilder uriComponentsBuilder;

    public AppointmentController(AppointmentService appointmentService,
                                 AppointmentDtoConverter appointmentDtoConverter,
                                 DoctorService doctorService, PetService petService,
                                 @Value("${server.address:localhost}") String address,
                                 @Value("${server.port:80}") String port) {
        this.appointmentService = appointmentService;
        this.appointmentDtoConverter = appointmentDtoConverter;
        this.doctorService = doctorService;
        this.petService = petService;
        this.uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(address)
                .port(port)
                .path("/doctors/");
    }

    @GetMapping("/doctors/{docId}/schedule/{date}")
    public AppointmentOutputDto getScheduleOfDoctor(@PathVariable Integer docId,
                                                    @PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
        doctorService.findDoctorByID(docId).orElseThrow(DoctorNotFoundException::new);
        return appointmentDtoConverter.toDto(appointmentService.getScheduleOfDoctor(docId, date));

    }

    @GetMapping("/appointments")
    public List<Appointment> findAll() {
        return appointmentService.findAll();
    }

    @PostMapping("/doctors/{docId}/schedule/{date}/{busyHour}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void makeAPetAppointment(@PathVariable Integer docId,
                                    @PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate date,
                                    @PathVariable Integer busyHour,
                                    @Valid @RequestBody AppointmentInputDto dto) {

        doctorService.findDoctorByID(docId).orElseThrow(DoctorNotFoundException::new);

        petService.findPet(dto.getPetId()).orElseThrow(PetNotFoundException::new);

        if (appointmentService.getScheduleOfDoctor(docId, date).containsKey(busyHour)) {
            log.error("you chose the time to record: " + busyHour + " , But Doctor busy on this time " +
                    "you can see schedule of this doctor on " + uriComponentsBuilder.toUriString() + docId + "/schedule/" + date);
            throw new DoctorBusyException(busyHour, docId, date, uriComponentsBuilder);
        }

        if (busyHour >= 8 && busyHour <= 17) {
            val appointment = appointmentDtoConverter.toModel(dto, docId, date, busyHour);
            appointmentService.makeAPetAppointment(appointment);
        } else {
            log.error("you chose the time to record: " + busyHour + " , Working hours must be between 8 and 17");
            throw new NotWorkingTimeException(busyHour);
        }

    }

}
