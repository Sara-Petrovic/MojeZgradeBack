package rs.fon.silab.njt.mojezgradespringboot.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.fon.silab.njt.mojezgradespringboot.model.Login;
import rs.fon.silab.njt.mojezgradespringboot.model.User;
import rs.fon.silab.njt.mojezgradespringboot.repository.UserRepository;

@Service
@Transactional
public class RegistrationService {
    @Autowired
    private UserRepository repo;
    
    private Map<Long, Login> loggedIn = new HashMap<>();
    
    public User saveUser(User user){
        return repo.save(user);
    }
    
    public User fetchUserByEmail(String email){
        return repo.findByEmail(email);
    }
    
    public User fetchUserByEmailAndPassword(String email, String password){
        return repo.findByEmailAndPassword(email,password);
    }

    public User logUserIn(User userObj) throws Exception {
        if(loggedIn.get(userObj.getUserId()) != null){
            throw new Exception("Ovaj korisnik sa je vec ulogovan: " + userObj.getEmail());
        }
        String token = generateLoginToken();
        loggedIn.put(userObj.getUserId(), new Login(userObj, token));
        System.err.println("\n\n\n" + userObj.getUserId() + "\n\n\n"+ token + "\n\n\n");
        return userObj;
    }

    private String generateLoginToken() {
        return UUID.randomUUID().toString();
    }

    public boolean logUserOut(User user) {
        if(loggedIn.get(user.getUserId()) == null){
            return false;
        }
        Login ret = loggedIn.remove(user.getUserId());
        System.err.println("\n\n" + loggedIn.size() + "\n\n");
        return ret != null;
    }
}
