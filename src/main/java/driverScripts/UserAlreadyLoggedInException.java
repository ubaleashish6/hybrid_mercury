package driverScripts;

@SuppressWarnings("serial")
public class UserAlreadyLoggedInException extends RuntimeException {
	public UserAlreadyLoggedInException(String message) {
		super(message);
	}
}