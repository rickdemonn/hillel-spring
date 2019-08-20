package hillel.spring.sickdoctor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "the doctor is sick")
public class DoctorIsSickException extends RuntimeException {
}
