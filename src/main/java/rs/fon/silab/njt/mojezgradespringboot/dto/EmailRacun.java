package rs.fon.silab.njt.mojezgradespringboot.dto;

import rs.fon.silab.njt.mojezgradespringboot.model.Racun;

public class EmailRacun {
    private Racun racun;
    private String emailPassword;
    private String sifraPlacanja;
    private String valuta;
    private String iznos;
    private String tekuciRacun;
    private String model;
    private String pozivNaBroj;

    public EmailRacun() {
    }

    public EmailRacun(Racun racun, String emailPassword, String sifraPlacanja, String valuta, String iznos, String tekuciRacun, String model, String pozivNaBroj) {
        this.racun = racun;
        this.emailPassword = emailPassword;
        this.sifraPlacanja = sifraPlacanja;
        this.valuta = valuta;
        this.iznos = iznos;
        this.tekuciRacun = tekuciRacun;
        this.model = model;
        this.pozivNaBroj = pozivNaBroj;
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getSifraPlacanja() {
        return sifraPlacanja;
    }

    public void setSifraPlacanja(String sifraPlacanja) {
        this.sifraPlacanja = sifraPlacanja;
    }

    public String getIznos() {
        return iznos;
    }

    public void setIznos(String iznos) {
        this.iznos = iznos;
    }

    public String getTekuciRacun() {
        return tekuciRacun;
    }

    public void setTekuciRacun(String tekuciRacun) {
        this.tekuciRacun = tekuciRacun;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPozivNaBroj() {
        return pozivNaBroj;
    }

    public void setPozivNaBroj(String pozivNaBroj) {
        this.pozivNaBroj = pozivNaBroj;
    }   

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }
}
