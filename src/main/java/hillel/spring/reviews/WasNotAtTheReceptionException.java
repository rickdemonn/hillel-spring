package hillel.spring.reviews;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "in order to leave a review, first go to the reception")
public class WasNotAtTheReceptionException extends RuntimeException{
}
