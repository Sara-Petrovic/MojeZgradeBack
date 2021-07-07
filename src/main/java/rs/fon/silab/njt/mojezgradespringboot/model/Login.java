package rs.fon.silab.njt.mojezgradespringboot.model;

import java.sql.Timestamp;
import java.time.Instant;

public class Login {
    
    User user;
    String token;
    Timestamp timestamp;

    public Login() {
    }
    
    public Login(User user, String token) {
        this.user = user;
        this.token = token;
        timestamp = Timestamp.from(Instant.now());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
