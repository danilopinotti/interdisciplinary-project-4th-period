package aux.exceptions;

/**
 * Created by danilopinotti on 30/11/15.
 */
public class LoginException extends RuntimeException {
    public LoginException(String message){
        super(message);
    }
}
