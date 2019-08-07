package hillel.spring.appointments;

import hillel.spring.doctor.Doctor;
import hillel.spring.doctor.DoctorRepo;
import hillel.spring.pet.Pet;
import hillel.spring.pet.PetRepo;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AppointmentsRepo appointmentsRepo;

    @Autowired
    DoctorRepo doctorRepo;

    @Autowired
    PetRepo petRepo;

    @After
    public void cleanUp() {
        appointmentsRepo.deleteAll();
        doctorRepo.deleteAll();
        petRepo.deleteAll();
    }

    @Test
    public void getScheduleOfDoctor() throws Exception {
        Integer docId = doctorRepo.save(new Doctor(null, "Aaa", Arrays.asList("veterinarian", "surgeon"))).getId();
        Integer petId = petRepo.save(new Pet(null, "Gu4ka")).getId();
        Integer appId = appointmentsRepo.save(new Appointments(null, docId, petId, 9, LocalDate.parse("2019-01-01"))).getId();

        mockMvc.perform(get("/doctors/{docId}/schedule/{date}", docId, "2019-01-01"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/doctors/{docId}/schedule/{date}", 150, "2019-01-01"))
                .andExpect(status().isNotFound());

        assertThat(appointmentsRepo.findById(appId)).isPresent();

    }

    @Test
    public void makeAPetAppointment() throws Exception {
        Integer docId = doctorRepo.save(new Doctor(null, "Aaa", Arrays.asList("veterinarian", "surgeon"))).getId();
        Integer petId = petRepo.save(new Pet(null, "Gu4ka")).getId();
        Integer pet2Id = petRepo.save(new Pet(null, "Gu4ka2")).getId();

        mockMvc.perform(post("/doctors/{docId}/schedule/{date}/{busyHour}", docId, "2019-01-01", "8").contentType("application/json")
                .content("{ \"petId\": " + petId + " }"))
                .andExpect(status().isNoContent());

        mockMvc.perform(post("/doctors/{docId}/schedule/{date}/{busyHour}", docId, "2019-01-01", "10").contentType("application/json")
                .content("{ \"petId\": " + pet2Id + " }"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/doctors/{docId}/schedule/{date}", docId, "2019-01-01"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.hourToPetId.8", is(petId)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.hourToPetId.10", is(pet2Id)));
    }

    @Test
    public void tryToMakeAppointmentWithWrongPetId() throws Exception{
        Integer docId = doctorRepo.save(new Doctor(null, "Aaa", Arrays.asList("veterinarian", "surgeon"))).getId();
        Integer petId = petRepo.save(new Pet(null, "Gu4ka")).getId();

        mockMvc.perform(post("/doctors/{docId}/schedule/{date}/{busyHour}", docId, "2019-01-01", "8").contentType("application/json")
                .content("{ \"petId\": " + 150 + " }"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void tryToMakeAppointmentWithWrongDocId() throws Exception{
        Integer docId = doctorRepo.save(new Doctor(null, "Aaa", Arrays.asList("veterinarian", "surgeon"))).getId();
        Integer petId = petRepo.save(new Pet(null, "Gu4ka")).getId();

        mockMvc.perform(post("/doctors/{docId}/schedule/{date}/{busyHour}",150,"2019-01-01","8").contentType("application/json")
                .content("{ \"petId\": "+petId+" }"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void tryToMakeAppointmentWithWrongWorkingTime() throws Exception{
        Integer docId = doctorRepo.save(new Doctor(null, "Aaa", Arrays.asList("veterinarian", "surgeon"))).getId();
        Integer petId = petRepo.save(new Pet(null, "Gu4ka")).getId();

        mockMvc.perform(post("/doctors/{docId}/schedule/{date}/{busyHour}",docId,"2019-01-01","3").contentType("application/json")
                .content("{ \"petId\": "+petId+" }"))
                .andExpect(status().isBadRequest());
    }
}