package rs.fon.silab.njt.mojezgradespringboot.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Mesto implements Serializable {

    @Id
    private Long mestoId;
    @NotNull
    private String ptt;
    private String naziv;

    public Mesto() {
    }

    public Mesto(Long mestoId, String ptt, String naziv) {
        this.mestoId = mestoId;
        this.ptt = ptt;
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Long getMestoId() {
        return mestoId;
    }

    public void setMestoId(Long mestoId) {
        this.mestoId = mestoId;
    }

    public String getPtt() {
        return ptt;
    }

    public void setPtt(String ptt) {
        this.ptt = ptt;
    }

    @Override
    public String toString() {
        return naziv;
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
        final Mesto other = (Mesto) obj;
        return Objects.equals(this.mestoId, other.mestoId);
    }
}
