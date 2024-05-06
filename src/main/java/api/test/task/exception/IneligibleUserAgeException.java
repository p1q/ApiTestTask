package api.test.task.exception;

public class IneligibleUserAgeException extends RuntimeException {
    public IneligibleUserAgeException() {
        super("User is not eligible to register due to our age restrictions.");
    }
}
