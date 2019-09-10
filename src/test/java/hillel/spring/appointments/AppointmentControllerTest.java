package hillel.spring.appointments;

import hillel.spring.TestRunner;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@TestRunner
public class AppointmentControllerTest {

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
    public void getScheduleOfDoctor() throws Exception {
        Integer docId = doctorRepo.save(new Doctor(null,
                null,
                "AyBolit",
                false,
                List.of("veterinarian", "surgeon"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1)).getId();;
        Integer petId = petRepo.save(new Pet(null, "Gu4ka")).getId();
        Integer appId = appointmentRepo.save(new Appointment(null, 1, docId, petId, 9, LocalDate.parse("2019-01-01"))).getId();

        mockMvc.perform(get("/doctors/{docId}/schedule/{date}", docId, "2019-01-01"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/doctors/{docId}/schedule/{date}", 150, "2019-01-01"))
                .andExpect(status().isNotFound());

        assertThat(appointmentRepo.findById(appId)).isPresent();

    }

    @Test
    public void makeAPetAppointment() throws Exception {
        Integer docId = doctorRepo.save(new Doctor(null,
                null,
                "AyBolit",
                false,
                List.of("veterinarian", "surgeon"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1)).getId();
        Integer petId = petRepo.save(new Pet(null, "Gu4ka")).getId();
        Integer pet2Id = petRepo.save(new Pet(null, "Gu4ka2")).getId();
        appointmentRepo.save(new Appointment(null,1, docId,petId,8,LocalDate.parse("2019-01-01")));
        appointmentRepo.save(new Appointment(null,2, docId,pet2Id,10,LocalDate.parse("2019-01-01")));

        mockMvc.perform(get("/doctors/{docId}/schedule/{date}", docId, "2019-01-01"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.hourToPetId.8", is(petId)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.hourToPetId.10", is(pet2Id)));
    }

    @Test
    public void tryToMakeAppointmentWithWrongPetId() throws Exception{
        Integer docId = doctorRepo.save(new Doctor(null,
                null,
                "AyBolit",
                false,
                List.of("veterinarian", "surgeon"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1)).getId();
        Integer petId = petRepo.save(new Pet(null, "Gu4ka")).getId();

        mockMvc.perform(post("/doctors/{docId}/schedule/{date}/{busyHour}", docId, "2019-01-01", "8").contentType("application/json")
                .content("{ \"petId\": " + 150 + " }"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void tryToMakeAppointmentWithWrongDocId() throws Exception{
        Integer docId = doctorRepo.save(new Doctor(null,
                null,
                "AyBolit",
                false,
                List.of("veterinarian", "surgeon"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1)).getId();
        Integer petId = petRepo.save(new Pet(null, "Gu4ka")).getId();

        mockMvc.perform(post("/doctors/{docId}/schedule/{date}/{busyHour}",150,"2019-01-01","8").contentType("application/json")
                .content("{ \"petId\": "+petId+" }"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void tryToMakeAppointmentWithWrongWorkingTime() throws Exception{
        Integer docId = doctorRepo.save(new Doctor(null,
                null,
                "AyBolit",
                false,
                List.of("veterinarian", "surgeon"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1)).getId();
        Integer petId = petRepo.save(new Pet(null, "Gu4ka")).getId();

        mockMvc.perform(post("/doctors/{docId}/schedule/{date}/{busyHour}",docId,"2019-01-01","3").contentType("application/json")
                .content("{ \"petId\": "+petId+" }"))
                .andExpect(status().isBadRequest());
    }
}