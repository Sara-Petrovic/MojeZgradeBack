package rs.fon.silab.njt.mojezgradespringboot.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
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
        //postavljam status racuna na kreiran
        Racun newRacun = new Racun(1l,
                0, r.getDatumIzdavanja(), Status.KREIRAN, optVlasnik.get(), r.getUpravnik());
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

    @GetMapping("/racun/all/status")
    public List<Status> getAllStatusRacuna() {
        return Arrays.stream(Status.values()).filter(value -> {
            try {
                Field field = Status.class.getField(value.toString());
                return !field.isAnnotationPresent(Deprecated.class);
            } catch (Exception e) {
                return false;
            }
        }).collect(Collectors.toList());
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

    // promeni ////////////////////////////////////////////////////////////////////////////////////////////////
    @PutMapping("/racun/{id}")
    public ResponseEntity<?> updateRacun(@PathVariable Long id, @RequestBody RacunDto racun) throws Exception {
        //koristim metodu koju sa ec naoisala da nadjem racun i sve njegove stavke
        ResponseEntity respose = findRacun(id);
        //ako racun ne postoji bacam exception - treba da promenim da ima neku informativnu poruku
        if (respose.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            throw new Exception();
        }
        //ako je pronasao "otpakujem" racunDto
        RacunDto racunPreUpdejta = (RacunDto) respose.getBody();
        //proveravam jos jednom da li mi je nasao racun i se njegove stavke - i ovde treba da dam informativniji exception
        if (racunPreUpdejta == null) {
            throw new Exception();
        }
        //proveravam da li je prosledjen novi vlasnik i ako jeste, da li postoji u bazi
        if (!Objects.equals(racun.getVlasnikPosebnogDela().getVlasnikId(), racunPreUpdejta.getVlasnikPosebnogDela().getVlasnikId())) {
            Optional<VlasnikPosebnogDela> optVlasnik = vlasnikService.findById(racun.getVlasnikPosebnogDela().getVlasnikId());
            if (!optVlasnik.isPresent()) {
                return new ResponseEntity("Vlasnika koga ste proseldili nije u bazi podataka.", HttpStatus.NOT_FOUND);
            }
            racun.setVlasnikPosebnogDela(optVlasnik.get());
        }

        //proveravam da racun nije nekog statusa iz koga ne moze da se promeni
        switch (racunPreUpdejta.getStatus()) {
            case PLACEN:
                return new ResponseEntity<>("Racun ne može da se izmeni nakon što je placen.", HttpStatus.FORBIDDEN);
            case POSLAT:
                return new ResponseEntity<>("Racun ne može da se izmeni nakon što je poslat.", HttpStatus.FORBIDDEN);
        }

        double ukupnaVrednost = 0;

        //sredim rbr stavki i ponovo izracunam ukupnu vrednost
        for (int i = 0; i < racun.getStavke().size(); i++) {
            racun.getStavke().get(i).setRedniBroj(i + 1);
            ukupnaVrednost += racun.getStavke().get(i).getKolicina() * racun.getStavke().get(i).getCena();
        }

        //ne menjam upravnika i id - ostalo azuriram
        Racun updatedRacun = service.update(new Racun(racunPreUpdejta.getRacunId(), ukupnaVrednost, racun.getDatumIzdavanja(), racun.getStatus(), racun.getVlasnikPosebnogDela(), racunPreUpdejta.getUpravnik()),
                racun.getStavke());

        return ResponseEntity.ok().body(new RacunDto(updatedRacun.getRacunId(),
                updatedRacun.getUkupnaVrednost(), updatedRacun.getDatumIzdavanja(),
                updatedRacun.getStatus(), updatedRacun.getVlasnikPosebnogDela(), updatedRacun.getUpravnik(), racun.getStavke()));

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping("/racun/sent/{id}")
    public ResponseEntity<?> racunIsSent(@PathVariable Long id) throws Exception {
        ResponseEntity respose = findRacun(id);
        //ako racun ne postoji bacam exception - treba da promenim da ima neku informativnu poruku
        if (respose.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            throw new Exception();
        }
        //ako je pronasao "otpakujem" racunDto
        RacunDto racunPreUpdejta = (RacunDto) respose.getBody();
        //proveravam jos jednom da li mi je nasao racun i se njegove stavke - i ovde treba da dam informativniji exception
        if (racunPreUpdejta == null) {
            throw new Exception();
        }
        
        switch (racunPreUpdejta.getStatus()) {
            case PLACEN:
                return new ResponseEntity<>("Racun ne može da se posalje nakon što je placen.", HttpStatus.FORBIDDEN);
            case POSLAT:
                return new ResponseEntity<>("Racun ne može da se posalje nakon što je vec poslat.", HttpStatus.FORBIDDEN);
        }
        
        return ResponseEntity.ok().body(service.save(new Racun(racunPreUpdejta.getRacunId(), racunPreUpdejta.getUkupnaVrednost(), racunPreUpdejta.getDatumIzdavanja(), Status.POSLAT, racunPreUpdejta.getVlasnikPosebnogDela(), racunPreUpdejta.getUpravnik())));
    }
    
    @PutMapping("/racun/paid/{id}")
    public ResponseEntity<?> racunIsPaid(@PathVariable Long id) throws Exception {
        ResponseEntity respose = findRacun(id);
        //ako racun ne postoji bacam exception - treba da promenim da ima neku informativnu poruku
        if (respose.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            throw new Exception();
        }
        //ako je pronasao "otpakujem" racunDto
        RacunDto racunPreUpdejta = (RacunDto) respose.getBody();
        //proveravam jos jednom da li mi je nasao racun i se njegove stavke - i ovde treba da dam informativniji exception
        if (racunPreUpdejta == null) {
            throw new Exception();
        }
        
        switch (racunPreUpdejta.getStatus()) {
            case PLACEN:
                return new ResponseEntity<>("Racun ne može da se plati nakon što je vec placen.", HttpStatus.FORBIDDEN);
            }
        
        return ResponseEntity.ok().body(service.save(new Racun(racunPreUpdejta.getRacunId(), racunPreUpdejta.getUkupnaVrednost(), racunPreUpdejta.getDatumIzdavanja(), Status.PLACEN, racunPreUpdejta.getVlasnikPosebnogDela(), racunPreUpdejta.getUpravnik())));
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
