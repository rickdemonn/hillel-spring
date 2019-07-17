package hillel.spring.DoctorRestAPI;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
@AllArgsConstructor
public class DoctorRestAPIController {
    private final DoctorRestAPIService doctorRestAPIService;

    @GetMapping("/doctors")
    public List<Doctor> findAllDoctors(){
        return doctorRestAPIService.findAllDoctors();
    }

    @GetMapping("/doctors/{id}")
    public Doctor findDoctorByID(@PathVariable Integer id){
        val mayBeDoctor = doctorRestAPIService.findDoctorByID(id);

        return mayBeDoctor.orElseThrow(DoctorNotFoundException::new);
    }

    @GetMapping(value = "/doctors",params = "specialization")
    public List<Doctor> findDoctorBySpecialization(@PathParam("specialization") String specialization){
        return doctorRestAPIService.findDoctorBySpecialization(specialization);
    }

    @GetMapping(value = "/doctors",params = "name")
    public List<Doctor> findDoctorsByFirstLetter(@PathParam("name") String name){
        return doctorRestAPIService.findDoctorsByFirstLetter(name);
    }

    @PostMapping("/doctors")
    public ResponseEntity<?> createDoctor(@RequestBody Doctor doctor){
        if (doctor.getId() != null) {
            throw new IdPresentForCreateException();
        }

        try {
            Integer id = doctorRestAPIService.createDoctor(doctor);
            doctor.setId(id);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .buildAndExpand(doctor).toUri();

            return ResponseEntity.created(location).body(doctor);
        } catch (Exception e){
            throw new RuntimeException("Oops, error");
        }
    }

    @PutMapping("/doctors/{id}")
    @ResponseStatus(NO_CONTENT)
    public void upDateDoctor(@PathVariable Integer id,
                                          @RequestBody Doctor doctor){
        if(!id.equals(doctor.getId())){
            throw new IdNotEqualsForUpdateDoctorException();
        }

        doctorRestAPIService.findDoctorByID(id).orElseThrow(DoctorNotFoundException::new);

        doctorRestAPIService.upDateDoctor(id,doctor);
    }

    @DeleteMapping("/doctors/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteDoctor(@PathVariable Integer id){

        doctorRestAPIService.findDoctorByID(id).orElseThrow(DoctorNotFoundException::new);

        doctorRestAPIService.deleteDoctor(id);
    }
}
