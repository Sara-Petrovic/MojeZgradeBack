package rs.fon.silab.njt.mojezgradespringboot.service;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.fon.silab.njt.mojezgradespringboot.exception.ResourceNotFoundException;
import rs.fon.silab.njt.mojezgradespringboot.exception.UnauthorizedException;
import rs.fon.silab.njt.mojezgradespringboot.model.Racun;
import rs.fon.silab.njt.mojezgradespringboot.model.Status;
import rs.fon.silab.njt.mojezgradespringboot.model.StavkaRacuna;
import rs.fon.silab.njt.mojezgradespringboot.model.User;
import rs.fon.silab.njt.mojezgradespringboot.model.VlasnikPosebnogDela;
import rs.fon.silab.njt.mojezgradespringboot.repository.RacunRepository;
import rs.fon.silab.njt.mojezgradespringboot.repository.StavkaRacunaRepository;

@Service
@Transactional
public class RacunService {

    @Autowired
    private RacunRepository repo;
    @Autowired
    private StavkaRacunaRepository stavkeRacnaRepo;
    @Autowired
    private RegistrationService userService;

    private final SimpleDateFormat df = new SimpleDateFormat("MMMMM-yyyy");

    private final String uplatnica = "<style>.row {\n"
            + "  display: flex;\n"
            + "}\n"
            + "\n"
            + ".column {\n"
            + "  flex: 50%;\n"
            + "}</style><div id=\"nalog_za_uplatu\" class =\"row\" style=\"border: black 1pt solid; margin:1em; padding:1em; width: 80%;\">\n"
            + "            <div style=\"text-align: right;\">\n"
            + "                <h3>NALOG ZA UPLATU</h3>\n"
            + "            </div>\n"
            + "\n"
            + "            <div style=\"display: flex;\">\n"
            + "                <div id=\"table_left\" class =\"column\">\n"
            + "                    <label for=\"uplatilac\">Uplatilac:</label><br>\n"
            + "                    <textarea id=\"uplatilac\" name=\"uplatilac\" rows=\"3\" cols=\"72\" wrap=\"hard\" maxlength=\"110\"\n"
            + "                              autofocus=\"\">%vlasnik_ime_prezime%\n"
            + "                        %ulica_broj_mesto%</textarea><br>\n"
            + "\n"
            + "                    <label for=\"svrha\">Svrha uplate:</label><br>\n"
            + "                    <textarea id=\"svrha\" name=\"svrha\" rows=\"3\" cols=\"72\" wrap=\"hard\" maxlength=\"110\">Odrzavanje zgrade - \n"
            + "racun za %mesec%</textarea><br>\n"
            + "\n"
            + "                    <label for=\"primalac\">Primalac:</label><br>\n"
            + "                    <textarea cols=\"72\" rows=\"3\" name=\"primalac\" id=\"primalac\" wrap=\"hard\" maxlength=\"110\">Stambena zajednica \n"
            + "%ulica_i_broj%</textarea>\n"
            + "                </div>\n"
            + "\n"
            + "                <div id=\"table_right\" class =\"column\">\n"
            + "                    <div style=\"display:flex\">\n"
            + "                        <div>\n"
            + "                            <label for=\"sifra\">Šifra plaćanja:</label><br>\n"
            + "                            <input id=\"sifra\" class=\"sifra\" name=\"sifra\" value=\"189\">\n"
            + "                        </div>\n"
            + "                        <div>\n"
            + "                            <label for=\"valuta\">Valuta:</label><br>\n"
            + "                            <input class=\"valuta\" type=\"text\" name=\"valuta\" maxlength=\"3\" value=\"RSD\">\n"
            + "                        </div>\n"
            + "                        <div>\n"
            + "                            <label for=\"iznos\">Iznos:</label><br>\n"
            + "                            <input id=\"iznos\" type=\"number\" name=\"iznos\"\n"
            + "                                   value=\"0.00\">\n"
            + "                        </div>\n"
            + "                    </div>\n"
            + "\n"
            + "                    <br>\n"
            + "                    <label for=\"racun\">Račun:</label><br>\n"
            + "                    <input type=\"text\" name=\"racun\" maxlength=\"20\" style=\"width: 100%;\"\n"
            + "                           value=\"%tekuciRacun%\"\n"
            + "                           placeholder=\"123-1234567890123-12\">\n"
            + "                    <br><br><br>\n"
            + "                    <label for=\"model-i-poziv\">Model i poziv na broj:</label>\n"
            + "                    <div id=\"model-i-poziv\">\n"
            + "                        <input class=\"model\" type=\"text\" name=\"model\" maxlength=\"10\" style=\"margin-right: 0.5em;\">\n"
            + "                        <input type=\"text\" name=\"poziv\" maxlength=\"20\" value=\"%poziv_na_broj%\">\n"
            + "                    </div>\n"
            + "                </div>\n"
            + "            </div>\n"
            + "            <div id=\"table-footer\" style=\"display: flex;  margin-top: 2em;\">\n"
            + "                <div style=\"margin-right: 10em;\">\n"
            + "                    ______________________________________<br>\n"
            + "                    <label>Pečat i potpis uplatioca</label>\n"
            + "                </div>\n"
            + "                <div style=\"margin-right: 10em;\">\n"
            + "                    ______________________________________<br>\n"
            + "                    <label>mesto i datum prijema</label>\n"
            + "                </div>\n"
            + "                <div>\n"
            + "                    ______________________________________<br>\n"
            + "                    <label>datum valute</label>\n"
            + "                </div></div></div>";

    public Racun save(Racun r, List<StavkaRacuna> stavke) {
        Racun saved = repo.save(r);
        for (StavkaRacuna stavka : stavke) {
            stavka.setRacun(saved);
        }
        stavkeRacnaRepo.saveAll(stavke);
        return saved;
    }

    public Racun save(Racun r) {
        return repo.save(r);
    }

    public Racun find(Long id) {
        Optional<Racun> optRacun = repo.findById(id);
        if (!optRacun.isPresent()) {
            return null;
        }
        return optRacun.get();
    }

    public List<Racun> findAll(Long userId, String loginToken) throws UnauthorizedException {
        User user = userService.isLoggedIn(userId, loginToken);
        if (user == null) {
            throw new UnauthorizedException("Korinik nije ulogovan.");
        }
        return repo.findAllByUpravnik(user);
    }

    public List<Racun> findByStatus(Long userId, String loginToken, Status statusEnum) throws UnauthorizedException {
        User user = userService.isLoggedIn(userId, loginToken);
        if (user == null) {
            throw new UnauthorizedException("Korinik nije ulogovan.");
        }
        return repo.findByStatusAndUpravnik(statusEnum, user);
    }

    public List<Racun> findByVlasnik(Long userId, String loginToken, VlasnikPosebnogDela vlasnik) throws UnauthorizedException {
        User user = userService.isLoggedIn(userId, loginToken);
        if (user == null) {
            throw new UnauthorizedException("Korinik nije ulogovan.");
        }
        return repo.findByVlasnikPosebnogDelaAndUpravnik(vlasnik, user);
    }

    public void deleteRacun(Racun racun) {
        repo.delete(racun);
    }

    public List<StavkaRacuna> getStavkeRacuna(Racun racun) {
        Optional<Racun> optRacun = repo.findById(racun.getRacunId());
        if (!optRacun.isPresent()) {
            return null;
        }
        Racun r = optRacun.get();
        return stavkeRacnaRepo.findAllByRacun(r);
    }

    public Racun update(Racun r, List<StavkaRacuna> stavke) {
        Racun saved = repo.save(r);
        for (StavkaRacuna stavka : stavke) {
            stavka.setRacun(saved);
        }
        stavkeRacnaRepo.saveAll(stavke);

        List<StavkaRacuna> stareStavke = stavkeRacnaRepo.findAllByRacun(r);

        if (stareStavke.size() > stavke.size()) {
            int razlika = stareStavke.size() - stavke.size();
            List<StavkaRacuna> zaBrisanje = stareStavke.subList(stareStavke.size() - razlika, stareStavke.size());
            for (StavkaRacuna s : zaBrisanje) {
                stavkeRacnaRepo.delete(s);
            }
        }
        return saved;
    }

    public Racun sendRacunViaEmail(Long racunId, String emailPassword) throws AddressException, MessagingException, IOException, ResourceNotFoundException, DocumentException {
        Racun r = find(racunId);

        if (r == null) {
            throw new ResourceNotFoundException("Ne postoji racun sa id-jem:: " + racunId);
        }
        List<StavkaRacuna> stavke = stavkeRacnaRepo.findAllByRacun(r);

        String tekstMejla
                = "Racun za " + df.format(r.getDatumIzdavanja()) + "<br>"
                + "Upravnik/ca: " + r.getUpravnik().getFirstName() + " " + r.getUpravnik().getLastName() + "<br>"
                + "Vlasnik/ca: " + r.getVlasnikPosebnogDela().getIme() + " " + r.getVlasnikPosebnogDela().getPrezime() + "<br><br><br>"
                + "<table><thead><th>Redni broj</th> <th>Usluga</th> <th>Cena</th></thead><tbody>";

        for (StavkaRacuna s : stavke) {
            tekstMejla += "<tr><td>" + s.getRedniBroj() + ".</td><td>" + s.getUsluga().getNaziv() + "</td><td>" + s.getCena() * s.getKolicina() + "</td></tr>";
        }

        tekstMejla += "</tbody></table><br>Ukupno: " + r.getUkupnaVrednost() + "<br>";

        String uplatnica_popunjena = uplatnica;

        uplatnica_popunjena = uplatnica_popunjena.replace("%ulica_broj_mesto%",
                "" + r.getVlasnikPosebnogDela().getStambenaZajednica().getUlica()
                + " " + r.getVlasnikPosebnogDela().getStambenaZajednica().getBroj()
                + " " + r.getVlasnikPosebnogDela().getStambenaZajednica().getMesto().getNaziv());

        uplatnica_popunjena = uplatnica_popunjena.replace("%mesec%", new SimpleDateFormat("MMMMM").format(r.getDatumIzdavanja()));

        uplatnica_popunjena = uplatnica_popunjena.replace("0.00", String.valueOf(r.getUkupnaVrednost()));

        uplatnica_popunjena = uplatnica_popunjena.replace("%tekuciRacun%", r.getVlasnikPosebnogDela().getStambenaZajednica().getTekuciRacun());

        uplatnica_popunjena = uplatnica_popunjena.replace("%poziv_na_broj%", r.getVlasnikPosebnogDela().getBrojPosebnogDela());

        uplatnica_popunjena = uplatnica_popunjena.replace("%vlasnik_ime_prezime%", r.getVlasnikPosebnogDela().getIme() + " " + r.getVlasnikPosebnogDela().getPrezime());

        uplatnica_popunjena = uplatnica_popunjena.replace("%ulica_i_broj%", r.getVlasnikPosebnogDela().getStambenaZajednica().getUlica() + " "
                + r.getVlasnikPosebnogDela().getStambenaZajednica().getBroj());

        System.out.println(uplatnica_popunjena);

        HtmlConverter.convertToPdf(uplatnica_popunjena, new FileOutputStream("generated_documents/upaltnica_" + r.getDatumIzdavanja() + ".pdf"));

        String mejlVlasnika = r.getVlasnikPosebnogDela().getKontaktVlasnika();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(r.getUpravnik().getEmail(), emailPassword);
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(r.getUpravnik().getEmail(), false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mejlVlasnika));
        msg.setSubject("Racun " + df.format(r.getDatumIzdavanja()));

        msg.setContent(tekstMejla, "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(tekstMejla, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        attachPart.attachFile(new File("generated_documents/upaltnica_" + r.getDatumIzdavanja() + ".pdf"));
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
        return r;
    }

}
