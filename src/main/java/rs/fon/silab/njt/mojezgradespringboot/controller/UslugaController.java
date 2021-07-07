package rs.fon.silab.njt.mojezgradespringboot.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.fon.silab.njt.mojezgradespringboot.model.Usluga;
import rs.fon.silab.njt.mojezgradespringboot.service.UslugaService;

@RestController
@CrossOrigin(origins = "*")
public class UslugaController {
    @Autowired
    private UslugaService service;
    
    @GetMapping("/usluga")
    public List<Usluga> getAllUsluge(){
        return service.getAll();
    }
    
}
