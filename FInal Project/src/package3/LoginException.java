package package3;

/**
 * Exception that is thrown whenever the user switches between the admin login panel and the student login
 * panel. It causes the constructors of the Admin and Student classes to stop, which lets RegThread switch to
 * constructing the other type of user.
 * @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
 *
 */
public class LoginException extends Exception{

	LoginException(){
		super("Changed login type");
	}
	
}
