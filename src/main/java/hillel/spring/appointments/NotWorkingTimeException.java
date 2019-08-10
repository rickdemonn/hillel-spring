package hillel.spring.appointments;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotWorkingTimeException extends RuntimeException {
    public NotWorkingTimeException() {
    }

    public NotWorkingTimeException(Integer busyHour) {
        super("you chose the time to record: " + busyHour + ", Working hours must be between 8 and 17");
    }
}
