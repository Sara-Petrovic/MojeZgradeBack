package rs.fon.silab.njt.mojezgradespringboot.service;

import java.util.List;
import java.util.Optional;
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

    public Optional<VlasnikPosebnogDela> findById(Long vlasnikId) {
        return repo.findById(vlasnikId);
    }

    public void delete(VlasnikPosebnogDela vlasnik) {
        repo.delete(vlasnik);
    }

    public List<VlasnikPosebnogDela> findAll() {
        return repo.findAll();
    }

    public List<VlasnikPosebnogDela> findByPrezime(String prezimeVlasnika) {
        return repo.findByPrezimeContaining(prezimeVlasnika);
    }

}
