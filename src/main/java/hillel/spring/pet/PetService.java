package hillel.spring.pet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PetService {
    private PetRepo petRepo;

    public List<Pet> findAll() {
        return petRepo.findAll();
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
