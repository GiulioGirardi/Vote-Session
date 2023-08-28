package challenge.interview.exceptions;

public class SubjectNotFoundException extends Exception{

    public SubjectNotFoundException(){
        super("Subject could not be found");
    }
}
