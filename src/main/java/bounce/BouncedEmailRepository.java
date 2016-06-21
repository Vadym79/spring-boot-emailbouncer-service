package bounce;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BouncedEmailRepository extends CrudRepository<BouncedEmail, String> {

    //BouncedEmail findByEmail (final String email);
    
    @Modifying
    @Transactional
    @Query(value ="UPDATE bounced_email SET number_of_bounces = number_of_bounces+1 WHERE email = :email", nativeQuery = true)
    int incrementNumberOfBouncedEmails(@Param("email")String email);
}
