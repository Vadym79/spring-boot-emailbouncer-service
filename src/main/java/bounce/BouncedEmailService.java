package bounce;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service

public class BouncedEmailService {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	@Autowired BouncedEmailRepository bouncedEmailRepository;
	
	private final static int NUMBER_OF_PREVIOUSLY_ALLOWED_BOUNCED_EMAILS=1;
	
	@Transactional
    @HystrixCommand(fallbackMethod = "defaultHandleBouncedEmail")
	public void handleBouncedEmail(final String email)
	{
       final BouncedEmail bouncedEmail=  this.bouncedEmailRepository.findOne(email);
       if(bouncedEmail == null)
       {
    	   this.bouncedEmailRepository.save(BouncedEmail.valueOfFirstBouncedEmail(email));
       }
       else
       {
    	   this.bouncedEmailRepository.incrementNumberOfBouncedEmails(email);
       }
	}
	
	public void defaultHandleBouncedEmail(final String email) {
       log.info("FALLBACK defaultHandleBouncedEmail CALLED ");
    }
	
	@HystrixCommand(fallbackMethod = "defaultShouldSendEmail")
	public boolean shouldSendEmail(final String email)
	{
		final BouncedEmail bouncedEmail=  this.bouncedEmailRepository.findOne(email);
	    if(bouncedEmail == null)
	    {
	    	return true;
	    } 
	    else
	    {
	    	return bouncedEmail.getNumberOfBounces()<= NUMBER_OF_PREVIOUSLY_ALLOWED_BOUNCED_EMAILS;
	    }
	}
	
	public boolean defaultShouldSendEmail(final String email) {
		 log.info("FALLBACK defaultShouledSendEmail CALLED ");
	     return true;
    }
}
