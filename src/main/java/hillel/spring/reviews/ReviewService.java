package hillel.spring.reviews;

import hillel.spring.appointments.AppointmentService;
import hillel.spring.pet.PetRepo;
import hillel.spring.pet.dto.PetNotFoundException;
import hillel.spring.reviews.dto.ReviewDtoConverter;
import hillel.spring.reviews.dto.ReviewInputWithTimeAndPetIdDto;
import lombok.AllArgsConstructor;
import lombok.val;
import org.hibernate.StaleObjectStateException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {
    private final PetRepo petRepo;
    private final ReviewRepo reviewRepo;
    private final AppointmentService appointmentService;
    private final ReviewDtoConverter dtoConverter;

    public List<Review> findAll() {
        return reviewRepo.findAll();
    }

    public Optional<Review> findById(Integer id){
        return reviewRepo.findById(id);
    }

    public void makeReview(Review review) {

        petRepo.findById(review.getPetId()).orElseThrow(PetNotFoundException::new);

        review.setDate(LocalDateTime.now(Clock.systemUTC()));

        val mayBeAppointmentsOfPet = appointmentService.findByPetId(review.getPetId()).orElseThrow(WasNotAtTheReceptionException::new);

        if (reviewRepo.findByPetId(review.getPetId()).isPresent()) {
            throw new ReviewHasAlreadyBeenLeftException();
        }

        LocalDateTime dateTimeOfAppointment = mayBeAppointmentsOfPet.getLocalDate().atTime(mayBeAppointmentsOfPet.getBusyHour(), 0);
        if (review.getDate().isBefore(dateTimeOfAppointment)) {
            throw new WasNotAtTheReceptionException();
        }

        reviewRepo.save(review);

    }

    public ReviewReport getReviewReport() {
        List<Review> reviewList = findAll();

        return new ReviewReport(

        reviewList.stream()
                .filter(x -> x.getService().isPresent())
                .mapToDouble(x -> x.getService().get())
                .average()
                .orElse(0),

        reviewList.stream()
                .filter(x -> x.getEquipment().isPresent())
                .mapToDouble(x -> x.getEquipment().get())
                .average()
                .orElse(0),

        reviewList.stream()
                .filter(x -> x.getSpecialistQualification().isPresent())
                .mapToDouble(x -> x.getSpecialistQualification().get())
                .average()
                .orElse(0),

        reviewList.stream()
                .filter(x -> x.getEffectivenessOfTheTreatment().isPresent())
                .mapToDouble(x -> x.getEffectivenessOfTheTreatment().get())
                .average()
                .orElse(0),

        reviewList.stream()
                .filter(x -> x.getOverallRating().isPresent())
                .mapToDouble(x -> x.getOverallRating().get())
                .average()
                .orElse(0),

        reviewList.stream()
                .filter(x -> x.getComment().isPresent())
                .collect(Collectors.toMap(Review::getDate, Review::getComment)));
    }

    @Retryable(StaleObjectStateException.class)
    public void changeReview(Integer id, ReviewInputWithTimeAndPetIdDto newReview) throws RuntimeException{

        val review = reviewRepo.findById(id).orElseThrow(ReviewNotFoundException::new);

        dtoConverter.update(review, newReview);


        reviewRepo.save(review);


    }
}
