package rs.fon.silab.njt.mojezgradespringboot.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.fon.silab.njt.mojezgradespringboot.model.Usluga;
import rs.fon.silab.njt.mojezgradespringboot.repository.UslugaRepository;

@Service
public class UslugaService {
    @Autowired
    private UslugaRepository repo;
    
    public List<Usluga> getAll(){
        return repo.findAll();
    }
    
    public Usluga save(Usluga usluga){
        return repo.save(usluga);
    }

    public Usluga find(Long id) {
        Optional<Usluga> opt = repo.findById(id);
        if(!opt.isPresent()){
            return null;
        }
        return opt.get();
    }
}
