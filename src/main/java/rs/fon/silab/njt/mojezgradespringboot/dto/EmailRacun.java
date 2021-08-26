package rs.fon.silab.njt.mojezgradespringboot.dto;

import rs.fon.silab.njt.mojezgradespringboot.model.Racun;

public class EmailRacun {
    private Racun racun;
    private String emailPassword;

    public EmailRacun() {
    }
    
    public EmailRacun(Racun racun, String emailPassword) {
        this.racun = racun;
        this.emailPassword = emailPassword;
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
    
    
}
