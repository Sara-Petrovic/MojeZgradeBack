package rs.fon.silab.njt.mojezgradespringboot.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.fon.silab.njt.mojezgradespringboot.model.Mesto;
import rs.fon.silab.njt.mojezgradespringboot.repository.MestoRepository;

@Service
@Transactional
public class MestoService {

    @Autowired
    private MestoRepository repo;

    public List<Mesto> getMesta() {
        return repo.findAll();
    }

}
