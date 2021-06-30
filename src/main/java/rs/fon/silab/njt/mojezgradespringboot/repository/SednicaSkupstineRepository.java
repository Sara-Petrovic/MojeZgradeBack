package rs.fon.silab.njt.mojezgradespringboot.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.silab.njt.mojezgradespringboot.model.SednicaSkupstine;
import rs.fon.silab.njt.mojezgradespringboot.model.StambenaZajednica;


public interface SednicaSkupstineRepository extends JpaRepository<SednicaSkupstine, Long>{
    List<SednicaSkupstine> findByStambenaZajednica_UlicaContaining(final String ulica);
}
