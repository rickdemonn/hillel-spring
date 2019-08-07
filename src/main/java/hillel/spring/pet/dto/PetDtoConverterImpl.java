package hillel.spring.pet.dto;

import hillel.spring.pet.Pet;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-08-07T11:06:53+0300",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 12.0.2 (Oracle Corporation)"
)
@Component
public class PetDtoConverterImpl implements PetDtoConverter {

    @Override
    public Pet toModel(PetInputDto dto) {
        if ( dto == null ) {
            return null;
        }

        Pet pet = new Pet();

        pet.setName( dto.getName() );

        return pet;
    }

    @Override
    public Pet toModel(Integer id, PetInputDto dto) {
        if ( id == null && dto == null ) {
            return null;
        }

        Pet pet = new Pet();

        if ( id != null ) {
            pet.setId( id );
        }
        if ( dto != null ) {
            pet.setName( dto.getName() );
        }

        return pet;
    }

    @Override
    public PetOutputDto toDto(Pet pet) {
        if ( pet == null ) {
            return null;
        }

        PetOutputDto petOutputDto = new PetOutputDto();

        petOutputDto.setId( pet.getId() );
        petOutputDto.setName( pet.getName() );

        return petOutputDto;
    }
}
