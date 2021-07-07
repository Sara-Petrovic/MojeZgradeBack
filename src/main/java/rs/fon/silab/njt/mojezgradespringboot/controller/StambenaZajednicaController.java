package rs.fon.silab.njt.mojezgradespringboot.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.fon.silab.njt.mojezgradespringboot.exception.ResourceNotFoundException;
import rs.fon.silab.njt.mojezgradespringboot.exception.UnauthorizedException;
import rs.fon.silab.njt.mojezgradespringboot.model.StambenaZajednica;
import rs.fon.silab.njt.mojezgradespringboot.service.StambenaZajednicaService;

@RestController
@CrossOrigin(origins = "*")
public class StambenaZajednicaController {

    @Autowired
    private StambenaZajednicaService service;

    @PostMapping("/stambenazajednica")
    public StambenaZajednica saveStambenaZajednica(@RequestBody StambenaZajednica sz) throws Exception {
        try {
            validateData(sz);
        } catch (Exception e) {
            return null;
        }
        return service.save(sz);
    }

    @GetMapping("/stambenazajednica")
    public List<StambenaZajednica> getAllStambenaZajednica() {
        return service.getAll();
    }

    @GetMapping("/stambenazajednica/all/{userId}/{loginToken}")
    public List<StambenaZajednica> getAllStambenaZajednica(@PathVariable(value = "userId") Long userId, @PathVariable(value = "loginToken") String loginToken) throws UnauthorizedException {
        return service.getAll(userId, loginToken);
    }

    @GetMapping("/stambenazajednica/{id}")
    public ResponseEntity<StambenaZajednica> findStambenaZajednica(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        StambenaZajednica sz = service.find(id);
        if (sz == null) {
            throw new ResourceNotFoundException("Ne postoji stambena zajednica sa ovim id-jem :: " + id);
        }
        return ResponseEntity.ok().body(sz);
    }

    @GetMapping("/stambenazajednica/user/{userId}/{loginToken}/searchbypib")
    public List<StambenaZajednica> findStambenaZajednicaByPib(@PathVariable(value = "userId") Long userId, @PathVariable(value = "loginToken") String loginToken, @RequestParam String pib) throws UnauthorizedException {
        if (pib.length() != 9) {
            return new ArrayList<>();
        }

        List<StambenaZajednica> sz = service.findByPib(userId, loginToken, pib);
        return sz;
    }

    @GetMapping("/stambenazajednica/user/{userId}/{loginToken}/searchbymaticnibroj")
    public List<StambenaZajednica> findStambenaZajednicaByMaticniBroj(@PathVariable(value = "userId") Long userId, @PathVariable(value = "loginToken") String loginToken, @RequestParam String maticni_broj) throws UnauthorizedException {
        if (maticni_broj.length() != 8) {
            return new ArrayList<>();
        }
        List<StambenaZajednica> sz = service.findByMaticniBroj(userId, loginToken, maticni_broj);
        return sz;
    }

    @GetMapping("/stambenazajednica/user/{userId}/{loginToken}/searchbyulicabroj")
    public List<StambenaZajednica> findStambenaZajednicaByUlicaIBroj(@PathVariable(value = "userId") Long userId, @PathVariable(value = "loginToken") String loginToken, @RequestParam String ulica, @RequestParam String broj) throws UnauthorizedException {
        if (broj == null || "".equals(broj) || " ".equals(broj)) {
            return service.findByUlica(userId, loginToken, ulica);
        }
        List<StambenaZajednica> sz = service.findByUlicaIBroj(userId, loginToken, ulica, broj);
        return sz;
    }

    @PutMapping("/stambenazajednica/{id}")
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
