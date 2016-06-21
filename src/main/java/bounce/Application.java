package bounce;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;

@EnableCircuitBreaker
@SpringBootApplication
public class Application {

	//private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired BouncedEmailService bouncedEmailService;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner demo(BouncedEmailRepository repository) {
		return (args) -> {
			// save a couple of customers
			//repository.save(BouncedEmail.valueOfFirstBouncedEmail("v.kazulkin@gmail.com"));
			/*

			// fetch all customers
			
            bouncedEmail = repository.findOne("v.kazulkin@gmail.com") ;
			
            log.info("bounced email pk "+bouncedEmail);
            
            repository.incrementNumberOfBouncedEmails("v.kazulkin@gmail.com");
            
            bouncedEmail = repository.findOne("v.kazulkin@gmail.com") ;
			
            log.info("bounced email pk after update "+bouncedEmail);
            */
			
			String email ="v.kazulkin@gmail.com";
			this.bouncedEmailService.handleBouncedEmail(email);
			
			System.out.println("should send email "+ email+ " "+ this.bouncedEmailService.shouldSendEmail(email));
			
			System.out.println("should send email "+ email+ " "+ this.bouncedEmailService.shouldSendEmail("bla@bla.com"));

			email ="v.kazulkin@iplabs.de";
			
			System.out.println("should send email "+ email+ " "+ this.bouncedEmailService.shouldSendEmail(email));
			
		
		};
	}

}
