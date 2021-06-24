package rs.fon.silab.njt.mojezgradespringboot.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StambenaZajednica implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long stambenaZajednicaId;
    private String ulica;
    private String broj;
    @ManyToOne
    @JoinColumn(name = "mestoId")
    private Mesto mesto;
    private String tekuciRacun;
    private String banka;
    private String pib;
    private String maticniBroj;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User upravnik;

    public StambenaZajednica() {
    }

    public StambenaZajednica(Long stambenaZajednicaId, String ulica, String broj,
            Mesto mesto, String tekuciRacun, String banka,
            String pib, String maticniBroj, User upravnik) {
        this.stambenaZajednicaId = stambenaZajednicaId;
        this.ulica = ulica;
        this.broj = broj;
        this.mesto = mesto;
        this.tekuciRacun = tekuciRacun;
        this.banka = banka;
        this.pib = pib;
        this.maticniBroj = maticniBroj;
        this.upravnik = upravnik;
    }

    @Override
    public String toString() {
        return ulica + " " + broj + ", " + mesto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StambenaZajednica other = (StambenaZajednica) obj;
        if (!Objects.equals(this.stambenaZajednicaId, other.stambenaZajednicaId)) {
            return false;
        }
        return true;
    }

    public String getBanka() {
        return banka;
    }

    public void setBanka(String banka) {
        this.banka = banka;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getMaticniBroj() {
        return maticniBroj;
    }

    public void setMaticniBroj(String maticniBroj) {
        this.maticniBroj = maticniBroj;
    }

    public String getTekuciRacun() {
        return tekuciRacun;
    }

    public void setTekuciRacun(String tekuciRacun) {
        this.tekuciRacun = tekuciRacun;
    }

    public Long getStambenaZajednicaId() {
        return stambenaZajednicaId;
    }

    public void setStambenaZajednicaId(Long stambenaZajednicaId) {
        this.stambenaZajednicaId = stambenaZajednicaId;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getBroj() {
        return broj;
    }

    public void setBroj(String broj) {
        this.broj = broj;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }

    public User getUpravnik() {
        return upravnik;
    }

    public void setUpravnik(User upravnik) {
        this.upravnik = upravnik;
    }
}
