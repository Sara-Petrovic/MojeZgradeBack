package rs.fon.silab.njt.mojezgradespringboot.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StavkaRacuna implements Serializable{
    @Id
    @GeneratedValue
    private int redniBroj;
    private double cena;
    @ManyToOne
    @JoinColumn(name = "uslugaId")
    private Usluga usluga;
    @ManyToOne
    @JoinColumn(name = "racunId")
    private Racun racun;

    public StavkaRacuna() {
    }

    public StavkaRacuna(int redniBroj, double cena, Usluga usluga, Racun racun) {
        setRedniBroj(redniBroj);
        setCena(cena);
        setUsluga(usluga);
        setRacun(racun);
    }

    public int getRedniBroj() {
        return redniBroj;
    }

    public void setRedniBroj(int redniBroj) {
        if(redniBroj < 1){
            throw new RuntimeException("Redni broj ne moze da bude manji od 1.");
        }
        this.redniBroj = redniBroj;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        if(cena < 0){
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
        this.racun = racun;
    }

    @Override
    public String toString() {
        return "StavkaRacuna{" + "redniBroj=" + redniBroj + ", cena=" + cena + ", usluga=" + usluga + ", racun=" + racun + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        if (this.redniBroj != other.redniBroj) {
            return false;
        }
        if (!Objects.equals(this.racun, other.racun)) {
            return false;
        }
        return true;
    }
    
    
}
