package rs.fon.silab.njt.mojezgradespringboot.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Racun implements Serializable{
    @Id
    @GeneratedValue
    private Long racunId;
    private double ukupnaVrednost;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date datumIzdavanja;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "vlasnikId")
    private VlasnikPosebnogDela vlasnikPosebnogDela;

    public Racun() {
    }

    public Racun(Long racunId, double ukupnaVrednost, Date datumIzdavanja, Status status, VlasnikPosebnogDela vlasnikPosebnogDela) {
        setRacunId(racunId);
        setUkupnaVrednost(ukupnaVrednost);
        setDatumIzdavanja(datumIzdavanja);
        setStatus(status);
        setVlasnikPosebnogDela(vlasnikPosebnogDela);
    }

    public VlasnikPosebnogDela getVlasnikPosebnogDela() {
        return vlasnikPosebnogDela;
    }

    public void setVlasnikPosebnogDela(VlasnikPosebnogDela vlasnikPosebnogDela) {
        this.vlasnikPosebnogDela = vlasnikPosebnogDela;
    }

    public Long getRacunId() {
        return racunId;
    }

    public void setRacunId(Long racunId) {
        if(racunId < 0){
            throw new RuntimeException("Id racuna ne moze da bude manji od 0.");
        }
        this.racunId = racunId;
    }

    public double getUkupnaVrednost() {
        return ukupnaVrednost;
    }

    public void setUkupnaVrednost(double ukupnaVrednost) {
        if(ukupnaVrednost < 0){
            throw new RuntimeException("Vrednost ne moze da bude manja od 0.");
        }
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
    
    @Override
    public String toString() {
        return "Racun{" + "racunId=" + racunId + ", ukupnaVrednost=" + ukupnaVrednost + ", datumIzdavanja=" + datumIzdavanja + ", status=" + status + '}';
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
        final Racun other = (Racun) obj;
        if (!Objects.equals(this.racunId, other.racunId)) {
            return false;
        }
        return true;
    }
}
