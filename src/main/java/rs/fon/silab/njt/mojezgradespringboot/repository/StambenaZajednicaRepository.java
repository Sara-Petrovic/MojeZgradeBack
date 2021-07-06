package rs.fon.silab.njt.mojezgradespringboot.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.silab.njt.mojezgradespringboot.model.StambenaZajednica;
import rs.fon.silab.njt.mojezgradespringboot.model.User;


public interface StambenaZajednicaRepository extends JpaRepository<StambenaZajednica, Long>{

    public List<StambenaZajednica> findByPibContainsAndUpravnik(String pib, User upravnik);

    public List<StambenaZajednica> findByMaticniBrojContainsAndUpravnik(String maticni_broj, User upravnik);
    
    public List<StambenaZajednica> findByUlicaContainsAndBrojAndUpravnik(String ulica, String broj, User upravnik);
    
    public List<StambenaZajednica> findByUlicaContainsAndUpravnik(String ulica, User upravnik);

    public List<StambenaZajednica> findAllByUpravnik(User upravnik);
    
}
