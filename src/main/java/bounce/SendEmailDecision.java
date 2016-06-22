package bounce;

public class SendEmailDecision 
{
	
	private final BouncedEmail bouncedEmail;
	private final int numberOfPreviouslyAllowedBouncedEmails;
	private final boolean shouldSendEmail;
	private String additionalMessage ="";
	
	public SendEmailDecision(final BouncedEmail bouncedEmail, final int numberOfPreviouslyAllowedBouncedEmails, final boolean shouldSendEmail) 
	{
		this.bouncedEmail = bouncedEmail;
		this.numberOfPreviouslyAllowedBouncedEmails = numberOfPreviouslyAllowedBouncedEmails;
		this.shouldSendEmail = shouldSendEmail;
	}

	public BouncedEmail getBouncedEmail() {
		return this.bouncedEmail;
	}

	public int getNumberOfPreviouslyAllowedBouncedEmails() {
		return this.numberOfPreviouslyAllowedBouncedEmails;
	}

	public boolean shouldSendEmail() {
		return this.shouldSendEmail;
	}
	
	public void withAdditionalMessage (final String additionalMessage)
	{
		this.additionalMessage=additionalMessage;
	}

	@Override
	public String toString() {
		return "SendEmailDecision [bouncedEmail=" + bouncedEmail + ", numberOfPreviouslyAllowedBouncedEmails="
				+ numberOfPreviouslyAllowedBouncedEmails + ", shouldSendEmail=" + shouldSendEmail
				+ ", additionalMessage=" + additionalMessage + "]";
	}

	
}
