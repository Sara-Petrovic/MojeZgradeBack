package rs.fon.silab.njt.mojezgradespringboot.service;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.fon.silab.njt.mojezgradespringboot.model.StambenaZajednica;
import rs.fon.silab.njt.mojezgradespringboot.repository.StambenaZajednicaRepository;

@Service
@Transactional
public class StambenaZajednicaService {

    @Autowired
    private StambenaZajednicaRepository repo;

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

    public List<StambenaZajednica> getAll() {
        return repo.findAll();
    }

    public List<StambenaZajednica> findByPib(String pib) {
        return repo.findByPibContains(pib);
    }

    public List<StambenaZajednica> findByMaticniBroj(String maticni_broj) {
        return repo.findByMaticniBrojContains(maticni_broj);
    }

    public List<StambenaZajednica> findByUlicaIBroj(String ulica, String broj) {
        return repo.findByUlicaContainsAndBroj(ulica, broj);
    }
    
    public List<StambenaZajednica> findByUlica(String ulica) {
        return repo.findByUlicaContains(ulica);
    }

}
