package hillel.spring.pet;

import hillel.spring.doctor.Doctor;
import hillel.spring.doctor.DoctorRepo;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PetControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PetRepo petRepo;

    @After
    public void cleanUp() {
        petRepo.deleteAll();
    }

    @Test
    public void findAll() throws Exception {
    }

    @Test
    public void findPet() throws Exception {
        Integer id = petRepo.save(new Pet(null, "Bobik")).getId();

        mockMvc.perform(get("/pets/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Bobik")));

        mockMvc.perform((get("/pets/{id}", 150))).andExpect(status().isNotFound());
    }

    @Test
    public void createPet() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/pets").contentType("application/json")
                .content(fromResource("pets/create-pet.json")))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("http://localhost:8081/pets/")))
                .andReturn().getResponse();

        Integer id = Integer.parseInt(response.getHeader("location")
                .replace("http://localhost:8081/pets/", ""));

        assertThat(petRepo.findById(id)).isPresent();
    }

    @Test
    public void updatePet() throws Exception {
        Integer id = petRepo.save(new Pet(null, "Gu4ka")).getId();

        mockMvc.perform(put("/pets/{id}", id).contentType("application/json")
                .content(fromResource("pets/update-pet.json")))
                .andExpect(status().isNoContent());

        mockMvc.perform(put("/pets/{id}", 150).contentType("application/json")
                .content(fromResource("pets/update-pet.json")))
                .andExpect(status().isNotFound());

        assertThat(petRepo.findById(id).get().getName()).isEqualTo("Bobik");
    }

    @Test
    public void deletePet() throws Exception {
        Integer id = petRepo.save(new Pet(null,"Bobik")).getId();

        mockMvc.perform(delete("/pets/{id}",id)).andExpect(status().isNoContent());
        mockMvc.perform(delete("/pets/{id}",150)).andExpect(status().isNotFound());

        assertThat(petRepo.findById(id)).isEmpty();

    }

    public String fromResource(String path) {
        try {
            File file = ResourceUtils.getFile("classpath:" + path);
            return Files.readString(file.toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}