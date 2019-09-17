package hillel.spring.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {

    Page<Doctor> findDistinctBySpecializationsInAndNameStartsWithIgnoreCase(@Param("specializations") List<String> specializations,
                                                                            @Param("name") String name,
                                                                            Pageable pageable);

    Page<Doctor> findByNameStartsWithIgnoreCase(@Param("name") String name, Pageable pageable);

    Page<Doctor> findDistinctBySpecializationsIn(@Param("specializations") List<String> specializations, Pageable pageable);
}
