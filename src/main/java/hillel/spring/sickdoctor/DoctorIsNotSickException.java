package hillel.spring.sickdoctor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "doctor is not sick")
public class DoctorIsNotSickException extends RuntimeException {
}
