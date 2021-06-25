package rs.fon.silab.njt.mojezgradespringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.fon.silab.njt.mojezgradespringboot.model.StambenaZajednica;
import rs.fon.silab.njt.mojezgradespringboot.model.VlasnikPosebnogDela;
import rs.fon.silab.njt.mojezgradespringboot.repository.StambenaZajednicaRepository;
import rs.fon.silab.njt.mojezgradespringboot.repository.VlasnikPosebnogDelaRepository;

@Service
@Transactional
public class VlasnikPosebnogDelaService {

    @Autowired
    private VlasnikPosebnogDelaRepository repo;

    public VlasnikPosebnogDela save(VlasnikPosebnogDela v) {
        return repo.save(v);
    }

}
