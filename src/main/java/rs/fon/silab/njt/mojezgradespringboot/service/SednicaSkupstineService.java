package rs.fon.silab.njt.mojezgradespringboot.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.fon.silab.njt.mojezgradespringboot.model.SednicaSkupstine;
import rs.fon.silab.njt.mojezgradespringboot.repository.SednicaSkupstineRepository;

@Service
@Transactional
public class SednicaSkupstineService {

    @Autowired
    private SednicaSkupstineRepository repo;

    public SednicaSkupstine save(SednicaSkupstine sednica) {
        return repo.save(sednica);
    }

    public List<SednicaSkupstine> findAll() {
        return repo.findAll();
    }

    public Optional<SednicaSkupstine> findById(Long sednicaId) {
        return repo.findById(sednicaId);
    }

    public List<SednicaSkupstine> findByUlica(String ulica) {
        return repo.findByStambenaZajednica_UlicaContaining(ulica);
    }

}
