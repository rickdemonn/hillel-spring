package hillel.spring.appointments;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DoctorBusyException extends RuntimeException{
    public DoctorBusyException() {
    }

    public DoctorBusyException(Integer busyHour, Integer docId, LocalDate date ,UriComponentsBuilder uriComponentsBuilder) {
        super("you chose the time to record: " + busyHour + " , But Doctor busy on this time " +
                "you can see schedule of this doctor on " + uriComponentsBuilder.toUriString() + docId + "/schedule/" + date);
    }
}

