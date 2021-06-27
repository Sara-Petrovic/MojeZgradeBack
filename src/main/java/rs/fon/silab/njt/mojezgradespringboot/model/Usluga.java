package rs.fon.silab.njt.mojezgradespringboot.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Usluga implements Serializable{
    @Id
    @GeneratedValue
    private Long uslugaId;
    private String naziv;
    private double cena;
    private JedinicaMere jedinicaMere;

    public Usluga() {
    }

    public Usluga(Long uslugaId, String naziv, double cena, JedinicaMere jedinicaMere) {
        setUslugaId(uslugaId);
        setNaziv(naziv);
        setCena(cena);
        setJedinicaMere(jedinicaMere);
    }

    public Long getUslugaId() {
        return uslugaId;
    }

    public void setUslugaId(Long uslugaId) {
        if(uslugaId < 0){
            throw new RuntimeException("Id usluge ne moze da bude manji od 0.");
        }
        this.uslugaId = uslugaId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
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

    public JedinicaMere getJedinicaMere() {
        return jedinicaMere;
    }

    public void setJedinicaMere(JedinicaMere jedinicaMere) {
        this.jedinicaMere = jedinicaMere;
    }

    @Override
    public String toString() {
        return "Usluga{" + "uslugaId=" + uslugaId + ", naziv=" + naziv + ", cena=" + cena + ", jedinicaMere=" + jedinicaMere + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Usluga other = (Usluga) obj;
        if (!Objects.equals(this.uslugaId, other.uslugaId)) {
            return false;
        }
        return true;
    }
}
