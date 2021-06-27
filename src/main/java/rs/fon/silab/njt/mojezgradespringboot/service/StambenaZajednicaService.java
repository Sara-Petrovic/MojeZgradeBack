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

    public StambenaZajednica find(Long szId) {
        Optional<StambenaZajednica> optionalResponse = repo.findById(szId);
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
    
}
