package hillel.spring.reviews;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Review Has Already Been Left")
public class ReviewHasAlreadyBeenLeftException extends RuntimeException {
}
