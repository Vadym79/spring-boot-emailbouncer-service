package bounce;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service

public class BouncedEmailService {

	private static final Logger log = LoggerFactory.getLogger(BouncedEmailService.class);
	final static boolean DEFAULT_SHOULD_SEND_EMAIL=false;
	private final static int NUMBER_OF_PREVIOUSLY_ALLOWED_BOUNCED_EMAILS=1;
	@Autowired BouncedEmailRepository bouncedEmailRepository;
	
	
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
	
	@HystrixCommand(fallbackMethod = "defaultGetSendEmailDecision")
	public SendEmailDecision getSendEmailDecision(final String email)
	{
	    final BouncedEmail bouncedEmail=  this.bouncedEmailRepository.findOne(email);
	    if(bouncedEmail == null)
	    {
	    	return new SendEmailDecision(BouncedEmail.valueOfNoBouncedEmail(email), NUMBER_OF_PREVIOUSLY_ALLOWED_BOUNCED_EMAILS, true);
	    } 
	    else
	    {
	    	boolean sendEmail=bouncedEmail.getNumberOfBounces()<= NUMBER_OF_PREVIOUSLY_ALLOWED_BOUNCED_EMAILS;
	    	return new SendEmailDecision(bouncedEmail,NUMBER_OF_PREVIOUSLY_ALLOWED_BOUNCED_EMAILS, sendEmail);
	    }
	}
	
	public SendEmailDecision defaultGetSendEmailDecision(final String email) {
		 log.info("FALLBACK getSendEmailDecision CALLED ");
		 
	     final SendEmailDecision sendEmailDecision =new SendEmailDecision(new BouncedEmail(email,0),NUMBER_OF_PREVIOUSLY_ALLOWED_BOUNCED_EMAILS, DEFAULT_SHOULD_SEND_EMAIL);
	     sendEmailDecision.withAdditionalMessage("fallback method called with should send email : "+DEFAULT_SHOULD_SEND_EMAIL);
	     return sendEmailDecision;
	     
	     
    }
}
