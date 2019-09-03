package hillel.spring.reviews;

import hillel.spring.appointments.Appointment;
import hillel.spring.appointments.AppointmentRepo;
import hillel.spring.pet.PetRepo;
import hillel.spring.pet.dto.PetNotFoundException;
import hillel.spring.reviews.dto.ReviewDtoConverter;
import hillel.spring.reviews.dto.ReviewInputWithTimeAndPetIdDto;
import hillel.spring.reviews.dto.ReviewOutputDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import org.hibernate.StaleObjectStateException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {
    private final PetRepo petRepo;
    private final ReviewRepo reviewRepo;
    private final ReviewConfig reviewConfig;
    private final AppointmentRepo appointmentRepo;
    private ReviewReport reviewReport;
    private ReviewDtoConverter dtoConverter;

    public List<Review> findAll() {
        return reviewRepo.findAll();
    }

    public Optional<Review> findById(Integer id){
        return reviewRepo.findById(id);
    }

    public void makeReview(Review review) throws RuntimeException {

        petRepo.findById(review.getPetId()).orElseThrow(PetNotFoundException::new);

        review.setDate(LocalDateTime.now(ZoneId.systemDefault()));

        val mayBeAppointmentsOfPet = appointmentRepo.findByPetId(review.getPetId()).orElseThrow(WasNotAtTheReceptionException::new);

        if (reviewRepo.findByPetId(review.getPetId()).isPresent()) throw new ReviewHasAlreadyBeenLeftException();

        LocalDateTime dateTimeOfAppointment = mayBeAppointmentsOfPet.getLocalDate().atTime(mayBeAppointmentsOfPet.getBusyHour(), 0);
        if (review.getDate().isBefore(dateTimeOfAppointment)) {
            throw new WasNotAtTheReceptionException();
        }

        if (checkReviews(review)) {
            reviewRepo.save(review);
        } else throw new InvalidChoiceOfReviewException();

    }

    private boolean checkReviews(Review review) {
        List<String> list = new ArrayList<>();

        review.getService().ifPresent(list::add);
        review.getEquipment().ifPresent(list::add);
        review.getSpecialistQualification().ifPresent(list::add);
        review.getEffectivenessOfTheTreatment().ifPresent(list::add);
        review.getOverallRating().ifPresent(list::add);

        return list.stream().allMatch(choices -> reviewConfig.getAllowList().stream().anyMatch(choices::equals));
    }

    public ReviewReport getReviewReport() {
        List<Review> reviewList = findAll();

        reviewReport.setServiceAvg(reviewList.stream()
                .filter(x -> x.getService().isPresent())
                .mapToDouble(x -> x.getService().get().chars().count())
                .average()
                .orElse(0));

        reviewReport.setEquipmentAvg(reviewList.stream()
                .filter(x -> x.getEquipment().isPresent())
                .mapToDouble(x -> x.getEquipment().get().chars().count())
                .average()
                .orElse(0));

        reviewReport.setSpecialistQualificationAvg(reviewList.stream()
                .filter(x -> x.getSpecialistQualification().isPresent())
                .mapToDouble(x -> x.getSpecialistQualification().get().chars().count())
                .average()
                .orElse(0));

        reviewReport.setEffectivenessOfTheTreatmentAvg(reviewList.stream()
                .filter(x -> x.getEffectivenessOfTheTreatment().isPresent())
                .mapToDouble(x -> x.getEffectivenessOfTheTreatment().get().chars().count())
                .average()
                .orElse(0));

        reviewReport.setOverallRatingAvg(reviewList.stream()
                .filter(x -> x.getOverallRating().isPresent())
                .mapToDouble(x -> x.getOverallRating().get().chars().count())
                .average()
                .orElse(0));

        reviewReport.setDateToComment(reviewList.stream()
                .filter(x -> x.getComment().isPresent())
                .collect(Collectors.toMap(Review::getDate, Review::getComment)));

        return reviewReport;
    }

    @Retryable(StaleObjectStateException.class)
    public void changeReview(Integer id, ReviewInputWithTimeAndPetIdDto newReview) throws RuntimeException{

        val review = reviewRepo.findById(id).orElseThrow(ReviewNotFoundException::new);

        dtoConverter.update(review, newReview);

        if (checkReviews(review)) {
            reviewRepo.save(review);
        } else throw new InvalidChoiceOfReviewException();

    }
}
