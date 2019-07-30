package hillel.spring.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {

    List<Doctor> findBySpecializationAndNameStartsWithIgnoreCase(@Param("specialization") String specialization, @Param("name") String name);

    List<Doctor> findBySpecialization(@Param("specialization") String specialization);

    List<Doctor> findByNameStartsWithIgnoreCase(@Param("name") String name);

    List<Doctor> findAllBySpecializationIn(@Param("specializations") List<String> specializations);
}
