package hillel.spring.doctor.dto;

import hillel.spring.doctor.SpecializationsConfig;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AllowSpecializationsValidator implements ConstraintValidator<AllowSpecializations, String> {
   @Autowired
   private SpecializationsConfig specializationsConfig;


   public boolean isValid(String value, ConstraintValidatorContext context) {
      return value == null || specializationsConfig.getSpecializations().stream().anyMatch(value::equals);
   }
}
