package rs.fon.silab.njt.mojezgradespringboot.service;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.fon.silab.njt.mojezgradespringboot.model.StambenaZajednica;
import rs.fon.silab.njt.mojezgradespringboot.model.User;
import rs.fon.silab.njt.mojezgradespringboot.repository.StambenaZajednicaRepository;
import rs.fon.silab.njt.mojezgradespringboot.repository.UserRepository;

@Service
@Transactional
public class StambenaZajednicaService {

    @Autowired
    private StambenaZajednicaRepository repo;
    @Autowired
    private UserRepository userRepo;

    public StambenaZajednica save(StambenaZajednica sz) {
        return repo.save(sz);
    }

    public StambenaZajednica find(Long id) {
        Optional<StambenaZajednica> optionalResponse = repo.findById(id);
        if (optionalResponse.isPresent()) {
            return optionalResponse.get();
        }
        return null;
    }

    public void delete(StambenaZajednica sz) {
        repo.delete(sz);
    }

    public List<StambenaZajednica> getAll(Long userID) {
        User user = userRepo.getById(userID);
        return repo.findAllByUpravnik(user);
    }

    public List<StambenaZajednica> findByPib(Long userId, String pib) {
        User user = userRepo.getById(userId);
        return repo.findByPibContainsAndUpravnik(pib, user);
    }

    public List<StambenaZajednica> findByMaticniBroj(Long userId, String maticni_broj) {
        User user = userRepo.getById(userId);
        return repo.findByMaticniBrojContainsAndUpravnik(maticni_broj, user);
    }

    public List<StambenaZajednica> findByUlicaIBroj(Long userId, String ulica, String broj) {
        User user = userRepo.getById(userId);
        return repo.findByUlicaContainsAndBrojAndUpravnik(ulica, broj, user);
    }
    
    public List<StambenaZajednica> findByUlica(Long userId, String ulica) {
        User user = userRepo.getById(userId);
        return repo.findByUlicaContainsAndUpravnik(ulica, user);
    }

    public List<StambenaZajednica> getAll() {
        return repo.findAll();
    }

}
