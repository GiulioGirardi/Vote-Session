package challenge.interview.exceptions;

public class AlreadyVoteException extends Exception {
    public AlreadyVoteException(){
        super("Associated already vote in this session");
    }
}
