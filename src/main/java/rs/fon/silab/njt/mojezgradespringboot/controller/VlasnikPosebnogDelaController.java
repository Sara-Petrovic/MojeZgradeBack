package rs.fon.silab.njt.mojezgradespringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rs.fon.silab.njt.mojezgradespringboot.model.VlasnikPosebnogDela;
import rs.fon.silab.njt.mojezgradespringboot.service.VlasnikPosebnogDelaService;


public class VlasnikPosebnogDelaController {
    @Autowired
    private VlasnikPosebnogDelaService service;
    
    @PostMapping("/vlasnikposebnogdela")
    @CrossOrigin(origins = "http://localhost:4200")
    public VlasnikPosebnogDela saveVlasnikPosebnogDela(@RequestBody VlasnikPosebnogDela v){
        return service.save(v);
    }
}
