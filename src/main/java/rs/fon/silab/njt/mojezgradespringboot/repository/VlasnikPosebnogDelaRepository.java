package rs.fon.silab.njt.mojezgradespringboot.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.fon.silab.njt.mojezgradespringboot.model.VlasnikPosebnogDela;


public interface VlasnikPosebnogDelaRepository extends JpaRepository<VlasnikPosebnogDela, Long>{
    //@Query("SELECT u FROM User u WHERE u.status = 1")
    List<VlasnikPosebnogDela> findByPrezimeContaining(String prezime);

    List<VlasnikPosebnogDela> findByStambenaZajednica_StambenaZajednicaId(Long szId);
}
