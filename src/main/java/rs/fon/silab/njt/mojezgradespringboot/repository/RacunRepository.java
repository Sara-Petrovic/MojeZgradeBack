package rs.fon.silab.njt.mojezgradespringboot.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.silab.njt.mojezgradespringboot.model.Racun;
import rs.fon.silab.njt.mojezgradespringboot.model.Status;
import rs.fon.silab.njt.mojezgradespringboot.model.User;
import rs.fon.silab.njt.mojezgradespringboot.model.VlasnikPosebnogDela;

public interface RacunRepository extends JpaRepository<Racun, Long>{

    public List<Racun> findByStatusAndVlasnikPosebnogDela_StambenaZajednica_Upravnik(Status status, User upravnik);
        
    public List<Racun> findAllByVlasnikPosebnogDela_StambenaZajednica_Upravnik(User upravnik);
    
    public List<Racun> findByVlasnikPosebnogDelaAndVlasnikPosebnogDela_StambenaZajednica_Upravnik(VlasnikPosebnogDela vlasnik, User upravnik);
    
    
}
