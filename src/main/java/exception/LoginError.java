package exception;

public class LoginError extends Exception {

    public LoginError(String error) {
        super(error);
    }
}
