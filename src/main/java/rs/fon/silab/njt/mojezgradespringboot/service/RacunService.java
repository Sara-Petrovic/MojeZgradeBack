package rs.fon.silab.njt.mojezgradespringboot.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.fon.silab.njt.mojezgradespringboot.model.Racun;
import rs.fon.silab.njt.mojezgradespringboot.model.Status;
import rs.fon.silab.njt.mojezgradespringboot.model.StavkaRacuna;
import rs.fon.silab.njt.mojezgradespringboot.model.VlasnikPosebnogDela;
import rs.fon.silab.njt.mojezgradespringboot.repository.RacunRepository;
import rs.fon.silab.njt.mojezgradespringboot.repository.StavkaRacunaRepository;

@Service
@Transactional
public class RacunService {

    @Autowired
    private RacunRepository repo;
    @Autowired
    private StavkaRacunaRepository stavkeRacnaRepo;

    public Racun save(Racun r, List<StavkaRacuna> stavke) {
        Racun saved = repo.save(r);
        for (StavkaRacuna stavka : stavke) {
            stavka.setRacun(saved);
        }
        stavkeRacnaRepo.saveAll(stavke);
        return saved;
    }

    public Racun save(Racun r) {
        return repo.save(r);
    }

    public Racun find(Long id) {
        Optional<Racun> optRacun = repo.findById(id);
        if (!optRacun.isPresent()) {
            return null;
        }
        return optRacun.get();
    }

    public List<Racun> findAll() {
        return repo.findAll();
    }

    public List<Racun> findByStatus(Status statusEnum) {
        return repo.findByStatus(statusEnum);
    }

    public List<Racun> findByVlasnik(VlasnikPosebnogDela vlasnik) {
        return repo.findByVlasnikPosebnogDela(vlasnik);
    }

    public void deleteRacun(Racun racun) {
        repo.delete(racun);
    }

    public List<StavkaRacuna> getStavkeRacuna(Racun racun) {
       Optional<Racun> optRacun = repo.findById(racun.getRacunId());
        if (!optRacun.isPresent()) {
            return null;
        }
        Racun r = optRacun.get();
        return stavkeRacnaRepo.findAllByRacun(r);
    }
}
