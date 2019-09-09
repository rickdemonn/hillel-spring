package hillel.spring.reviews;

import hillel.spring.pet.dto.PetNotFoundException;
import hillel.spring.reviews.dto.ReviewDtoConverter;
import hillel.spring.reviews.dto.ReviewInputDto;
import hillel.spring.reviews.dto.ReviewInputWithTimeAndPetIdDto;
import hillel.spring.reviews.dto.ReviewOutputDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewDtoConverter reviewDtoConverter;

    @GetMapping("/reviews")
    public List<Review> findReviews() {
        return reviewService.findAll();
    }

    @PostMapping("/reviews/{petId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void makeReview(@PathVariable Integer petId,
                           @Valid @RequestBody ReviewInputDto dto) {

        val review = reviewDtoConverter.toModel(dto, petId);
        try {
            reviewService.makeReview(review);
        } catch (PetNotFoundException pe) {
            log.error("selected pet id : " + petId + " .But this pet not found");
            throw pe;
        } catch (WasNotAtTheReceptionException we) {
            log.error("try to leave a review before visiting a doctor");
            throw we;
        } catch (ReviewHasAlreadyBeenLeftException re) {
            log.error("Review Has Already Been Left");
            throw re;
        }
    }

    @GetMapping("/reviews/report")
    public ReviewOutputDto getReviewReport(){
        return reviewDtoConverter.toDto(reviewService.getReviewReport());
    }

    @PatchMapping("/reviews/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changeReview(@PathVariable Integer id, @Valid @RequestBody ReviewInputWithTimeAndPetIdDto dto){

        try {
            reviewService.changeReview(id, dto);
        } catch (ReviewNotFoundException re){
            log.error("Review not found");
            throw re;
        }
    }
}
