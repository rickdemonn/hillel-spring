package hillel.spring.pet.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PetInputDto {
    @NotEmpty
    private String name;
}
