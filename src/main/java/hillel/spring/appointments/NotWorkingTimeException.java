package hillel.spring.appointments;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "working hours must be between 8 and 17")
public class NotWorkingTimeException extends RuntimeException{
}
