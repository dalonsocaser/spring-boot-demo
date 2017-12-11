package es.caser.spring.springbootdemo.exception;

public class UserNotFoundException extends BaseException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4684402586952828170L;
	private static final String USER_S_NOT_FOUND = "User %s not found";

	public UserNotFoundException(String userId) {
		super("-1004", String.format(USER_S_NOT_FOUND, userId));
		
	}

	

}
