package hillel.spring.appointments;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "doctor is busy on this time")
public class DoctorBusyException extends RuntimeException{
}

