package rs.fon.silab.njt.mojezgradespringboot.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.fon.silab.njt.mojezgradespringboot.model.StambenaZajednica;
import rs.fon.silab.njt.mojezgradespringboot.service.StambenaZajednicaService;

@RestController
public class StambenaZajednicaController {

    @Autowired
    private StambenaZajednicaService service;

    @PostMapping("/stambenazajednica")
    @CrossOrigin(origins = "http://localhost:4200")
    public StambenaZajednica saveStambenaZajednica(@RequestBody StambenaZajednica sz) throws Exception {
        validateData(sz);
        return service.save(sz);
    }
    @GetMapping("/stambenazajednica")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<StambenaZajednica> getAllStambenaZajednica(){
        return service.getAll();
    }
    
    @GetMapping("/stambenazajednica/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public StambenaZajednica findStambenaZajednica(@PathVariable(value = "id") Long id) throws Exception {
        return service.find(id);
    }

    @PutMapping("/stambenazajednica/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public StambenaZajednica editStambenaZajednica(@PathVariable(value = "id") Long id, @RequestBody StambenaZajednica sz) throws Exception {
        if (service.find(id) == null) {
            throw new Exception("Stambena zajednica ne postoji u bazi.");
        }
        validateData(sz);
        sz.setStambenaZajednicaId(id);
        return service.save(sz);
    }
    
    @DeleteMapping("/stambenazajednica/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public StambenaZajednica deleteStambenaZajednica(@PathVariable(value = "id") Long id) throws Exception {
        StambenaZajednica sz = findStambenaZajednica(id); 
        service.delete(sz);
        return sz;
    }

    private void validateData(StambenaZajednica sz) throws Exception {
        if(sz.getPib().length() != 9){
            throw new Exception("PIB nije ispravno unet: PIB mora da ima tacno 9 cifara."); 
        }
        if(sz.getMaticniBroj().length() != 8){
            throw new Exception("Maticni broj nije ispravno unet: Maticni broj mora da ima tacno 8 cifara."); 
        }
        if(sz.getTekuciRacun().length() != 18){
            throw new Exception("Broj racuna nije ispravno unet: Broj racuna mora da ima tacno 18 cifara."); 
        }
    }

}
