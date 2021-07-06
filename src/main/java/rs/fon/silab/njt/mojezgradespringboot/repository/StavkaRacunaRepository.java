package rs.fon.silab.njt.mojezgradespringboot.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.silab.njt.mojezgradespringboot.model.Racun;
import rs.fon.silab.njt.mojezgradespringboot.model.StavkaRacuna;

public interface StavkaRacunaRepository extends JpaRepository<StavkaRacuna, Long>{

    public List<StavkaRacuna> findAllByRacun(Racun racun);
    
}
