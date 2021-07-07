package rs.fon.silab.njt.mojezgradespringboot.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class SednicaSkupstine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sednicaSkupstineId;
    private Date datumOdrzavanja;
    private int brojPrisutnih;
    private String dnevniRed;

    @ManyToOne
    @JoinColumn(name = "stambenaZajednicaId")
    private StambenaZajednica stambenaZajednica;

    @ManyToMany(targetEntity = VlasnikPosebnogDela.class, cascade = CascadeType.REMOVE)
    private List<VlasnikPosebnogDela> vlasnici; //set je efikasniji od liste kod manyToMany asocijacije

    public SednicaSkupstine() {
        vlasnici = new ArrayList<VlasnikPosebnogDela>();
    }

    public SednicaSkupstine(Long sednicaSkupstineId, Date datumOdrzavanja,
            int brojPrisutnih, String dnevniRed, StambenaZajednica stambenaZajednica,
            List<VlasnikPosebnogDela> vlasnici) {
        this.sednicaSkupstineId = sednicaSkupstineId;
        this.datumOdrzavanja = datumOdrzavanja;
        this.brojPrisutnih = brojPrisutnih;
        this.dnevniRed = dnevniRed;
        this.stambenaZajednica = stambenaZajednica;
        this.vlasnici = vlasnici;
    }

    public List<VlasnikPosebnogDela> getVlasnici() {
        return vlasnici;
    }

    public void setVlasnici(List<VlasnikPosebnogDela> vlasnici) {
        this.vlasnici = vlasnici;
    }

    public Long getSednicaSkupstineId() {
        return sednicaSkupstineId;
    }

    public void setSednicaSkupstineId(Long sednicaSkupstineId) {
        this.sednicaSkupstineId = sednicaSkupstineId;
    }

    public Date getDatumOdrzavanja() {
        return datumOdrzavanja;
    }

    public void setDatumOdrzavanja(Date datumOdrzavanja) {
        this.datumOdrzavanja = datumOdrzavanja;
    }

    public int getBrojPrisutnih() {
        return brojPrisutnih;
    }

    public void setBrojPrisutnih(int brojPrisutnih) {
        this.brojPrisutnih = brojPrisutnih;
    }

    public String getDnevniRed() {
        return dnevniRed;
    }

    public void setDnevniRed(String dnevniRed) {
        this.dnevniRed = dnevniRed;
    }

    public StambenaZajednica getStambenaZajednica() {
        return stambenaZajednica;
    }

    public void setStambenaZajednica(StambenaZajednica stambenaZajednica) {
        this.stambenaZajednica = stambenaZajednica;
    }

    @Override
    public String toString() {
        return "SednicaSkupstine{" + "sednicaSkupstineId=" + sednicaSkupstineId + ", datumOdrzavanja=" + datumOdrzavanja + ", brojPrisutnih=" + brojPrisutnih + ", dnevniRed=" + dnevniRed + ", stambenaZajednica=" + stambenaZajednica + ", vlasnici=" + vlasnici + '}';
    }

    public void removeVlasnikPosebnogDela(VlasnikPosebnogDela v) {
        this.vlasnici.remove(v);
    }

}
