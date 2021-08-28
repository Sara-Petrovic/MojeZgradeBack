package rs.fon.silab.njt.mojezgradespringboot.service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.DocumentException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
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
import rs.fon.silab.njt.mojezgradespringboot.dto.EmailRacun;
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

    private final SimpleDateFormat df = new SimpleDateFormat("dd-MMMMM-yyyy");
 
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
        return repo.findAllByVlasnikPosebnogDela_StambenaZajednica_Upravnik(user);
    }

    public List<Racun> findByStatus(Long userId, String loginToken, Status statusEnum) throws UnauthorizedException {
        User user = userService.isLoggedIn(userId, loginToken);
        if (user == null) {
            throw new UnauthorizedException("Korinik nije ulogovan.");
        }
        return repo.findByStatusAndVlasnikPosebnogDela_StambenaZajednica_Upravnik(statusEnum, user);
    }

    public List<Racun> findByVlasnik(Long userId, String loginToken, VlasnikPosebnogDela vlasnik) throws UnauthorizedException {
        User user = userService.isLoggedIn(userId, loginToken);
        if (user == null) {
            throw new UnauthorizedException("Korinik nije ulogovan.");
        }
        return repo.findByVlasnikPosebnogDelaAndVlasnikPosebnogDela_StambenaZajednica_Upravnik(vlasnik, user);
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

    //Racun r, String emailPassword
    
    public Racun sendRacunViaEmail(Long racunId, EmailRacun emailRacun) throws AddressException, MessagingException, IOException, ResourceNotFoundException, DocumentException {
        Racun r = emailRacun.getRacun();
        String emailPassword = emailRacun.getEmailPassword();
        
        
        List<StavkaRacuna> stavke = stavkeRacnaRepo.findAllByRacun(r);

        String tekstMejla
                = "Racun za " + df.format(r.getDatumIzdavanja()) + "<br>"
                + "Upravnik/ca: " + r.getVlasnikPosebnogDela().getStambenaZajednica().getUpravnik().getFirstName() + " " + r.getVlasnikPosebnogDela().getStambenaZajednica().getUpravnik().getLastName() + "<br>"
                + "Vlasnik/ca: " + r.getVlasnikPosebnogDela().getIme() + " " + r.getVlasnikPosebnogDela().getPrezime() + "<br><br><br>"
                + "<table><thead><th>Redni broj</th> <th>Usluga</th> <th>Cena</th></thead><tbody>";

        for (StavkaRacuna s : stavke) {
            tekstMejla += "<tr><td>" + s.getRedniBroj() + ".</td><td>" + s.getUsluga().getNaziv() + "</td><td>" + s.getCena() * s.getKolicina() + "</td></tr>";
        }

        tekstMejla += "</tbody></table><br>Ukupno: " + r.getUkupnaVrednost() + "<br>";
        
        String uplatnica = "";
        String uplatnicaFilePath = "src\\main\\resources\\uplatnica.html";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(uplatnicaFilePath))) {
            String line = reader.readLine();
            while(line!=null){
                uplatnica += line;
                line = reader.readLine();
            }
        }
        
        //platilac
        uplatnica = uplatnica.replace("%vlasnik_ime_prezime%", r.getVlasnikPosebnogDela().getIme() + " " + r.getVlasnikPosebnogDela().getPrezime());

        uplatnica = uplatnica.replace("%ulica_broj_mesto%",
                "" + r.getVlasnikPosebnogDela().getStambenaZajednica().getUlica()
                + " " + r.getVlasnikPosebnogDela().getStambenaZajednica().getBroj()
                + " " + r.getVlasnikPosebnogDela().getStambenaZajednica().getMesto().getNaziv());

        //svrha uplate
        uplatnica = uplatnica.replace("%mesec%", new SimpleDateFormat("MMMMM").format(r.getDatumIzdavanja()));

        //prvi red (sifra placanja, valuta, iznos)
        uplatnica = uplatnica.replace("%sifra-placanja%", emailRacun.getSifraPlacanja());
        uplatnica = uplatnica.replace("%valuta%", emailRacun.getValuta());
        uplatnica = uplatnica.replace("0.00", emailRacun.getIznos());

        //drugi red - tekuci racun
        uplatnica = uplatnica.replace("%tekuciRacun%", emailRacun.getTekuciRacun());

        //treci red - model i poziv na broj
        uplatnica = uplatnica.replace("%model%", emailRacun.getModel());
        uplatnica = uplatnica.replace("%poziv_na_broj%", emailRacun.getPozivNaBroj());

        
        uplatnica = uplatnica.replace("%ulica_i_broj%", r.getVlasnikPosebnogDela().getStambenaZajednica().getUlica() + " "
                + r.getVlasnikPosebnogDela().getStambenaZajednica().getBroj());
        
        ConverterProperties cp = new ConverterProperties();
        cp.setCharset("UTF-8");
        
        String fileName =  "generated_documents/uplatnice/" + 
                        r.getVlasnikPosebnogDela().getIme() + "_" + r.getVlasnikPosebnogDela().getPrezime() + "_" + r.getVlasnikPosebnogDela().getBrojPosebnogDela() + 
                                "/" + df.format(r.getDatumIzdavanja()) + ".pdf";

        HtmlConverter.convertToPdf(uplatnica, new FileOutputStream(new File(fileName)), cp);

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
                return new PasswordAuthentication(r.getVlasnikPosebnogDela().getStambenaZajednica().getUpravnik().getEmail(), emailPassword);
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(r.getVlasnikPosebnogDela().getStambenaZajednica().getUpravnik().getEmail(), false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mejlVlasnika));
        msg.setSubject("Racun " + df.format(r.getDatumIzdavanja()));

        msg.setContent(tekstMejla, "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(tekstMejla, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        attachPart.attachFile(new File(fileName));
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
        return r;
    }

}
