package rs.fon.silab.njt.mojezgradespringboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.fon.silab.njt.mojezgradespringboot.dto.RacunDto;
import rs.fon.silab.njt.mojezgradespringboot.exception.UnauthorizedException;
import rs.fon.silab.njt.mojezgradespringboot.model.Racun;
import rs.fon.silab.njt.mojezgradespringboot.model.Status;
import rs.fon.silab.njt.mojezgradespringboot.model.StavkaRacuna;
import rs.fon.silab.njt.mojezgradespringboot.model.VlasnikPosebnogDela;
import rs.fon.silab.njt.mojezgradespringboot.service.RacunService;
import rs.fon.silab.njt.mojezgradespringboot.service.VlasnikPosebnogDelaService;

@RestController
@CrossOrigin(origins = "*")
public class RacunController {

    @Autowired
    private RacunService service;
    @Autowired
    private VlasnikPosebnogDelaService vlasnikService;

    @PostMapping("/racun")
    public ResponseEntity<?> saveRacun(@RequestBody RacunDto r) {
        Optional<VlasnikPosebnogDela> optVlasnik = vlasnikService.findById(r.getVlasnikPosebnogDela().getVlasnikId());
        if (!optVlasnik.isPresent()) {
            return new ResponseEntity("Vlasnika koga ste proseldili nije u bazi podataka.", HttpStatus.NOT_FOUND);
        }
        Racun newRacun = new Racun(1l,
                0, r.getDatumIzdavanja(), r.getStatus(), optVlasnik.get(), r.getUpravnik());
        List<StavkaRacuna> stavke = r.getStavke();
        double ukupnaVrednost = 0;
        for (int i = 0; i < stavke.size(); i++) {
            stavke.get(i).setRedniBroj(i + 1);
            ukupnaVrednost += stavke.get(i).getKolicina() * stavke.get(i).getCena();
        }
        newRacun.setUkupnaVrednost(ukupnaVrednost);
        newRacun = service.save(newRacun, stavke);
        RacunDto returnValue = new RacunDto(newRacun.getRacunId(), newRacun.getUkupnaVrednost(),
                newRacun.getDatumIzdavanja(), newRacun.getStatus(), newRacun.getVlasnikPosebnogDela(), newRacun.getUpravnik(), stavke);
        return ResponseEntity.ok().body(returnValue);
    }

    @GetMapping("/racun/all/{userId}/{loginToken}")
    public ResponseEntity<?> findAllRacun(@PathVariable(value = "userId") Long userId, @PathVariable(value = "loginToken") String loginToken) throws UnauthorizedException {
        List<Racun> r = service.findAll(userId, loginToken);
        return ResponseEntity.ok().body(r);
    }

    @GetMapping("/racun/{id}")
    public ResponseEntity<?> findRacun(@PathVariable Long id) {
        Racun r = service.find(id);
        if (r == null) {
            return new ResponseEntity<>("Racun sa ovim id nije pronadjen.", HttpStatus.NOT_FOUND);
        }
        List<StavkaRacuna> stavke = service.getStavkeRacuna(r);
        return ResponseEntity.ok().body(new RacunDto(r.getRacunId(),
                r.getUkupnaVrednost(), r.getDatumIzdavanja(),
                r.getStatus(), r.getVlasnikPosebnogDela(), r.getUpravnik(), stavke));
    }

    @GetMapping("/racun/user/{userId}/{loginToken}/searchbystatus")
    public ResponseEntity<?> findRacunByStatus(@PathVariable(value = "userId") Long userId, @PathVariable(value = "loginToken") String loginToken, @RequestParam(value = "status") String status) throws UnauthorizedException {

        Status statusEnum = Status.valueOf(status);
        List<Racun> r = service.findByStatus(userId, loginToken, statusEnum);
        return ResponseEntity.ok().body(r);
    }

    @GetMapping("/racun/user/{userId}/{loginToken}/searchbyvlasnik")
    public ResponseEntity<?> findRacunByVlasnik(@PathVariable(value = "userId") Long userId, @PathVariable(value = "loginToken") String loginToken, @RequestParam(value = "vlasnik") Long vlasnik) throws UnauthorizedException {
        Optional<VlasnikPosebnogDela> optVlasnik = vlasnikService.findById(vlasnik);
        if (!optVlasnik.isPresent()) {
            return ResponseEntity.ok().body(new ArrayList<>());
        }
        List<Racun> r = service.findByVlasnik(userId, loginToken, optVlasnik.get());
        return ResponseEntity.ok().body(r);
    }

    @PutMapping("/racun/{id}")
    public ResponseEntity<?> updateRacun(@PathVariable Long id, @RequestBody Racun racun) throws Exception {
        Racun updatedRacun = service.find(id);
        if (updatedRacun == null) {
            throw new Exception();
        }
        updatedRacun.setStatus(racun.getStatus());
        /*
            nije moguce da se menja kog datuma je izdat racun, njegove stavke, na koga glasi ili na koju sumu
         */
        updatedRacun = service.save(updatedRacun);
        List<StavkaRacuna> stavke = service.getStavkeRacuna(updatedRacun);
        return ResponseEntity.ok().body(new RacunDto(updatedRacun.getRacunId(),
                updatedRacun.getUkupnaVrednost(), updatedRacun.getDatumIzdavanja(),
                updatedRacun.getStatus(), updatedRacun.getVlasnikPosebnogDela(), updatedRacun.getUpravnik(), stavke));
    }

    @DeleteMapping("/racun/{id}")
    public ResponseEntity<?> deleteRacun(@PathVariable Long id) throws Exception {
        Racun racun = service.find(id);
        if (racun == null) {
            return new ResponseEntity<>("Racun sa ovom sifrom ne postoji", HttpStatus.NOT_FOUND);
        }
        service.deleteRacun(racun);
        return ResponseEntity.ok().body("Racun sa id-jem: " + id + " je uspešno izbrisan.");
    }
}
