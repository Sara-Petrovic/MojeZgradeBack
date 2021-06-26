package rs.fon.silab.njt.mojezgradespringboot.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rs.fon.silab.njt.mojezgradespringboot.model.StambenaZajednica;
import rs.fon.silab.njt.mojezgradespringboot.service.StambenaZajednicaService;

@RestController
public class StambenaZajednicaController {
    
    @Autowired
    private StambenaZajednicaService service;
    
    @PostMapping("/stambenazajednica")
    @CrossOrigin(origins = "http://localhost:4200")
    public StambenaZajednica saveStambenaZajednica(@RequestBody StambenaZajednica sz){
        return service.save(sz);
    }
    @GetMapping("/stambenazajednica")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<StambenaZajednica> getAllStambenaZajednica(){
        return service.getAll();
    }
    
}
