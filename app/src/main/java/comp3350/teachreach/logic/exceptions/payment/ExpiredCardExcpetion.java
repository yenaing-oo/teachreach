package comp3350.teachreach.logic.exceptions.payment;

public class ExpiredCardExcpetion extends PaymentException {
    public ExpiredCardExcpetion(String message) {
        super(message);
    }

    public ExpiredCardExcpetion(String message, Throwable cause) {
        super(message, cause);
    }
}
