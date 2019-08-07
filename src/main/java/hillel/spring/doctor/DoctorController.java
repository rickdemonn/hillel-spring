package hillel.spring.doctor;


import hillel.spring.doctor.dto.DoctorDtoConverter;
import hillel.spring.doctor.dto.DoctorInputDto;
import hillel.spring.doctor.dto.DoctorOutputDto;
import lombok.val;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
public class DoctorController {
    private final DoctorService doctorService;
    private final DoctorDtoConverter doctorDtoConverter;
    private final SpecializationsConfig specializationsConfig;
    private final UriComponentsBuilder uriComponentsBuilder;
    private final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    public DoctorController(DoctorService doctorService,
                            DoctorDtoConverter doctorDtoConverter,
                            SpecializationsConfig specializationsConfig,
                            @Value("${doctor.hostname:localhost}") String hostname) {
        this.doctorService = doctorService;
        this.doctorDtoConverter = doctorDtoConverter;
        this.specializationsConfig = specializationsConfig;
        this.uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(hostname)
                .port(8081)
                .path("/doctors/{id}");
    }

    @GetMapping("/doctors")
    public List<DoctorOutputDto> findDoctors(@RequestParam Optional<String> name,
                                             @RequestParam Optional<List<String>> specializations) {

        return doctorService.findDoctors(name, specializations).stream()
                .map(doctorDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/doctors/{id}")
    public DoctorOutputDto findDoctorByID(@PathVariable Integer id) {
        val mayBeDoctor = doctorService.findDoctorByID(id);
        return doctorDtoConverter.toDto(mayBeDoctor.orElseThrow(DoctorNotFoundException::new));
    }

    @PostMapping("/doctors")
    public ResponseEntity<?> createDoctor(@RequestBody DoctorInputDto docDto) {
        val doctor = doctorDtoConverter.toModel(docDto);

        if (checkSpecializations(doctor)) {
            val newDoc = doctorService.createDoctor(doctor);
            return ResponseEntity.created(uriComponentsBuilder.build(newDoc.getId())).build();
        } else {
            logger.error("Choice specialization: " + doctor.getSpecializations()
                    + " Allow specializations: " + specializationsConfig.getSpecializations().toString());
            throw new WrongSpecializationsException();
        }
    }

    @PutMapping("/doctors/{id}")
    @ResponseStatus(NO_CONTENT)
    public void upDateDoctor(@PathVariable Integer id,
                             @RequestBody DoctorInputDto docDto) {

        doctorService.findDoctorByID(id).orElseThrow(DoctorNotFoundException::new);
        val doctor = doctorDtoConverter.toModel(docDto, id);
        if (checkSpecializations(doctor)) {
            doctorService.upDateDoctor(doctor);
        } else {
            logger.error("Choice specialization: " + doctor.getSpecializations()
                    + " Allow specializations: " + specializationsConfig.getSpecializations().toString());
            throw new WrongSpecializationsException();
        }
    }

    @DeleteMapping("/doctors/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteDoctor(@PathVariable Integer id) {

        doctorService.findDoctorByID(id).orElseThrow(DoctorNotFoundException::new);

        doctorService.deleteDoctor(id);
    }

    private boolean checkSpecializations(Doctor doctor) {
        return doctor.getSpecializations().stream()
                .allMatch(doc -> specializationsConfig.getSpecializations().stream().anyMatch(doc::equals));
    }
}
