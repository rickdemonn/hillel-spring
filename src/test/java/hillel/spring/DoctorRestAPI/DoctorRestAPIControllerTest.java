package hillel.spring.DoctorRestAPI;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

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
    public void shouldFindAllDoctors() throws Exception {
        doctorRestAPIRepo.init();

        mockMvc.perform(get("/doctors"))
               .andExpect(status().isOk())
               .andExpect(content().json(fromResource("DoctorRestAPI/all-doctors.json")));
    }

    @Test
    public void shouldFindDoctorById() throws Exception {
        doctorRestAPIRepo.createDoctor(new Doctor(1,"Aaa","surgeon"));
        doctorRestAPIRepo.createDoctor(new Doctor(3,"ccc","NoSurgeon"));

        mockMvc.perform(get("/doctors/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().json(fromResource("DoctorRestAPI/find-doctorbyID.json")));

        mockMvc.perform(get("/doctors/{id}",3))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnSurgeon() throws Exception {
        doctorRestAPIRepo.createDoctor(new Doctor(1,"aaa","surgeon"));
        doctorRestAPIRepo.createDoctor(new Doctor(2,"bbb","surgeon"));
        doctorRestAPIRepo.createDoctor(new Doctor(3,"ccc","NoSurgeon"));

        mockMvc.perform(get("/doctors").param("specialization", "surgeon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",Matchers.hasSize(2)));
    }

    @Test
    public void shouldReturnDoctorsByFirstLetterOfName() throws Exception {
        doctorRestAPIRepo.createDoctor(new Doctor(1,"Aaa","surgeon"));
        doctorRestAPIRepo.createDoctor(new Doctor(2,"bbb","surgeon"));
        doctorRestAPIRepo.createDoctor(new Doctor(3,"ccc","NoSurgeon"));

        mockMvc.perform(get("/doctors").param("name","A"))
                .andExpect(status().isOk())
                .andExpect(content().json(fromResource("DoctorRestAPI/DoctorsNamedOnA.json")));
    }

    @Test
    public void shouldReturnDoctorsBySpecAndFirstLetter() throws Exception {
        doctorRestAPIRepo.createDoctor(new Doctor(1,"Abb","surgeon"));
        doctorRestAPIRepo.createDoctor(new Doctor(2,"bbb","surgeon"));
        doctorRestAPIRepo.createDoctor(new Doctor(3,"Acc","NoSurgeon"));
        doctorRestAPIRepo.createDoctor(new Doctor(4,"AAA","surgeon"));

        mockMvc.perform(get("/doctors").param("name","A").param("specialization", "surgeon"))
                .andExpect(status().isOk())
                .andExpect(content().json(fromResource("DoctorRestAPI/DoctorsNamedOnAAndSpecSurgeon.json")));
    }

    @Test
    public void shouldCreateDoctor() throws Exception {
        mockMvc.perform(post("/doctors").contentType("application/json")
                .content(fromResource("DoctorRestAPI/create-doctor.json")))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","http://localhost:8081/doctors/1"));

        Assertions.assertThat(doctorRestAPIRepo.findDoctorByID(1).get().getId()).isEqualTo(1);
    }

    @Test
    public void shouldUpdateDoctor() throws Exception {
        doctorRestAPIRepo.createDoctor(new Doctor(1,"Aaa","surgeon"));

        mockMvc.perform(put("/doctors/{id}",1).contentType("application/json")
                .content(fromResource("DoctorRestAPI/update-doctor.json")))
                .andExpect(status().isNoContent());

        mockMvc.perform(put("/doctors/{id}",2).contentType("application/json")
                .content(fromResource("DoctorRestAPI/update-doctor.json")))
                .andExpect(status().isNotFound());

        Assertions.assertThat(doctorRestAPIRepo.findDoctorByID(1).get().getName()).isEqualTo("Zlo");
    }

    @Test
    public void shouldDeleteDoctor() throws Exception {
        doctorRestAPIRepo.createDoctor(new Doctor(1,"Aaa","surgeon"));

        mockMvc.perform(delete("/doctors/{id}",1)).andExpect(status().isNoContent());

        mockMvc.perform(delete("/doctors/{id}",2)).andExpect(status().isNotFound());

        Assertions.assertThat(doctorRestAPIRepo.findDoctorByID(1)).isEmpty();
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