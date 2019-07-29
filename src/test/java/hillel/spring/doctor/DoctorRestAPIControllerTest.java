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

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DoctorRestAPIControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DoctorRestAPIRepo doctorRestAPIRepo;

    @After
    public void cleanUp(){
        doctorRestAPIRepo.deleteAll();
    }


    @Test
    public void findDoctorById() throws Exception {
        Integer id = doctorRestAPIRepo.save(new Doctor(null,"AiBolit","veterinarian")).getId();

        mockMvc.perform(get("/doctors/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specialization", is("veterinarian")))
                .andExpect(jsonPath("$.name", is("AiBolit")));
    }

    @Test
    public void shouldFindAllDoctors() throws Exception {
        doctorRestAPIRepo.save(new Doctor(null,"AiBolit","veterinarian"));
        doctorRestAPIRepo.save(new Doctor(null,"Dr. Chaos","surgeon"));

        mockMvc.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].specialization", is("veterinarian")))
                .andExpect(jsonPath("$[1].specialization", is("surgeon")))
                .andExpect(jsonPath("$[0].name", is("AiBolit")))
                .andExpect(jsonPath("$[1].name", is("Dr. Chaos")))
                .andExpect(jsonPath("$[0].id", is(notNullValue())))
                .andExpect(jsonPath("$[1].id", is(notNullValue())));

    }

    @Test
    public void shouldReturnSurgeon() throws Exception {
        doctorRestAPIRepo.save(new Doctor(null,"ccc","veterinarian"));
        doctorRestAPIRepo.save(new Doctor(null,"aaa","surgeon"));
        doctorRestAPIRepo.save(new Doctor(null,"bbb","surgeon"));
        doctorRestAPIRepo.save(new Doctor(null,"ccc","veterinarian"));

        mockMvc.perform(get("/doctors").param("specialization", "surgeon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].specialization", is("surgeon")))
                .andExpect(jsonPath("$[1].specialization", is("surgeon")));
    }

    @Test
    public void shouldReturnDoctorsByFirstLetterOfName() throws Exception {
        doctorRestAPIRepo.save(new Doctor(null,"Aaa","surgeon"));
        doctorRestAPIRepo.save(new Doctor(null,"DAaa","surgeon"));
        doctorRestAPIRepo.save(new Doctor(null,"bbb","surgeon"));
        doctorRestAPIRepo.save(new Doctor(null,"ccc","surgeon"));
        doctorRestAPIRepo.save(new Doctor(null,"aaa","surgeon"));

        mockMvc.perform(get("/doctors").param("name","A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Aaa")))
                .andExpect(jsonPath("$[1].name", is("aaa")));
    }

    @Test
    public void shouldReturnDoctorsBySpecAndFirstLetter() throws Exception {
        doctorRestAPIRepo.save(new Doctor(null,"Aaa","surgeon"));
        doctorRestAPIRepo.save(new Doctor(null,"DAaa","surgeon"));
        doctorRestAPIRepo.save(new Doctor(null,"bbb","surgeon"));
        doctorRestAPIRepo.save(new Doctor(null,"ccc","surgeon"));
        doctorRestAPIRepo.save(new Doctor(null,"aaa","veterinarian"));
        doctorRestAPIRepo.save(new Doctor(null,"AAA","veterinarian"));
        doctorRestAPIRepo.save(new Doctor(null,"BB","veterinarian"));

        mockMvc.perform(get("/doctors").param("name","A").param("specialization", "surgeon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Aaa")))
                .andExpect(jsonPath("$[0].specialization", is("surgeon")));
    }

    @Test
    public void shouldReturnDoctorsBySpecializations() throws Exception {
        doctorRestAPIRepo.save(new Doctor(null,"Aaa","surgeon"));
        doctorRestAPIRepo.save(new Doctor(null,"BBB","veterinarian"));
        doctorRestAPIRepo.save(new Doctor(null,"CCC","geneticist"));
        doctorRestAPIRepo.save(new Doctor(null,"DDD","geneticist"));

        mockMvc.perform(get("/doctors").param("specializations","surgeon","veterinarian"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].specialization", is("surgeon")))
                .andExpect(jsonPath("$[1].specialization", is("veterinarian")));

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

        assertThat(doctorRestAPIRepo.findById(id)).isPresent();

        mockMvc.perform(post("/doctors").contentType("application/json")
                .content(fromResource("DoctorRestAPI/tryToCreateWrong-doctor.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateDoctor() throws Exception {
        Integer id = doctorRestAPIRepo.save(new Doctor(null,"Aaa","surgeon")).getId();

        mockMvc.perform(put("/doctors/{id}",id).contentType("application/json")
                .content(fromResource("DoctorRestAPI/update-doctor.json")))
                .andExpect(status().isNoContent());

        mockMvc.perform(put("/doctors/{id}",150).contentType("application/json")
                .content(fromResource("DoctorRestAPI/update-doctor.json")))
                .andExpect(status().isNotFound());

        mockMvc.perform(put("/doctors/{id}",id).contentType("application/json")
                .content(fromResource("DoctorRestAPI/tryToCreateWrong-doctor.json")))
                .andExpect(status().isBadRequest());

        assertThat(doctorRestAPIRepo.findById(id).get().getName()).isEqualTo("Zlo");
    }

    @Test
    public void shouldDeleteDoctor() throws Exception {
        Integer id = doctorRestAPIRepo.save(new Doctor(null,"Aaa","surgeon")).getId();

        mockMvc.perform(delete("/doctors/{id}",id))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/doctors/{id}",150))
                .andExpect(status().isNotFound());

        assertThat(doctorRestAPIRepo.findById(id)).isEmpty();
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