package hillel.spring.doctor;


import hillel.spring.doctor.dto.DoctorDtoConverter;
import hillel.spring.doctor.dto.DoctorInputDto;
import hillel.spring.doctor.dto.DoctorOutputDto;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
@Slf4j
public class DoctorController {
    private final DoctorService doctorService;
    private final DoctorDtoConverter doctorDtoConverter;
    private final UriComponentsBuilder uriComponentsBuilder;
    private final RestTemplate restTemplate;
    private final DoctorServiceConfig doctorServiceConfig;

    public DoctorController(DoctorService doctorService,
                            DoctorDtoConverter doctorDtoConverter,
                            @Value("${server.address:localhost}") String hostname,
                            DoctorServiceConfig doctorServiceConfig) {
        this.doctorService = doctorService;
        this.doctorDtoConverter = doctorDtoConverter;
        this.restTemplate = resTemplate();
        this.doctorServiceConfig = doctorServiceConfig;
        this.uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(hostname)
                .port(8081)
                .path("/doctors/{id}");
    }

    @GetMapping("/doctors")
    public Page<DoctorOutputDto> findDoctors(@RequestParam Optional<String> name,
                                             @RequestParam Optional<List<String>> specializations,
                                             Pageable pageable) {

        return doctorService.findDoctors(name, specializations, pageable).map(doctorDtoConverter::toDto);

    }

    @GetMapping("/doctors/{id}")
    public DoctorOutputDto findDoctorByID(@PathVariable Integer id) {
        val mayBeDoctor = doctorService.findDoctorByID(id);
        return doctorDtoConverter.toDto(mayBeDoctor.orElseThrow(DoctorNotFoundException::new));
    }

    @PostMapping("/doctors")
    public ResponseEntity<?> createDoctor(@Valid @RequestBody DoctorInputDto docDto) {
        val doctor = doctorDtoConverter.toModel(docDto);

        try {
            log.info("Start http request to info service");
            val docInfo = restTemplate.getForObject(doctorServiceConfig.getDoctorUrl()+ "/info/" + doctor.getDocInfoId(), DocInfo.class);
            log.info("Response: " + docInfo);
            val id = doctorService.createDoctor(doctorDtoConverter.createInfo(doctor,docInfo));
            return ResponseEntity.created(uriComponentsBuilder.build(id)).build();
        } catch (Exception e) {
            log.error("Info not found");
        }
        return ResponseEntity.badRequest().body("Info " + doctor.getDocInfoId() + " not found");
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<?> upDateDoctor(@PathVariable Integer id,
                             @Valid @RequestBody DoctorInputDto docDto) {

        val doctor = doctorDtoConverter.toModel(docDto, id);
        try {
            log.info("Start http request to info service");
            val docInfo = restTemplate.getForObject(doctorServiceConfig.getDoctorUrl()+ "/info/" + docDto.getDocInfoId(), DocInfo.class);
            log.info("Response: " + docInfo);
            doctorService.upDateDoctor(doctor, docInfo);
            return ResponseEntity.accepted().build();
        } catch (DoctorNotFoundException de){
            log.error("Doc not found " + id);
            throw de;
        } catch (Exception e) {
            log.error("Info not found");
        }
        return ResponseEntity.badRequest().body("Info " + doctor.getDocInfoId() + " not found");
    }

    @DeleteMapping("/doctors/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteDoctor(@PathVariable Integer id) {

        doctorService.findDoctorByID(id).orElseThrow(DoctorNotFoundException::new);

        doctorService.deleteDoctor(id);
    }

    @Bean
    public RestTemplate resTemplate() {
        return new RestTemplate();
    }


}
