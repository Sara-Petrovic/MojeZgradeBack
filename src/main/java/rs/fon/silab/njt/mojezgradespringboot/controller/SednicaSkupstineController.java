package rs.fon.silab.njt.mojezgradespringboot.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rs.fon.silab.njt.mojezgradespringboot.exception.ResourceNotFoundException;
import rs.fon.silab.njt.mojezgradespringboot.model.SednicaSkupstine;
import rs.fon.silab.njt.mojezgradespringboot.service.SednicaSkupstineService;

@RestController
@CrossOrigin(origins = "*")
public class SednicaSkupstineController {
    @Autowired
    private SednicaSkupstineService service;

    @PostMapping("/sednicaskupstine")
    public SednicaSkupstine saveSednicaSkupstine(@Valid @RequestBody SednicaSkupstine sednica) {
        System.out.println(sednica);
        return service.save(sednica);
    }
    
    @GetMapping("/sednicaskupstine")
    public List<SednicaSkupstine> getAllSednicaSkupstine() {
        return service.findAll();
    }

    @GetMapping("/sednicaskupstine/{id}")
    public ResponseEntity<SednicaSkupstine> getSednicaSkupstineById(@PathVariable(value = "id") Long sednicaId)
         throws ResourceNotFoundException {
        SednicaSkupstine s = service.findById(sednicaId)
                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji sednica skupstine sa ovim id-jem :: " + sednicaId));
        return ResponseEntity.ok().body(s);
    }
    @GetMapping("/findsednicabyulica/{ulica}")
    public List<SednicaSkupstine> getSednicaSkupstineByUlica(@PathVariable(value = "ulica") String ulica) {
       return service.findByUlica(ulica);
    }
}
