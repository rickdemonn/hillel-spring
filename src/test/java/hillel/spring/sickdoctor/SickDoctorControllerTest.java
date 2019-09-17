package hillel.spring.sickdoctor;

import hillel.spring.TestRunner;
import hillel.spring.appointments.Appointment;
import hillel.spring.appointments.AppointmentRepo;
import hillel.spring.doctor.Doctor;
import hillel.spring.doctor.DoctorRepo;
import hillel.spring.pet.Pet;
import hillel.spring.pet.PetRepo;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@TestRunner
public class SickDoctorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AppointmentRepo appointmentRepo;

    @Autowired
    DoctorRepo doctorRepo;

    @Autowired
    PetRepo petRepo;

    @After
    public void cleanUp() {
        appointmentRepo.deleteAll();
        doctorRepo.deleteAll();
        petRepo.deleteAll();
    }

    @Test
    public void shouldBeDistributeDoctorRecords() throws Exception {
        Integer firstDocId = doctorRepo.save(new Doctor(null,
                null,
                "AyBolit",
                true,
                List.of("veterinarian", "surgeon"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1)).getId();
        Integer secondDocId = doctorRepo.save(new Doctor(null,
                null,
                "AyBolit",
                false,
                List.of("veterinarian", "surgeon"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1)).getId();

        Integer petId = petRepo.save(new Pet(null, "Bobik")).getId();

        appointmentRepo.save(new Appointment(null,null, firstDocId, petId,8, LocalDate.parse("2019-01-01")));
        appointmentRepo.save(new Appointment(null,null, firstDocId, petId,10,LocalDate.parse("2019-01-01")));

        mockMvc.perform(post("/sickdoctor/{sickDocId}/{date}/{docId}", firstDocId, "2019-01-01", secondDocId))
                .andExpect(status().isAccepted()).andExpect(jsonPath("$.doctor.id", is(secondDocId)))
                .andExpect(jsonPath("$.hourToPetId.8", is(petId)));

    }

    @Test
    public void tryToMakeDistributeDoctorRecordsWhenDoctorIsBusy() throws Exception {
        Integer firstDocId = doctorRepo.save(new Doctor(null,
                null,
                "AyBolit",
                false,
                List.of("veterinarian", "surgeon"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1)).getId();
        Integer secondDocId = doctorRepo.save(new Doctor(null,
                null,
                "AyBolit",
                false,
                List.of("veterinarian", "surgeon"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1)).getId();

        Integer petId = petRepo.save(new Pet(null, "Bobik")).getId();

        appointmentRepo.save(new Appointment(null,null, firstDocId, petId,8, LocalDate.parse("2019-01-01")));
        appointmentRepo.save(new Appointment(null,null, firstDocId, petId,10,LocalDate.parse("2019-01-01")));

        appointmentRepo.save(new Appointment(null,null, secondDocId, petId,8,LocalDate.parse("2019-01-01")));
        appointmentRepo.save(new Appointment(null,null, secondDocId, petId,10,LocalDate.parse("2019-01-01")));

        mockMvc.perform(post("/sickdoctor/{sickDocId}/{date}/{docId}", firstDocId, "2019-01-01", secondDocId))
                .andExpect(status().isBadRequest());

    }


}