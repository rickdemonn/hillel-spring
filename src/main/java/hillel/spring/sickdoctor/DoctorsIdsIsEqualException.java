package hillel.spring.sickdoctor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "doctors id's is equal")
public class DoctorsIdsIsEqualException extends RuntimeException {
}
