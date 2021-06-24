package rs.fon.silab.njt.mojezgradespringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.fon.silab.njt.mojezgradespringboot.model.User;
import rs.fon.silab.njt.mojezgradespringboot.repository.UserRepository;

/**
 *
 * @author Sara
 */

@Service
@Transactional
public class RegistrationService {
    @Autowired
    private UserRepository repo;
    
    public User saveUser(User user){
        return repo.save(user); //cuva u bazu
    }
    
    public User fetchUserByEmail(String email){
        return repo.findByEmail(email);
    }
}
