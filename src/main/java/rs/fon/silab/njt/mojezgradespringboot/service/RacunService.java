package rs.fon.silab.njt.mojezgradespringboot.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.fon.silab.njt.mojezgradespringboot.model.Racun;
import rs.fon.silab.njt.mojezgradespringboot.repository.RacunRepository;

@Service
@Transactional
public class RacunService {
    @Autowired
    private RacunRepository repo;

    public Racun save(Racun r) {
        return repo.save(r);
    }

    public Racun find(Long id) {
        Optional<Racun> optRacun = repo.findById(id);
        if(!optRacun.isPresent()){
            return null;
        }
        return optRacun.get();
    }

    public List<Racun> findAll() {
       return repo.findAll();
    }
    
    
}
