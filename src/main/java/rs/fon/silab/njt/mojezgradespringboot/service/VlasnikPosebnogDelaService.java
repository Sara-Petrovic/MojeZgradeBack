package rs.fon.silab.njt.mojezgradespringboot.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.fon.silab.njt.mojezgradespringboot.exception.UnauthorizedException;
import rs.fon.silab.njt.mojezgradespringboot.model.User;
import rs.fon.silab.njt.mojezgradespringboot.model.VlasnikPosebnogDela;
import rs.fon.silab.njt.mojezgradespringboot.repository.VlasnikPosebnogDelaRepository;

@Service
@Transactional
public class VlasnikPosebnogDelaService {

    @Autowired
    private VlasnikPosebnogDelaRepository repo;
    @Autowired
    private RegistrationService userService;

    public VlasnikPosebnogDela save(VlasnikPosebnogDela v) {
        return repo.save(v);
    }

    public Optional<VlasnikPosebnogDela> findById(Long vlasnikId) {
        return repo.findById(vlasnikId);
    }

    public void delete(VlasnikPosebnogDela vlasnik) {
        repo.delete(vlasnik);
    }

    public List<VlasnikPosebnogDela> findAll(Long userId, String loginToken) throws UnauthorizedException {
        User user = userService.isLoggedIn(userId, loginToken);
        if (user == null) {
            throw new UnauthorizedException("Korinik nije ulogovan.");
        }
        return repo.findByStambenaZajednica_Upravnik_UserId(user.getUserId());
    }

    public List<VlasnikPosebnogDela> findByPrezime(String prezimeVlasnika, Long userId , String loginToken) throws UnauthorizedException {
        User user = userService.isLoggedIn(userId, loginToken);
        if (user == null) {
            throw new UnauthorizedException("Korinik nije ulogovan.");
        }
        return repo.findByPrezimeContainingAndStambenaZajednica_Upravnik_UserId(prezimeVlasnika, user.getUserId());
    }

    public List<VlasnikPosebnogDela> findByStambenaZajednica(Long szId, Long userId , String loginToken) throws UnauthorizedException {
        User user = userService.isLoggedIn(userId, loginToken);
        if (user == null) {
            throw new UnauthorizedException("Korinik nije ulogovan.");
        }
        return repo.findByStambenaZajednica_StambenaZajednicaId(szId);
    }

}
