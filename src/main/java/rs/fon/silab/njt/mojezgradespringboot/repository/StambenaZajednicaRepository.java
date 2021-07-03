package rs.fon.silab.njt.mojezgradespringboot.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.silab.njt.mojezgradespringboot.model.StambenaZajednica;


public interface StambenaZajednicaRepository extends JpaRepository<StambenaZajednica, Long>{

    public List<StambenaZajednica> findByPib(String pib);

    public List<StambenaZajednica> findByMaticniBroj(String maticni_broj);
    
    public List<StambenaZajednica> findByUlicaAndBroj(String ulica, String broj);
    
}
