package rs.fon.silab.njt.mojezgradespringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rs.fon.silab.njt.mojezgradespringboot.model.User;
import rs.fon.silab.njt.mojezgradespringboot.service.RegistrationService;

@RestController
@CrossOrigin(origins = "*")
public class RegistrationController {

    @Autowired
    private RegistrationService service;

    @PostMapping("/registeruser")
    public User registerUser(@RequestBody User user) throws Exception {
        String tempEmail = user.getEmail();
        if (tempEmail != null && !tempEmail.isEmpty()) {
            User userObj = service.fetchUserByEmail(tempEmail);
            if (userObj != null) {
                throw new Exception("User sa ovim email-om vec postoji.");
            }
        }
        User userObj = null;
        userObj = service.saveUser(user);
        return userObj;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) throws Exception {
        String tempEmail = user.getEmail();
        String tempPassword = user.getPassword();
        User userObj = null;

        if (tempEmail != null && tempPassword != null) {
            userObj = service.fetchUserByEmailAndPassword(tempEmail, tempPassword);
        }
        if (userObj == null) {
            throw new Exception("Neuspesan login. Proverite email i/ili lozinku.");
        }
        userObj = service.logUserIn(userObj);
        return ResponseEntity.ok().body(new User(userObj.getUserId(), userObj.getEmail(), "", userObj.getFirstName(), userObj.getLastName()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody User user) throws Exception {
        if (!service.logUserOut(user)) {
            return new ResponseEntity("Korisnik nije ulogovan ili nije registrovan.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("Korisnik " + user.getEmail() + " je izlogovan.");
    }
}
