package rs.fon.silab.njt.mojezgradespringboot.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.fon.silab.njt.mojezgradespringboot.exception.UnauthorizedException;
import rs.fon.silab.njt.mojezgradespringboot.model.SednicaSkupstine;
import rs.fon.silab.njt.mojezgradespringboot.model.User;
import rs.fon.silab.njt.mojezgradespringboot.model.VlasnikPosebnogDela;
import rs.fon.silab.njt.mojezgradespringboot.repository.SednicaSkupstineRepository;

@Service
@Transactional
public class SednicaSkupstineService {

    @Autowired
    private SednicaSkupstineRepository repo;
    @Autowired
    private RegistrationService userService;

    public SednicaSkupstine save(SednicaSkupstine sednica) {
        return repo.save(sednica);
    }

    public List<SednicaSkupstine> findAll(Long userId, String loginToken) throws UnauthorizedException {
        User user = userService.isLoggedIn(userId, loginToken);
        if (user == null) {
            throw new UnauthorizedException("Korinik nije ulogovan.");
        }
        return repo.findByStambenaZajednica_Upravnik_UserId(user.getUserId());
    }

    public Optional<SednicaSkupstine> findById(Long sednicaId) {
        return repo.findById(sednicaId);
    }

    public List<SednicaSkupstine> findByUlica(String ulica, Long userId, String loginToken) throws UnauthorizedException {
        User user = userService.isLoggedIn(userId, loginToken);
        if (user == null) {
            throw new UnauthorizedException("Korinik nije ulogovan.");
        }
        return repo.findByStambenaZajednica_UlicaContainingAndStambenaZajednica_Upravnik_UserId(ulica, user.getUserId());
    }

    public void deleteVlasnikFromSednice(VlasnikPosebnogDela vlasnik) {
        // repo.
    }

}
