package rs.fon.silab.njt.mojezgradespringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import rs.fon.silab.njt.mojezgradespringboot.service.RacunService;

@RestController
@CrossOrigin(origins = "*")
public class RacunController {
    @Autowired
    private RacunService service;
    
}
