package hillel.spring.pet;

import hillel.spring.pet.dto.PetDtoConverter;
import hillel.spring.pet.dto.PetInputDto;
import hillel.spring.pet.dto.PetNotFoundException;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
public class PetController {
    private final PetService petService;
    private final PetDtoConverter petDtoConverter;
    private final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(8081)
            .path("/pets/{id}");

    @GetMapping("/pets")
    public List<Pet> findAll() {
        return petService.findAll();
    }

    @GetMapping("/pets/{id}")
    public Pet findPet(@PathVariable Integer id) {
        return petService.findPet(id).orElseThrow(PetNotFoundException::new);
    }

    @PostMapping("/pets")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> createPet(@RequestBody PetInputDto dto) {
        val newPet = petDtoConverter.toModel(dto);
        petService.createPet(newPet);
        return ResponseEntity.created(uriComponentsBuilder.build(newPet.getId())).build();
    }

    @PutMapping("/pets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePet(@PathVariable Integer id, @RequestBody PetInputDto dto) {
        petService.findPet(id).orElseThrow(PetNotFoundException::new);
        petService.updatePet(petDtoConverter.toModel(id, dto));
    }

    @DeleteMapping("/pets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable Integer id) {
        petService.findPet(id).orElseThrow(PetNotFoundException::new);
        petService.deletePet(id);
    }
}
