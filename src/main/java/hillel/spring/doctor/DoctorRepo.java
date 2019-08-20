package hillel.spring.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {

    List<Doctor> findDistinctBySpecializationsInAndNameStartsWithIgnoreCase(@Param("specializations") List<String> specializations,
                                                                            @Param("name") String name);

    List<Doctor> findByNameStartsWithIgnoreCase(@Param("name") String name);

    List<Doctor> findDistinctBySpecializationsIn(@Param("specializations") List<String> specializations);
}
