package rs.fon.silab.njt.mojezgradespringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public Racun saveRacun(@RequestBody Racun r){
        return service.save(r);
    }
    
    @GetMapping("/racun/{id}")
    public ResponseEntity<?> findRacun(@PathVariable Long id){
        Racun r =  service.find(id);
        if(r == null){
            return new ResponseEntity<>("Racun sa ovim id nije pronadjen.",HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(r);
    }

    @PutMapping("/racun/{id}")
    public ResponseEntity<?> updateRacun(@PathVariable Long id, @RequestBody Racun racun) throws Exception{
        Racun updatedRacun =  service.find(id);
        if(updatedRacun == null){
            throw new Exception();
        }
        updatedRacun.setStatus(racun.getStatus());
        /*
            nije moguce da se menja kog datuma je izdat racun, na koga glasi ili na koju sumu
        */
        return ResponseEntity.ok().body(service.save(updatedRacun));
    }
    
}
