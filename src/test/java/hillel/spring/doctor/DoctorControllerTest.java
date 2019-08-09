package hillel.spring.doctor;

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
import java.util.List;


import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DoctorRepo doctorRepo;

    @After
    public void cleanUp() {
        doctorRepo.deleteAll();
    }


    @Test
    public void findDoctorById() throws Exception {
        Integer id = doctorRepo.save(new Doctor(null, "AiBolit", List.of("veterinarian", "surgeon"))).getId();

        mockMvc.perform(get("/doctors/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specializations[0]", is("veterinarian")))
                .andExpect(jsonPath("$.specializations[1]", is("surgeon")))
                .andExpect(jsonPath("$.name", is("AiBolit")));
    }

    @Test
    public void shouldFindAllDoctors() throws Exception {
        doctorRepo.save(new Doctor(null, "AiBolit", List.of("veterinarian")));
        doctorRepo.save(new Doctor(null, "Dr. Chaos", List.of("veterinarian", "surgeon")));

        mockMvc.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].specializations[0]", is("veterinarian")))
                .andExpect(jsonPath("$[1].specializations[0]", is("veterinarian")))
                .andExpect(jsonPath("$[1].specializations[1]", is("surgeon")))
                .andExpect(jsonPath("$[0].name", is("AiBolit")))
                .andExpect(jsonPath("$[1].name", is("Dr. Chaos")))
                .andExpect(jsonPath("$[0].id", is(notNullValue())))
                .andExpect(jsonPath("$[1].id", is(notNullValue())));

    }

    @Test
    public void shouldReturnSurgeon() throws Exception {
        doctorRepo.save(new Doctor(null, "ccc", List.of("veterinarian", "surgeon")));
        doctorRepo.save(new Doctor(null, "aaa", List.of("surgeon", "veterinarian")));
        doctorRepo.save(new Doctor(null, "bbb", List.of("veterinarian", "geneticist")));
        doctorRepo.save(new Doctor(null, "ccc", List.of("geneticist", "veterinarian")));

        mockMvc.perform(get("/doctors").param("specializations", "surgeon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].specializations[1]", is("surgeon")))
                .andExpect(jsonPath("$[1].specializations[0]", is("surgeon")));
    }

    @Test
    public void shouldReturnDoctorsByFirstLetterOfName() throws Exception {
        doctorRepo.save(new Doctor(null, "Aaa", List.of("surgeon", "veterinarian")));
        doctorRepo.save(new Doctor(null, "DAaa", List.of("surgeon", "veterinarian")));
        doctorRepo.save(new Doctor(null, "bbb", List.of("surgeon", "veterinarian")));
        doctorRepo.save(new Doctor(null, "ccc", List.of("surgeon", "veterinarian")));
        doctorRepo.save(new Doctor(null, "aaa", List.of("surgeon", "veterinarian")));

        mockMvc.perform(get("/doctors").param("name", "A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Aaa")))
                .andExpect(jsonPath("$[1].name", is("aaa")));
    }

    @Test
    public void shouldReturnDoctorsBySpecAndFirstLetter() throws Exception {
        doctorRepo.save(new Doctor(null, "Aaa", List.of("surgeon", "veterinarian")));
        doctorRepo.save(new Doctor(null, "DAaa", List.of("geneticist", "veterinarian")));
        doctorRepo.save(new Doctor(null, "bbb", List.of("geneticist")));
        doctorRepo.save(new Doctor(null, "ccc", List.of("surgeon")));
        doctorRepo.save(new Doctor(null, "aaa", List.of("veterinarian")));
        doctorRepo.save(new Doctor(null, "AAA", List.of("surgeon", "geneticist")));
        doctorRepo.save(new Doctor(null, "BB", List.of("geneticist", "surgeon")));

        mockMvc.perform(get("/doctors").param("name", "A").param("specializations", "surgeon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Aaa")))
                .andExpect(jsonPath("$[0].specializations[0]", is("surgeon")))
                .andExpect(jsonPath("$[1].name", is("AAA")))
                .andExpect(jsonPath("$[0].specializations[0]", is("surgeon")));
        ;
    }

    @Test
    public void shouldReturnDoctorsBySpecializations() throws Exception {
        doctorRepo.save(new Doctor(null,"Aaa",List.of("veterinarian", "surgeon")));
        doctorRepo.save(new Doctor(null,"BBB",List.of("geneticist")));
        doctorRepo.save(new Doctor(null,"CCC",List.of("geneticist")));
        doctorRepo.save(new Doctor(null,"DDD",List.of("surgeon", "veterinarian")));

        mockMvc.perform(get("/doctors").param("specializations","surgeon","veterinarian"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].specializations[1]", is("surgeon")))
                .andExpect(jsonPath("$[1].specializations[1]", is("veterinarian")));

    }

    @Test
    public void shouldCreateDoctor() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/doctors").contentType("application/json")
                .content(fromResource("DoctorRestAPI/create-doctor.json")))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("http://localhost:8081/doctors/")))
                .andReturn().getResponse();

        Integer id = Integer.parseInt(response.getHeader("location")
                .replace("http://localhost:8081/doctors/", ""));

        assertThat(doctorRepo.findById(id)).isPresent();
    }

    @Test
    public void shouldCanNotCreateDoctor() throws Exception {
        mockMvc.perform(post("/doctors").contentType("application/json")
                .content(fromResource("DoctorRestAPI/tryToCreateWrong-doctor.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateDoctor() throws Exception {
        Integer id = doctorRepo.save(new Doctor(null,"Aaa",Arrays.asList("veterinarian", "surgeon"))).getId();

        mockMvc.perform(put("/doctors/{id}",id).contentType("application/json")
                .content(fromResource("DoctorRestAPI/update-doctor.json")))
                .andExpect(status().isNoContent());

        mockMvc.perform(put("/doctors/{id}",150).contentType("application/json")
                .content(fromResource("DoctorRestAPI/update-doctor.json")))
                .andExpect(status().isNotFound());

        mockMvc.perform(put("/doctors/{id}",id).contentType("application/json")
                .content(fromResource("DoctorRestAPI/tryToCreateWrong-doctor.json")))
                .andExpect(status().isBadRequest());

        assertThat(doctorRepo.findById(id).get().getName()).isEqualTo("Zlo");
    }

    @Test
    public void shouldDeleteDoctor() throws Exception {
        Integer id = doctorRepo.save(new Doctor(null,"Aaa",List.of("veterinarian", "surgeon"))).getId();

        mockMvc.perform(delete("/doctors/{id}",id))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/doctors/{id}",150))
                .andExpect(status().isNotFound());

        assertThat(doctorRepo.findById(id)).isEmpty();
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