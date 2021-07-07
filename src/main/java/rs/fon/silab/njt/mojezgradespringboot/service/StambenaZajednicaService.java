package rs.fon.silab.njt.mojezgradespringboot.service;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.fon.silab.njt.mojezgradespringboot.exception.UnauthorizedException;
import rs.fon.silab.njt.mojezgradespringboot.model.StambenaZajednica;
import rs.fon.silab.njt.mojezgradespringboot.model.User;
import rs.fon.silab.njt.mojezgradespringboot.repository.StambenaZajednicaRepository;

@Service
@Transactional
public class StambenaZajednicaService {

    @Autowired
    private StambenaZajednicaRepository repo;
    @Autowired
    private RegistrationService userService;

    public StambenaZajednica save(StambenaZajednica sz) {
        return repo.save(sz);
    }

    public StambenaZajednica find(Long id) {
        Optional<StambenaZajednica> optionalResponse = repo.findById(id);
        if (optionalResponse.isPresent()) {
            return optionalResponse.get();
        }
        return null;
    }

    public void delete(StambenaZajednica sz) {
        repo.delete(sz);
    }

    public List<StambenaZajednica> getAll(Long userID, String loginToken) throws UnauthorizedException {
        User user = userService.isLoggedIn(userID, loginToken);
        if (user == null) {
            throw new UnauthorizedException("Korinik nije ulogovan.");
        }
        return repo.findAllByUpravnik(user);
    }

    public List<StambenaZajednica> findByPib(Long userId, String loginToken, String pib) throws UnauthorizedException {
        User user = userService.isLoggedIn(userId, loginToken);
        if (user == null) {
            throw new UnauthorizedException("Korinik nije ulogovan.");
        }
        return repo.findByPibContainsAndUpravnik(pib, user);
    }

    public List<StambenaZajednica> findByMaticniBroj(Long userId, String loginToken, String maticni_broj) throws UnauthorizedException {
        User user = userService.isLoggedIn(userId, loginToken);
        if (user == null) {
            throw new UnauthorizedException("Korinik nije ulogovan.");
        }
        return repo.findByMaticniBrojContainsAndUpravnik(maticni_broj, user);
    }

    public List<StambenaZajednica> findByUlicaIBroj(Long userId, String loginToken, String ulica, String broj) throws UnauthorizedException {
        User user = userService.isLoggedIn(userId, loginToken);
        if (user == null) {
            throw new UnauthorizedException("Korinik nije ulogovan.");
        }
        return repo.findByUlicaContainsAndBrojAndUpravnik(ulica, broj, user);
    }

    public List<StambenaZajednica> findByUlica(Long userId, String loginToken, String ulica) throws UnauthorizedException {
        User user = userService.isLoggedIn(userId, loginToken);
        if (user == null) {
            throw new UnauthorizedException("Korinik nije ulogovan.");
        }
        return repo.findByUlicaContainsAndUpravnik(ulica, user);
    }

    public List<StambenaZajednica> getAll() {
        return repo.findAll();
    }

}
