package backend.exceptions;

/**
 * Created by kdziegie on 2016-04-21.
 */
public class OutOfBoundariesException extends Exception {
    public OutOfBoundariesException(){
        super("You try to access element behind boundaries of the space");
    }
}
