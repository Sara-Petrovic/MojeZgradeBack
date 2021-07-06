package rs.fon.silab.njt.mojezgradespringboot.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.silab.njt.mojezgradespringboot.model.Racun;
import rs.fon.silab.njt.mojezgradespringboot.model.Status;
import rs.fon.silab.njt.mojezgradespringboot.model.User;
import rs.fon.silab.njt.mojezgradespringboot.model.VlasnikPosebnogDela;

public interface RacunRepository extends JpaRepository<Racun, Long>{

    public List<Racun> findByStatusAndUpravnik(Status status, User upravnik);
    
    public List<Racun> findByVlasnikPosebnogDelaAndUpravnik(VlasnikPosebnogDela vlasnik, User upravnik);
    
    public List<Racun> findAllByUpravnik(User upravnik);
    
}
