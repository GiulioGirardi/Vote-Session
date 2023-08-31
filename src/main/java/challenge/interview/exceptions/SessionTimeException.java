package challenge.interview.exceptions;

public class SessionTimeException extends Exception {
    public SessionTimeException(){
        super("Session time is incorrect");
    }
}
