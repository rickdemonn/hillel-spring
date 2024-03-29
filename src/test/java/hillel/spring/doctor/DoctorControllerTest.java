package hillel.spring.doctor;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import hillel.spring.TestRunner;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@TestRunner
public class DoctorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DoctorRepo doctorRepo;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @After
    public void cleanUp() {
        doctorRepo.deleteAll();
    }


    @Test
    public void findDoctorById() throws Exception {
        Integer id = doctorRepo.save(new Doctor(null,
                null,
                "AiBolit",
                false,
                List.of("veterinarian", "surgeon"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1)).getId();

        mockMvc.perform(get("/doctors/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specializations[0]", is("veterinarian")))
                .andExpect(jsonPath("$.specializations[1]", is("surgeon")))
                .andExpect(jsonPath("$.name", is("AiBolit")));
    }

    @Test
    public void shouldFindAllDoctors() throws Exception {
        doctorRepo.save(new Doctor(null,
                null,
                "AiBolit",
                false,
                List.of("veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));
        doctorRepo.save(new Doctor(null,
                null,
                "Dr. Chaos",
                false,
                List.of("veterinarian", "surgeon"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));

        mockMvc.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].specializations[0]", is("veterinarian")))
                .andExpect(jsonPath("$.content[1].specializations[0]", is("veterinarian")))
                .andExpect(jsonPath("$.content[1].specializations[1]", is("surgeon")))
                .andExpect(jsonPath("$.content[0].name", is("AiBolit")))
                .andExpect(jsonPath("$.content[1].name", is("Dr. Chaos")))
                .andExpect(jsonPath("$.content[0].id", is(notNullValue())))
                .andExpect(jsonPath("$.content[1].id", is(notNullValue())));

    }

    @Test
    public void shouldReturnSurgeon() throws Exception {
        doctorRepo.save(new Doctor(null,
                null,
                "Dr. Chaos",
                false,
                List.of("veterinarian", "surgeon"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));
        doctorRepo.save(new Doctor(null,
                null,
                "Dr. Chaos",
                false,
                List.of("surgeon", "veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));
        doctorRepo.save(new Doctor(null,
                null,
                "Dr. Chaos",
                false,
                List.of("veterinarian","geneticist"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));
        doctorRepo.save(new Doctor(null,
                null,
                "Dr. Chaos",
                false,
                List.of("geneticist", "veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));

        mockMvc.perform(get("/doctors").param("specializations", "surgeon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].specializations[1]", is("surgeon")));
    }

    @Test
    public void shouldReturnDoctorsByFirstLetterOfName() throws Exception {
        doctorRepo.save(new Doctor(null,
                null,
                "Aaa",
                false,
                List.of("geneticist", "veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));
        doctorRepo.save(new Doctor(null,
                null,
                "DAaa",
                false,
                List.of("geneticist", "veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));
        doctorRepo.save(new Doctor(null,
                null,
                "bbb",
                false,
                List.of("geneticist", "veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));
        doctorRepo.save(new Doctor(null,
                null,
                "ccc",
                false,
                List.of("geneticist", "veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));
        doctorRepo.save(new Doctor(null,
                null,
                "aaa",
                false,
                List.of("geneticist", "veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));

        mockMvc.perform(get("/doctors").param("name", "A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name", is("Aaa")))
                .andExpect(jsonPath("$.content[1].name", is("aaa")));
    }

    @Test
    public void shouldReturnDoctorsBySpecAndFirstLetter() throws Exception {
        doctorRepo.save(new Doctor(null,
                null,
                "Aaa",
                false,
                List.of("surgeon", "veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));
        doctorRepo.save(new Doctor(null,
                null,
                "DAaa",
                false,
                List.of("geneticist", "veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));
        doctorRepo.save(new Doctor(null,
                null,
                "bbb",
                false,
                List.of("geneticist", "veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));
        doctorRepo.save(new Doctor(null,
                null,
                "ccc",
                false,
                List.of("geneticist", "veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));
        doctorRepo.save(new Doctor(null,
                null,
                "aaa",
                false,
                List.of("geneticist", "veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));
        doctorRepo.save(new Doctor(null,
                null,
                "AAA",
                false,
                List.of("surgeon", "veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));
        doctorRepo.save(new Doctor(null,
                null,
                "BB",
                false,
                List.of("geneticist", "veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));

        mockMvc.perform(get("/doctors").param("name", "A").param("specializations", "surgeon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name", is("Aaa")))
                .andExpect(jsonPath("$.content[0].specializations[0]", is("surgeon")))
                .andExpect(jsonPath("$.content[1].name", is("AAA")))
                .andExpect(jsonPath("$.content[0].specializations[0]", is("surgeon")));
        ;
    }

    @Test
    public void shouldReturnDoctorsBySpecializations() throws Exception {
        doctorRepo.save(new Doctor(null,
                null,
                "Aaa",
                false,
                List.of("veterinarian", "surgeon"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));
        doctorRepo.save(new Doctor(null,
                null,
                "BBB",
                false,
                List.of("geneticist"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));
        doctorRepo.save(new Doctor(null,
                null,
                "CCC",
                false,
                List.of("geneticist"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));
        doctorRepo.save(new Doctor(null,
                null,
                "DDD",
                false,
                List.of("surgeon", "veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1));

        mockMvc.perform(get("/doctors").param("specializations","surgeon","veterinarian"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].specializations[1]", is("surgeon")))
                .andExpect(jsonPath("$.content[1].specializations[1]", is("veterinarian")));

    }

    @Test
    public void shouldCreateDoctor() throws Exception {
        WireMock.stubFor(WireMock.get("/info/1")
                .willReturn(WireMock.okJson(fromResource("DoctorRestAPI/wiremock-response.json"))));

        MockHttpServletResponse response = mockMvc.perform(post("/doctors").contentType("application/json")
                .content(fromResource("DoctorRestAPI/create-doctor.json")))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("http://localhost:8081/doctors/")))
                .andReturn().getResponse();

        Integer id = Integer.parseInt(response.getHeader("location")
                .replace("http://localhost:8081/doctors/", ""));

        assertThat(doctorRepo.findById(id)).isPresent();

        mockMvc.perform(get("/doctors/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specializations[1]", is("veterinarian")));
    }

    @Test
    public void shouldCanNotCreateDoctor() throws Exception {
        mockMvc.perform(post("/doctors").contentType("application/json")
                .content(fromResource("DoctorRestAPI/tryToCreateWrong-doctor.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateDoctor() throws Exception {
        WireMock.stubFor(WireMock.get("/info/1")
                .willReturn(WireMock.okJson(fromResource("DoctorRestAPI/wiremock-response.json"))));

        Integer id = doctorRepo.save(new Doctor(null,
                null,
                "DDD",
                false,
                List.of("surgeon", "veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                2)).getId();

        mockMvc.perform(put("/doctors/{id}",id).contentType("application/json")
                .content(fromResource("DoctorRestAPI/update-doctor.json")))
                .andExpect(status().isAccepted());

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
        Integer id = doctorRepo.save(new Doctor(null,
                null,
                "DDD",
                false,
                List.of("surgeon", "veterinarian"),
                "Politeh",
                LocalDate.parse("2000-01-01"),
                1)).getId();

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