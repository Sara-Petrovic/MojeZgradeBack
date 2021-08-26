package rs.fon.silab.njt.mojezgradespringboot.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.fon.silab.njt.mojezgradespringboot.model.Mesto;
import rs.fon.silab.njt.mojezgradespringboot.service.MestoService;

@RestController
@CrossOrigin(origins = "*")
public class MestoController {

    @Autowired
    private MestoService service;

    @GetMapping("/mesto")
    public List<Mesto> getAllMesta() {
        return service.getMesta();
    }
}
