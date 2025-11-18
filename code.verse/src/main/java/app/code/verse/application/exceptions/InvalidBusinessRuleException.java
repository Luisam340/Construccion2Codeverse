package app.code.verse.application.exceptions;

public class InvalidBusinessRuleException extends RuntimeException {
    public InvalidBusinessRuleException(String message) {
        super(message);
    }
}
