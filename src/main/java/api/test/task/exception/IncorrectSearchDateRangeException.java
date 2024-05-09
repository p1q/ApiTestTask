package api.test.task.exception;

public class IncorrectSearchDateRangeException extends RuntimeException {
    public IncorrectSearchDateRangeException() {
        super("The 'from' search date cannot be later than the 'to' search date.");
    }
}
