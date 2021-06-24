package rs.fon.silab.njt.mojezgradespringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.fon.silab.njt.mojezgradespringboot.model.User;
import rs.fon.silab.njt.mojezgradespringboot.repository.UserRepository;

@Service
@Transactional
public class RegistrationService {
    @Autowired
    private UserRepository repo;
    
    public User saveUser(User user){
        return repo.save(user);
    }
    
    public User fetchUserByEmail(String email){
        return repo.findByEmail(email);
    }
    
    public User fetchUserByEmailAndPassword(String email, String password){
        return repo.findByEmailAndPassword(email,password);
    }
}
