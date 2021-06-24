package rs.fon.silab.njt.mojezgradespringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.fon.silab.njt.mojezgradespringboot.model.User;
import rs.fon.silab.njt.mojezgradespringboot.service.RegistrationService;

/**
 *
 * @author Sara
 */

@RestController
public class RegistrationController {
    
    //kada se registruje korisnik
    @Autowired
    private RegistrationService service;
    
    @PostMapping("/registeruser")
    public User registerUser(@RequestBody User user) throws Exception{
        String tempEmail = user.getEmail();
        if(tempEmail!=null && !tempEmail.isEmpty()){
            User userObj = service.fetchUserByEmail(tempEmail);
            if(userObj!=null){
                throw new Exception("User sa ovim email-om vec postoji.");
            }
        }
        User userObj = null;
        userObj = service.saveUser(user);
        return userObj;
    }
    
    public void loginUser(@RequestBody User user){
        String tempEmail = user.getEmail();
        String tempPassword = user.getPassword();
        
        if(tempEmail!=null && tempPassword!=null){
            
        }
    }
}
