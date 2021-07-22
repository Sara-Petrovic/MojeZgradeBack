package rs.fon.silab.njt.mojezgradespringboot.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rs.fon.silab.njt.mojezgradespringboot.exception.ResourceNotFoundException;
import rs.fon.silab.njt.mojezgradespringboot.exception.UnauthorizedException;
import rs.fon.silab.njt.mojezgradespringboot.model.SednicaSkupstine;
import rs.fon.silab.njt.mojezgradespringboot.model.VlasnikPosebnogDela;
import rs.fon.silab.njt.mojezgradespringboot.service.SednicaSkupstineService;
import rs.fon.silab.njt.mojezgradespringboot.service.VlasnikPosebnogDelaService;

@RestController
@CrossOrigin(origins = "*")
public class SednicaSkupstineController {
    @Autowired
    private SednicaSkupstineService service;
                 
     @Autowired
    private VlasnikPosebnogDelaService vlasnikService;

    @PostMapping("/sednicaskupstine")
    public SednicaSkupstine saveSednicaSkupstine(@Valid @RequestBody SednicaSkupstine sednica) {
        System.out.println(sednica);
        return service.save(sednica);
        
//        sednica.getVlasnici().forEach(t -> {
//            Optional<VlasnikPosebnogDela> optionalVlasnik = vlasnikService.findById(t.getVlasnikId());
//        optionalVlasnik.ifPresent(vlasnik -> {
//            vlasnik.getSednice().add(sednica);
//            sednica.getVlasnici().add(vlasnik);
//        });
//        });
        
       // return service.save(sednica);
    }
    
    @GetMapping("/sednice/user/{userid}/{loginToken}")
    public List<SednicaSkupstine> getAllSednicaSkupstine(@PathVariable(value = "userid") Long userId, @PathVariable(value = "loginToken") String loginToken) throws UnauthorizedException {
        return service.findAll(userId, loginToken);
    }

    @GetMapping("/sednicaskupstine/{id}")
    public ResponseEntity<SednicaSkupstine> getSednicaSkupstineById(@PathVariable(value = "id") Long sednicaId)
         throws ResourceNotFoundException {
        SednicaSkupstine s = service.findById(sednicaId)
                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji sednica skupstine sa ovim id-jem :: " + sednicaId));
        return ResponseEntity.ok().body(s);
    }
    @GetMapping("/findsednicabyulica/{ulica}/user/{userid}/{loginToken}")
    public List<SednicaSkupstine> getSednicaSkupstineByUlica(
            @PathVariable(value = "ulica") String ulica,
            @PathVariable(value = "userid") Long userId, 
            @PathVariable(value = "loginToken") String loginToken) throws UnauthorizedException {
       return service.findByUlica(ulica, userId, loginToken);
    }
    
    @PutMapping("/sednicaskupstine/{id}")
    public ResponseEntity<VlasnikPosebnogDela> updateSednicaSkupstine(@PathVariable(value = "id") Long sednicaId,
            @Valid @RequestBody SednicaSkupstine sednicaDetails) throws ResourceNotFoundException {
//        VlasnikPosebnogDela vlasnik = service.findById(vlasnikId)
//                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji vlasnik sa ovim id-jem :: " + vlasnikId));
//
//        vlasnik.setIme(vlasnikDetails.getIme());
//        vlasnik.setPrezime(vlasnikDetails.getPrezime());
//        vlasnik.setBrojPosebnogDela(vlasnikDetails.getBrojPosebnogDela());
//        vlasnik.setKontaktVlasnika(vlasnikDetails.getKontaktVlasnika());
//        vlasnik.setMernaJedinica(vlasnikDetails.getMernaJedinica());
//        vlasnik.setVelicinaPosebnogDela(vlasnikDetails.getVelicinaPosebnogDela());
//        vlasnik.setStambenaZajednica(vlasnikDetails.getStambenaZajednica());
//        final VlasnikPosebnogDela updatedEmployee = service.save(vlasnik);
//        return ResponseEntity.ok(updatedEmployee);
    return null;
    }
}
