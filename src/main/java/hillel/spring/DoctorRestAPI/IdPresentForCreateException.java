package hillel.spring.DoctorRestAPI;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "id must be not present")
public class IdPresentForCreateException extends RuntimeException{
}
