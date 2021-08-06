package rs.fon.silab.njt.mojezgradespringboot.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    @PostMapping("/usluga")
    public ResponseEntity<?> saveUsluga(@RequestBody Usluga u){
        if(u.getCena() < 0){
            return new ResponseEntity("Cena ne moze da bude negativna!", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok().body(service.save(u));
    }
    
    @GetMapping("/usluga/{id}")
    public ResponseEntity<?> findUsluga(@PathVariable(value = "id") Long id){
        Usluga response = service.find(id);
        if(response == null){
            return new ResponseEntity("Ne postoji usliuga sa id-jem:: " + id, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(response);
    }
    
}
