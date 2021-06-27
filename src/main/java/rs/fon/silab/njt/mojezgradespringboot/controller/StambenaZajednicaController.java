package rs.fon.silab.njt.mojezgradespringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public StambenaZajednica findStambenaZajednica(@RequestParam Long id) throws Exception {
        //implementiraj pretragu po drugim kriterijumima
        return service.find(id);
    }

    @PutMapping("/stambenazajednica")
    @CrossOrigin(origins = "http://localhost:4200")
    public StambenaZajednica editStambenaZajednica(@RequestBody StambenaZajednica sz) throws Exception {
        if (service.find(sz.getStambenaZajednicaId()) == null) {
            throw new Exception("Stambena zajednica ne postoji u bazi.");
        }
        validateData(sz);
        return service.save(sz);
    }
    
    @DeleteMapping("/stambenazajednica")
    @CrossOrigin(origins = "http://localhost:4200")
    public StambenaZajednica deleteStambenaZajednica(@RequestParam Long id) throws Exception {
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
