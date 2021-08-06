package rs.fon.silab.njt.mojezgradespringboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StavkaRacuna implements Serializable {

    @EmbeddedId
    private StavkaRacunKey stavkaRacunaId;
    private double cena;
    private double kolicina;
    @ManyToOne
    @JoinColumn(name = "uslugaId")
    private Usluga usluga;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "racunId", insertable = false, updatable = false)
    @JsonIgnore
    private Racun racun;

    public StavkaRacuna() {
        stavkaRacunaId = new StavkaRacunKey();
    }

    public StavkaRacuna(int redniBroj, double cena, double kolicina, Usluga usluga, Racun racun) {
        stavkaRacunaId = new StavkaRacunKey();
        this.stavkaRacunaId.setRacunId(racun.getRacunId());
        setRedniBroj(redniBroj);
        setCena(cena);
        setKolicina(kolicina);
        setUsluga(usluga);
        setRacun(racun);
    }

    public int getRedniBroj() {
        return stavkaRacunaId.getRedniBroj();
    }

    public void setRedniBroj(int redniBroj) {
        if (redniBroj < 1) {
            throw new RuntimeException("Redni broj ne moze da bude manji od 1.");
        }
        stavkaRacunaId.setRedniBroj(redniBroj);
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        if (cena < 0) {
            throw new RuntimeException("Cena ne moze da bude manja od 0.");
        }
        this.cena = cena;
    }

    public Usluga getUsluga() {
        return usluga;
    }

    public void setUsluga(Usluga usluga) {
        this.usluga = usluga;
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.stavkaRacunaId.setRacunId(racun.getRacunId());
        this.racun = racun;
    }

    public double getKolicina() {
        return kolicina;
    }

    public void setKolicina(double kolicina) {
        this.kolicina = kolicina;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.stavkaRacunaId);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.cena) ^ (Double.doubleToLongBits(this.cena) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.kolicina) ^ (Double.doubleToLongBits(this.kolicina) >>> 32));
        hash = 59 * hash + Objects.hashCode(this.usluga);
        hash = 59 * hash + Objects.hashCode(this.racun);
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
        final StavkaRacuna other = (StavkaRacuna) obj;
        if (!Objects.equals(this.stavkaRacunaId, other.stavkaRacunaId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StavkaRacuna{" + "stavkaRacunaId=" + stavkaRacunaId + ", cena=" + cena + ", kolicina=" + kolicina + ", usluga=" + usluga + ", racun=" + racun + '}';
    }
   

}
