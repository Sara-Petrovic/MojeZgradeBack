package rs.fon.silab.njt.mojezgradespringboot.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class StavkaRacunKey implements Serializable{
    private Long racunId;
    private int redniBroj;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.racunId);
        hash = 53 * hash + this.redniBroj;
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
        final StavkaRacunKey other = (StavkaRacunKey) obj;
        if (this.redniBroj != other.redniBroj) {
            return false;
        }
        if (!Objects.equals(this.racunId, other.racunId)) {
            return false;
        }
        return true;
    }

    public Long getRacunId() {
        return racunId;
    }

    public void setRacunId(Long racunId) {
        this.racunId = racunId;
    }

    public int getRedniBroj() {
        return redniBroj;
    }

    public void setRedniBroj(int redniBroj) {
        this.redniBroj = redniBroj;
    }
    
    

}
