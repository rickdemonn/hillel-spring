package hillel.spring.reviews;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {
    Optional<Review> findByPetId(Integer petId);
    Optional<Review> findById(Integer id);
}
