package rs.fon.silab.njt.mojezgradespringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.silab.njt.mojezgradespringboot.model.User;

/**
 *
 * @author Sara
 */
public interface UserRepository extends JpaRepository<User, Long>{

    public User findByEmail(String email);
    
}
