package rs.fon.silab.njt.mojezgradespringboot.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class VlasnikPosebnogDela implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long vlasnikId;

    @NotNull
    @Size(min=3, message="Ime mora imati najmanje 3 karaktera.")
    private String ime;
    
    @NotNull
    @Size(min=3, message="Prezime mora imati najmanje 3 karaktera.")
    private String prezime;
    
    @NotEmpty
    private String brojPosebnogDela;
    
    private double velicinaPosebnogDela;
    
    @Enumerated(EnumType.STRING)
    private JedinicaMere mernaJedinica;
    
    private String kontaktVlasnika;
    
    @ManyToOne
    @JoinColumn(name = "stambenaZajednicaId")
    @NotNull
    private StambenaZajednica stambenaZajednica;

    public VlasnikPosebnogDela() {
    }

    public VlasnikPosebnogDela(Long vlasnikId, String ime, String prezime, 
            String brojPosebnogDela, double velicinaPosebnogDela, 
            String kontaktVlasnika, StambenaZajednica stambenaZajednica) {
        this.vlasnikId = vlasnikId;
        this.ime = ime;
        this.prezime = prezime;
        this.brojPosebnogDela = brojPosebnogDela;
        this.velicinaPosebnogDela = velicinaPosebnogDela;
        this.kontaktVlasnika = kontaktVlasnika;
        this.stambenaZajednica = stambenaZajednica;
        //default:
        mernaJedinica = JedinicaMere.KVADRATNI_METAR;
    }

    public StambenaZajednica getStambenaZajednica() {
        return stambenaZajednica;
    }

    public void setStambenaZajednica(StambenaZajednica stambenaZajednica) {
        this.stambenaZajednica = stambenaZajednica;
    }

    public Long getVlasnikId() {
        return vlasnikId;
    }

    public void setVlasnikId(Long vlasnikId) {
        this.vlasnikId = vlasnikId;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getBrojPosebnogDela() {
        return brojPosebnogDela;
    }

    public void setBrojPosebnogDela(String brojPosebnogDela) {
        this.brojPosebnogDela = brojPosebnogDela;
    }

    public double getVelicinaPosebnogDela() {
        return velicinaPosebnogDela;
    }

    public void setVelicinaPosebnogDela(double velicinaPosebnogDela) {
        this.velicinaPosebnogDela = velicinaPosebnogDela;
    }

    public String getKontaktVlasnika() {
        return kontaktVlasnika;
    }

    public void setKontaktVlasnika(String kontaktVlasnika) {
        this.kontaktVlasnika = kontaktVlasnika;
    }

    public JedinicaMere getMernaJedinica() {
        return mernaJedinica;
    }

    public void setMernaJedinica(JedinicaMere mernaJedinica) {
        this.mernaJedinica = mernaJedinica;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }


}
