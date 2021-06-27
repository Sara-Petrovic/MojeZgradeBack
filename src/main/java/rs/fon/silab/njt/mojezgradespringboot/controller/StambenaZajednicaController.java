package rs.fon.silab.njt.mojezgradespringboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rs.fon.silab.njt.mojezgradespringboot.exception.ResourceNotFoundException;
import rs.fon.silab.njt.mojezgradespringboot.model.StambenaZajednica;
import rs.fon.silab.njt.mojezgradespringboot.service.StambenaZajednicaService;

@RestController
public class StambenaZajednicaController {

    @Autowired
    private StambenaZajednicaService service;

    @PostMapping("/stambenazajednica")
    @CrossOrigin(origins = "http://localhost:4200")
    public StambenaZajednica saveStambenaZajednica(@RequestBody StambenaZajednica sz) throws Exception {
        try {
            validateData(sz);
        } catch (Exception e) {
            return null;
        } 
        return service.save(sz);
    }

    @GetMapping("/stambenazajednica")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<StambenaZajednica> getAllStambenaZajednica() {
        return service.getAll();
    }

    @GetMapping("/stambenazajednica/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<StambenaZajednica> findStambenaZajednica(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        StambenaZajednica sz = service.find(id);
        if (sz == null) {
            throw new ResourceNotFoundException("Ne postoji stambena zajednica sa ovim id-jem :: " + id);
        }
        return ResponseEntity.ok().body(sz);
    }

    @PutMapping("/stambenazajednica/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<StambenaZajednica> updateStambenaZajednica(@PathVariable(value = "id") Long id, @RequestBody StambenaZajednica szDetails) throws Exception {
        StambenaZajednica sz = service.find(id);
        if (sz == null) {
            throw new ResourceNotFoundException("Ne postoji stambena zajednica sa ovim id-jem :: " + id);
        }
        try {
            validateData(szDetails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }   
        sz.setUlica(szDetails.getUlica());
        sz.setBroj(szDetails.getBroj());
        sz.setBanka(szDetails.getBanka());
        sz.setPib(szDetails.getPib());
        sz.setMesto(szDetails.getMesto());
        sz.setMaticniBroj(szDetails.getMaticniBroj());
        sz.setTekuciRacun(szDetails.getTekuciRacun());
        sz.setUpravnik(szDetails.getUpravnik());
        sz.setStambenaZajednicaId(szDetails.getStambenaZajednicaId());

        StambenaZajednica updatedSZ = service.save(sz);
        return ResponseEntity.ok(updatedSZ);
    }

    @DeleteMapping("/stambenazajednica/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, Boolean> deleteStambenaZajednica(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        StambenaZajednica sz = service.find(id);
        if (sz == null) {
            throw new ResourceNotFoundException("Ne postoji vlasnik sa ovim id-jem :: " + id);
        }
        service.delete(sz);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    private void validateData(StambenaZajednica sz) throws Exception {
        if (sz.getPib().length() != 9) {
            throw new Exception("PIB nije ispravno unet: PIB mora da ima tacno 9 cifara.");
        }
        if (sz.getMaticniBroj().length() != 8) {
            throw new Exception("Maticni broj nije ispravno unet: Maticni broj mora da ima tacno 8 cifara.");
        }
        if (sz.getTekuciRacun().length() != 18) {
            throw new Exception("Broj racuna nije ispravno unet: Broj racuna mora da ima tacno 18 cifara.");
        }
    }

}
