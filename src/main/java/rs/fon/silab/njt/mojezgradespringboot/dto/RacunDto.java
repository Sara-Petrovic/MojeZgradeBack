package rs.fon.silab.njt.mojezgradespringboot.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.fon.silab.njt.mojezgradespringboot.model.Status;
import rs.fon.silab.njt.mojezgradespringboot.model.StavkaRacuna;
import rs.fon.silab.njt.mojezgradespringboot.model.User;
import rs.fon.silab.njt.mojezgradespringboot.model.VlasnikPosebnogDela;


public class RacunDto {
    private Long racunId;
    private double ukupnaVrednost;
    private Date datumIzdavanja;
    private Status status;
    private VlasnikPosebnogDela vlasnikPosebnogDela;
    private User upravnik;
    private List<StavkaRacuna> stavke;

    public RacunDto() {
        this.stavke = new ArrayList<>();
    }

    public RacunDto(Long racunId, double ukupnaVrednost, Date datumIzdavanja, Status status, VlasnikPosebnogDela vlasnikPosebnogDela, User upravnik, List<StavkaRacuna> stavke) {
        this.racunId = racunId;
        this.ukupnaVrednost = ukupnaVrednost;
        this.datumIzdavanja = datumIzdavanja;
        this.status = status;
        this.vlasnikPosebnogDela = vlasnikPosebnogDela;
        this.upravnik = upravnik;
        this.stavke = stavke;
    }

    public RacunDto(Long racunId, double ukupnaVrednost, Date datumIzdavanja, Status status, User upravnik, VlasnikPosebnogDela vlasnikPosebnogDela) {
        this.racunId = racunId;
        this.ukupnaVrednost = ukupnaVrednost;
        this.datumIzdavanja = datumIzdavanja;
        this.status = status;
        this.vlasnikPosebnogDela = vlasnikPosebnogDela;
        this.upravnik = upravnik;
        this.stavke = new ArrayList<>();
    }

    public Long getRacunId() {
        return racunId;
    }

    public void setRacunId(Long racunId) {
        this.racunId = racunId;
    }

    public double getUkupnaVrednost() {
        return ukupnaVrednost;
    }

    public void setUkupnaVrednost(double ukupnaVrednost) {
        this.ukupnaVrednost = ukupnaVrednost;
    }

    public Date getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(Date datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public VlasnikPosebnogDela getVlasnikPosebnogDela() {
        return vlasnikPosebnogDela;
    }

    public void setVlasnikPosebnogDela(VlasnikPosebnogDela vlasnikPosebnogDela) {
        this.vlasnikPosebnogDela = vlasnikPosebnogDela;
    }

    public List<StavkaRacuna> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaRacuna> stavke) {
        this.stavke = stavke;
    }

    public User getUpravnik() {
        return upravnik;
    }

    public void setUpravnik(User upravnik) {
        this.upravnik = upravnik;
    }
    
}
