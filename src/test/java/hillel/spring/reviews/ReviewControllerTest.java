package hillel.spring.reviews;

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
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@TestRunner
public class ReviewControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AppointmentRepo appointmentRepo;

    @Autowired
    DoctorRepo doctorRepo;

    @Autowired
    PetRepo petRepo;

    @Autowired
    ReviewRepo reviewRepo;

    @After
    public void cleanUp() {
        appointmentRepo.deleteAll();
        doctorRepo.deleteAll();
        petRepo.deleteAll();
        reviewRepo.deleteAll();
    }

    @Test
    public void shouldBeMakeReview() throws Exception{
        Integer petId = petRepo.save(new Pet(null, "Gu4ka")).getId();
        Integer docId = doctorRepo.save(new Doctor(null,
                null,
                "AyBolit",
                false,
                 List.of("veterinarian", "surgeon"),
                "Politeh",
                 LocalDate.parse("2000-01-01"),
                1)).getId();
        appointmentRepo.save(new Appointment(null,1, docId,petId,8, LocalDate.parse("2019-01-01")));

        mockMvc.perform(post("/reviews/{petId}", petId).contentType("application/json")
                .content(fromResource("review/make-review.json")))
                .andExpect(status().isAccepted());
    }

    @Test
    public void tryToMakeReviewWithWrongAllowParameters() throws Exception{
        Integer petId = petRepo.save(new Pet(null, "Gu4ka")).getId();
        Integer docId = doctorRepo.save(new Doctor(null,
                null,
                "AyBolit",
                false,
                List.of("veterinarian", "surgeon"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1)).getId();
        appointmentRepo.save(new Appointment(null,1, docId,petId,8, LocalDate.parse("2019-01-01")));

        mockMvc.perform(post("/reviews/{petId}", petId).contentType("application/json")
                .content(fromResource("review/wrong-parameters.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void tryToMakeReviewBeforeWentToTheReception() throws Exception{
        Integer petId = petRepo.save(new Pet(null, "Gu4ka")).getId();
        Integer docId = doctorRepo.save(new Doctor(null,
                null,
                "AyBolit",
                false,
                List.of("veterinarian", "surgeon"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1)).getId();
        appointmentRepo.save(new Appointment(null,1, docId,petId,8, LocalDate.parse("2500-01-01")));//future

        mockMvc.perform(post("/reviews/{petId}", petId).contentType("application/json")
                .content(fromResource("review/make-review.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void tryToMakeTwoReviews() throws Exception{
        Integer petId = petRepo.save(new Pet(null, "Gu4ka")).getId();
        Integer docId = doctorRepo.save(new Doctor(null,
                null,
                "AyBolit",
                false,
                List.of("veterinarian", "surgeon"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1)).getId();
        appointmentRepo.save(new Appointment(null,1, docId,petId,8, LocalDate.parse("2019-01-01")));

        mockMvc.perform(post("/reviews/{petId}", petId).contentType("application/json")
                .content(fromResource("review/make-review.json")))
                .andExpect(status().isAccepted());

        mockMvc.perform(post("/reviews/{petId}", petId).contentType("application/json")
                .content(fromResource("review/make-review.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldBeGetReviewReport() throws Exception{
        reviewRepo.save(new Review(null,1, 1,
                2,
                2,
                2,
                3,
                null,
                "Hello", LocalDateTime.parse("2019-01-01T12:00")));
        reviewRepo.save(new Review(null,1, 1,
                4,
                4,
                4,
                1,
                2,
                "Hello2", LocalDateTime.parse("2019-01-01T13:00")));

        mockMvc.perform(get("/reviews/report"))
                .andExpect(status().isOk())
                .andExpect(content().json(fromResource("review/report.json")));
    }

    @Test
    public void shouldBeUpdateReview() throws Exception{
        Integer petId = petRepo.save(new Pet(null, "Gu4ka")).getId();
        Integer id = reviewRepo.save(new Review(null,null, petId,
                2,
                2,
                3,
                3,
                null,
                "Hello", LocalDateTime.parse("2019-01-01T12:00"))).getId();

        mockMvc.perform(patch("/reviews/{id}",id).contentType("application/json")
                .content(fromResource("review/patch-review.json")))
                .andExpect(status().isAccepted());


    }

    public String fromResource(String path){
        try {
            File file = ResourceUtils.getFile("classpath:" + path);
            return Files.readString(file.toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}