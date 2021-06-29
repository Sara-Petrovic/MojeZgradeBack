package rs.fon.silab.njt.mojezgradespringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rs.fon.silab.njt.mojezgradespringboot.model.Racun;
import rs.fon.silab.njt.mojezgradespringboot.service.RacunService;

@RestController
@CrossOrigin(origins = "*")
public class RacunController {
    @Autowired
    private RacunService service;
    
    @PostMapping("/racun")
    public Racun saveStambenaZajednica(@RequestBody Racun r){
        return service.save(r);
    }

    
}
