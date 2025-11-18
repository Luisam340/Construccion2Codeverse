package app.code.verse.application.exceptions;

public class InsufficientInventoryException extends RuntimeException {
    public InsufficientInventoryException(String message) {
        super(message);
    }

    public InsufficientInventoryException(String itemName, int requested, int available) {
        super(String.format("Inventario insuficiente para %s. Solicitado: %d, Disponible: %d",
            itemName, requested, available));
    }
}
