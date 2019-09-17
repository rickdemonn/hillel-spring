package hillel.spring.reviews;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidChoiceOfReviewException extends RuntimeException{
    public InvalidChoiceOfReviewException() {
    }

    public InvalidChoiceOfReviewException(String message) {
        super(message);
    }
}
