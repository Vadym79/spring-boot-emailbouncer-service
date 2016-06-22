package bounce;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BouncedEmail {

    @Id
    private  String email;
    private  int numberOfBounces;

        public  BouncedEmail ()
        {
        	
        }
    
        public BouncedEmail(final String email, final int numberOfBounces) 
        {
        	this.email=email;
        	this.numberOfBounces = numberOfBounces;
        }
        
        public static BouncedEmail valueOfFirstBouncedEmail(final String email) 
        {
        	 return new BouncedEmail(email, 1);
        } 

        public static BouncedEmail valueOfNoBouncedEmail(final String email) 
        {
        	 return new BouncedEmail(email, 0);
        } 

		@Override
		public String toString() {
			return "BouncedEmail [email=" + email + ", numberOfBounces=" + numberOfBounces + "]";
		}

		public String getEmail() {
			return this.email;
		}

		public int getNumberOfBounces() {
			return this.numberOfBounces;
		}

    
}

