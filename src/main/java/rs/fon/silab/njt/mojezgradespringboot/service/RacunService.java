package rs.fon.silab.njt.mojezgradespringboot.service;

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
import org.springframework.web.multipart.MultipartFile;
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
    
    private SimpleDateFormat df = new SimpleDateFormat("MMMMM-yyyy");

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

    public void sendRacunViaEmail(Long racunId, String emailPassword, MultipartFile uplatnica) throws AddressException, MessagingException, IOException, ResourceNotFoundException {
        Racun r = find(racunId);
        if (r == null) {
            throw new ResourceNotFoundException("Ne postoji racun sa id-jem:: " + racunId);
        }
        List<StavkaRacuna> stavke = stavkeRacnaRepo.findAllByRacun(r);
        String tekstMejla = 
                "Racun za " + df.format(r.getDatumIzdavanja())+ "<br>"
                + "Upravnik: " + r.getUpravnik().getFirstName() + " " + r.getUpravnik().getLastName() + "<br>"
                + "Vlasnik: " + r.getVlasnikPosebnogDela().getIme() + " " + r.getVlasnikPosebnogDela().getPrezime() + "<br><br><br>" +
                "<table><thead><th>Redni broj</th> <th>Usluga</th> <th>Cena</th></thead><tbody>";
        
        
        for(StavkaRacuna s : stavke){
            tekstMejla+= "<tr><td>" + s.getRedniBroj() + ".</td><td>" + s.getUsluga().getNaziv() + "</td><td>" + s.getCena()*s.getKolicina() + "</td></tr>";
        }
        
        tekstMejla+= "</tbody></table><br>Ukupno: " + r.getUkupnaVrednost() + "<br>";
        
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

        attachPart.attachFile(convertMultiPartToFile(uplatnica));

        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }
    
    private File convertMultiPartToFile(MultipartFile file ) throws IOException
    {
        File convFile = new File( file.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }
}
