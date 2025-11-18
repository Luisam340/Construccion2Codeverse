package app.code.verse.adapter.in.validators;

import org.springframework.stereotype.Component;

@Component
public class InventoryValidator extends SimpleValidator {

    public String idValidator(String value, String fieldName) throws Exception {
        return stringValidator(value, fieldName);
    }

    public String nameValidator(String value) throws Exception {
        return stringValidator(value, "nombre");
    }

    public double priceValidator(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        return price;
    }

    public double costValidator(double cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("El costo no puede ser negativo");
        }
        return cost;
    }

    public int stockValidator(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        return stock;
    }
}
