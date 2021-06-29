package rs.fon.silab.njt.mojezgradespringboot.service;

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
}
