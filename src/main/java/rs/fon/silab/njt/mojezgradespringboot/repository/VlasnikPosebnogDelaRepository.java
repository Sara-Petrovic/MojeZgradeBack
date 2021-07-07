package rs.fon.silab.njt.mojezgradespringboot.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.silab.njt.mojezgradespringboot.model.VlasnikPosebnogDela;


public interface VlasnikPosebnogDelaRepository extends JpaRepository<VlasnikPosebnogDela, Long>{
    //@Query("SELECT u FROM User u WHERE u.status = 1")
    List<VlasnikPosebnogDela> findByPrezimeContainingAndStambenaZajednica_Upravnik_UserId(String prezime, Long userId);

    List<VlasnikPosebnogDela> findByStambenaZajednica_StambenaZajednicaId(Long szId);
    
    List<VlasnikPosebnogDela> findByStambenaZajednica_Upravnik_UserId(Long userId);
}
