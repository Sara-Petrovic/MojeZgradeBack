package rs.fon.silab.njt.mojezgradespringboot.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.silab.njt.mojezgradespringboot.model.SednicaSkupstine;


public interface SednicaSkupstineRepository extends JpaRepository<SednicaSkupstine, Long> {

    List<SednicaSkupstine> findByStambenaZajednica_UlicaContainingAndStambenaZajednica_Upravnik_UserId(final String ulica, Long userId);

    List<SednicaSkupstine> findByStambenaZajednica_Upravnik_UserId(Long userId);

}
