package hillel.spring.pet.dto;

import hillel.spring.pet.Pet;

public interface PetDtoConverter {
    Pet toModel(PetInputDto dto);
    Pet toModel(Integer id, PetInputDto dto);
    PetOutputDto toDto(Pet pet);
}
