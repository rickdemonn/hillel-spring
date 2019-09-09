package hillel.spring.doctor;


import hillel.spring.doctor.dto.DoctorDtoConverter;
import hillel.spring.doctor.dto.DoctorInputDto;
import hillel.spring.doctor.dto.DoctorOutputDto;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    private final SpecializationsConfig specializationsConfig;
    private final UriComponentsBuilder uriComponentsBuilder;


    public DoctorController(DoctorService doctorService,
                            DoctorDtoConverter doctorDtoConverter,
                            SpecializationsConfig specializationsConfig,
                            @Value("${server.address:localhost}") String hostname) {
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

        val newDoc = doctorService.createDoctor(doctor);
        return ResponseEntity.created(uriComponentsBuilder.build(newDoc.getId())).build();

    }

    @PutMapping("/doctors/{id}")
    @ResponseStatus(NO_CONTENT)
    public void upDateDoctor(@PathVariable Integer id,
                             @Valid @RequestBody DoctorInputDto docDto) {

        doctorService.findDoctorByID(id).orElseThrow(DoctorNotFoundException::new);
        val doctor = doctorDtoConverter.toModel(docDto, id);

        doctorService.upDateDoctor(doctor);
    }

    @DeleteMapping("/doctors/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteDoctor(@PathVariable Integer id) {

        doctorService.findDoctorByID(id).orElseThrow(DoctorNotFoundException::new);

        doctorService.deleteDoctor(id);
    }

}
