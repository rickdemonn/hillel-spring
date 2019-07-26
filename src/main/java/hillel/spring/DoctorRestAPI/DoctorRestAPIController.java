package hillel.spring.DoctorRestAPI;

import hillel.spring.DoctorRestAPI.dto.DoctorDtoConverter;
import hillel.spring.DoctorRestAPI.dto.DoctorInputDto;
import hillel.spring.DoctorRestAPI.dto.DoctorOutputDto;
import lombok.AllArgsConstructor;
import lombok.val;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
@AllArgsConstructor
public class DoctorRestAPIController {
    private final DoctorRestAPIService doctorRestAPIService;
    private final DoctorDtoConverter doctorDtoConverter = Mappers.getMapper(DoctorDtoConverter.class);

    private final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
                                                                                .scheme("http")
                                                                                .host("localhost")
                                                                                .port(8081)
                                                                                .path("/doctors/{id}");

    @GetMapping("/doctors")
    public List<DoctorOutputDto> findDoctors(@RequestParam Optional<String> specialization,
                                             @RequestParam Optional<String> name){

        Optional<Predicate<Doctor>> mayBeSpecialization = specialization.map(this::filterBySpecialization);
        Optional<Predicate<Doctor>> mayBeFirstLetterOfName = name.map(this::filterByFirstLetterOfName);

        Predicate<Doctor> predicate = Stream.of(mayBeFirstLetterOfName, mayBeSpecialization)
                                            .flatMap(Optional::stream)
                                            .reduce(Predicate::and)
                                            .orElse(doc -> true);


        return doctorRestAPIService.findDoctors(predicate).stream()
                                                          .map(doctorDtoConverter::toDto)
                                                          .collect(Collectors.toList());
    }

    private Predicate<Doctor> filterByFirstLetterOfName(String firstLetterOfName) {
        return doctor -> doctor.getName().startsWith(firstLetterOfName);
    }

    private Predicate<Doctor> filterBySpecialization(String specialization) {
        return doctor -> doctor.getSpecialization().equals(specialization);
    }

    @GetMapping("/doctors/{id}")
    public DoctorOutputDto findDoctorByID(@PathVariable Integer id){
        val mayBeDoctor = doctorRestAPIService.findDoctorByID(id);

        return doctorDtoConverter.toDto(mayBeDoctor.orElseThrow(DoctorNotFoundException::new));
    }

    @PostMapping("/doctors")
    public ResponseEntity<?> createDoctor(@RequestBody DoctorInputDto docDto){
        final val doctor = doctorDtoConverter.toModel(docDto);

        try {
            Integer id = doctorRestAPIService.createDoctor(doctor);
            return ResponseEntity.created(uriComponentsBuilder.build(id)).build();
        } catch (Exception e){
            throw new RuntimeException("Oops, error");
        }
    }

    @PutMapping("/doctors/{id}")
    @ResponseStatus(NO_CONTENT)
    public void upDateDoctor(@PathVariable Integer id,
                             @RequestBody DoctorInputDto docDto){

        doctorRestAPIService.findDoctorByID(id).orElseThrow(DoctorNotFoundException::new);
        final val doctor = doctorDtoConverter.toModel(docDto);

        doctorRestAPIService.upDateDoctor(id,doctor);
    }

    @DeleteMapping("/doctors/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteDoctor(@PathVariable Integer id){

        doctorRestAPIService.findDoctorByID(id).orElseThrow(DoctorNotFoundException::new);

        doctorRestAPIService.deleteDoctor(id);
    }
}
