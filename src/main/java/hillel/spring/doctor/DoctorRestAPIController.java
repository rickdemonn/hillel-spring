package hillel.spring.doctor;


import hillel.spring.doctor.dto.DoctorDtoConverterImpl;
import hillel.spring.doctor.dto.DoctorInputDto;
import hillel.spring.doctor.dto.DoctorOutputDto;
import lombok.val;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
public class DoctorRestAPIController {
    private final DoctorRestAPIService doctorRestAPIService;
    private final DoctorDtoConverterImpl doctorDtoConverter;
    private final SpecializationsConfig specializationsConfig;
    private final UriComponentsBuilder uriComponentsBuilder;

    public DoctorRestAPIController(DoctorRestAPIService doctorRestAPIService,
                                   DoctorDtoConverterImpl doctorDtoConverter,
                                   SpecializationsConfig specializationsConfig,
                                   @Value("${doctor.hostname:localhost}") String hostname) {
        this.doctorRestAPIService = doctorRestAPIService;
        this.doctorDtoConverter = doctorDtoConverter;
        this.specializationsConfig = specializationsConfig;
        this.uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(hostname)
                .port(8081)
                .path("/doctors/{id}");
    }

    @GetMapping("/doctors")
    public List<DoctorOutputDto> findDoctors(@RequestParam Optional<String> specialization,
                                             @RequestParam Optional<String> name,
                                             @RequestParam Optional<List<String>> specializations){

        return doctorRestAPIService.findDoctors(specialization, name, specializations).stream()
                                                          .map(doctorDtoConverter::toDto)
                                                          .collect(Collectors.toList());
    }

    @GetMapping("/doctors/{id}")
    public DoctorOutputDto findDoctorByID(@PathVariable Integer id){
        val mayBeDoctor = doctorRestAPIService.findDoctorByID(id);

        return doctorDtoConverter.toDto(mayBeDoctor.orElseThrow(DoctorNotFoundException::new));
    }

    @PostMapping("/doctors")
    public ResponseEntity<?> createDoctor(@RequestBody DoctorInputDto docDto){
        final val doctor = doctorDtoConverter.toModel(docDto);

        if(checkSpecialization(doctor)) {
            try {
                final val newDoc = doctorRestAPIService.createDoctor(doctor);
                return ResponseEntity.created(uriComponentsBuilder.build(newDoc.getId())).build();
            } catch (Exception e) {
                throw new RuntimeException("Oops, error");
            }
        } else throw new WrongSpecializationsException();
    }

    @PutMapping("/doctors/{id}")
    @ResponseStatus(NO_CONTENT)
    public void upDateDoctor(@PathVariable Integer id,
                             @RequestBody DoctorInputDto docDto){

        doctorRestAPIService.findDoctorByID(id).orElseThrow(DoctorNotFoundException::new);
        final val doctor = doctorDtoConverter.toModel(docDto);
        doctor.setId(id);
        if(checkSpecialization(doctor)) {
            doctorRestAPIService.upDateDoctor(doctor);
        } else throw new WrongSpecializationsException();
    }

    @DeleteMapping("/doctors/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteDoctor(@PathVariable Integer id){

        doctorRestAPIService.findDoctorByID(id).orElseThrow(DoctorNotFoundException::new);

        doctorRestAPIService.deleteDoctor(id);
    }

    private boolean checkSpecialization(Doctor doctor) {
        return specializationsConfig.getSpecializations().stream()
                .anyMatch(spc -> spc.equals(doctor.getSpecialization()));
    }
}
