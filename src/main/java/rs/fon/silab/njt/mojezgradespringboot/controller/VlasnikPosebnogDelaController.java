package rs.fon.silab.njt.mojezgradespringboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rs.fon.silab.njt.mojezgradespringboot.exception.ResourceNotFoundException;
import rs.fon.silab.njt.mojezgradespringboot.exception.UnauthorizedException;
import rs.fon.silab.njt.mojezgradespringboot.model.VlasnikPosebnogDela;
import rs.fon.silab.njt.mojezgradespringboot.service.SednicaSkupstineService;
import rs.fon.silab.njt.mojezgradespringboot.service.VlasnikPosebnogDelaService;

@RestController
@CrossOrigin(origins = "*")
public class VlasnikPosebnogDelaController {

    @Autowired
    private VlasnikPosebnogDelaService service;

    @Autowired
    private SednicaSkupstineService sednicaSkupstineService;

    @PostMapping("/vlasnikposebnogdela")
    public VlasnikPosebnogDela saveVlasnikPosebnogDela(@Valid @RequestBody VlasnikPosebnogDela vlasnik) throws Exception {
        try {
            validateData(vlasnik);
        } catch (Exception e) {
            System.out.println("poruka " + e.getMessage());
            throw new Exception(e.getMessage());
        }
        return service.save(vlasnik);
    }

    @GetMapping("/vlasnikposebnogdela/all/{userid}/{loginToken}")
    public List<VlasnikPosebnogDela> getAllVlasnikPosebnogDela(@PathVariable(value = "userid") Long userId, @PathVariable(value = "loginToken") String loginToken) throws UnauthorizedException {
        return service.findAll(userId, loginToken);
    }

    @GetMapping("/findvlasnikbyid/{id}")
    public ResponseEntity<VlasnikPosebnogDela> getVlasnikPosebnogDelaById(@PathVariable(value = "id") Long vlasnikId)
            throws ResourceNotFoundException {
        VlasnikPosebnogDela v = service.findById(vlasnikId)
                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji vlasnik sa ovim id-jem :: " + vlasnikId));
        return ResponseEntity.ok().body(v);
    }

    @GetMapping("/findvlasnikbyprezime/{prezime}/user/{userid}/{loginToken}")
    public List<VlasnikPosebnogDela> getVlasnikPosebnogDelaByPrezime(@PathVariable(value = "userid") Long userId, @PathVariable(value = "loginToken") String loginToken, @PathVariable(value = "prezime") String prezimeVlasnika)
            throws ResourceNotFoundException, UnauthorizedException {
        List<VlasnikPosebnogDela> vlasnici = service.findByPrezime(prezimeVlasnika, userId, loginToken);
        return vlasnici;
    }

    @GetMapping("/findvlasnikbystambenazajednica/{id}/user/{userid}/{loginToken}")
    public List<VlasnikPosebnogDela> getVlasnikPosebnogDelaByStambenaZajednica(@PathVariable(value = "id") Long szId, @PathVariable(value = "userid") Long userId, @PathVariable(value = "loginToken") String loginToken)
            throws ResourceNotFoundException, UnauthorizedException {
        List<VlasnikPosebnogDela> vlasnici = service.findByStambenaZajednica(szId, userId, loginToken);
        return vlasnici;
    }

    @PutMapping("/vlasnikposebnogdela/{id}")
    public ResponseEntity<VlasnikPosebnogDela> updateVlasnikPosebnogDela(@PathVariable(value = "id") Long vlasnikId,
            @Valid @RequestBody VlasnikPosebnogDela vlasnikDetails) throws ResourceNotFoundException {
        VlasnikPosebnogDela vlasnik = service.findById(vlasnikId)
                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji vlasnik sa ovim id-jem :: " + vlasnikId));

        vlasnik.setIme(vlasnikDetails.getIme());
        vlasnik.setPrezime(vlasnikDetails.getPrezime());
        vlasnik.setBrojPosebnogDela(vlasnikDetails.getBrojPosebnogDela());
        vlasnik.setKontaktVlasnika(vlasnikDetails.getKontaktVlasnika());
        vlasnik.setMernaJedinica(vlasnikDetails.getMernaJedinica());
        vlasnik.setVelicinaPosebnogDela(vlasnikDetails.getVelicinaPosebnogDela());
        vlasnik.setStambenaZajednica(vlasnikDetails.getStambenaZajednica());
        final VlasnikPosebnogDela updatedEmployee = service.save(vlasnik);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/vlasnikposebnogdela/{id}")
    public Map<String, Boolean> deleteVlasnikPosebnogDela(@PathVariable(value = "id") Long vlasnikId)
            throws ResourceNotFoundException {
        VlasnikPosebnogDela vlasnik = service.findById(vlasnikId)
                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji vlasnik sa ovim id-jem :: " + vlasnikId));

//        vlasnik.getSednice().forEach(sednica -> {
//            sednica.getVlasnici().remove(vlasnik);
//            service.save(vlasnik);
//        });

        service.delete(vlasnik);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    private void validateData(VlasnikPosebnogDela v) throws Exception {
        String msg = "";
        if (v.getIme().length() < 3) {
            msg += "Ime mora imati najmanje 3 slova. ";
        }
        if (v.getPrezime().length() < 3) {
            msg += "Prezime mora imati najmanje 3 slova. ";
        }
        if (v.getStambenaZajednica() == null) {
            msg += "Morate uneti stambenu zajednicu. ";
        }
        if (v.getBrojPosebnogDela().equals("")) {
            msg += "Morate uneti broj stana vlasnika. ";
        }
        if (!msg.equals("")) {
            throw new Exception(msg);
        }
    }
}
