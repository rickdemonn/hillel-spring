package hillel.spring.pet;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PetService {
    private PetRepo petRepo;

    public Page<Pet> findAll(Pageable pageable) {
        return petRepo.findAll(pageable);
    }

    public Pet createPet(Pet pet) {
        return petRepo.save(pet);
    }

    public void updatePet(Pet pet) {
        petRepo.save(pet);
    }

    public void deletePet(Integer id) {
        petRepo.deleteById(id);
    }

    public Optional<Pet> findPet(Integer id) {
       return petRepo.findById(id);
    }
}
