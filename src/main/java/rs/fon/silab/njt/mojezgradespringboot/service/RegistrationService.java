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
    
    private final Map<Long, Login> loggedIn = new HashMap<>();
    
    public User saveUser(User user){
        return repo.save(user);
    }
    
    public User fetchUserByEmail(String email){
        return repo.findByEmail(email);
    }
    
    public User fetchUserByEmailAndPassword(String email, String password){
        return repo.findByEmailAndPassword(email,password);
    }

    public Login logUserIn(User userObj) throws Exception {
        if(loggedIn.get(userObj.getUserId()) != null){
            throw new Exception("Ovaj korisnik sa je vec ulogovan: " + userObj.getEmail());
        }
        String token = generateLoginToken();
        Login login = new Login(userObj, token);
        loggedIn.put(userObj.getUserId(), login);
        System.err.println("\n\n\n" + userObj.getUserId() + "\n\n\n"+ token + "\n\n\n");
        return login;
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

    public User isLoggedIn(Long userID, String loginToken) {
        Login login = loggedIn.get(userID);
        if(login == null) {
            return null;
        }
        if(login.getToken().equals(loginToken)){
            return login.getUser();
        }
        return null;
    }
}
