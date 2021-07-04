package rs.fon.silab.njt.mojezgradespringboot.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.silab.njt.mojezgradespringboot.model.StambenaZajednica;


public interface StambenaZajednicaRepository extends JpaRepository<StambenaZajednica, Long>{

    public List<StambenaZajednica> findByPibContains(String pib);

    public List<StambenaZajednica> findByMaticniBrojContains(String maticni_broj);
    
    public List<StambenaZajednica> findByUlicaContainsAndBroj(String ulica, String broj);
    
    public List<StambenaZajednica> findByUlicaContains(String ulica);
    
}
