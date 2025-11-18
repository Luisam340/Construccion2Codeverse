package app.code.verse.adapter.in.validators;

import org.springframework.stereotype.Component;

@Component
public class VitalSignValidator extends SimpleValidator {

    public String bloodPressureValidator(String value) throws Exception {
        return stringValidator(value, "presión arterial");
    }

    public Double temperatureValidator(Double temperature) {
        if (temperature == null) {
            throw new IllegalArgumentException("La temperatura no puede ser nula");
        }
        if (temperature < 30.0 || temperature > 45.0) {
            throw new IllegalArgumentException("La temperatura debe estar entre 30 y 45 grados");
        }
        return temperature;
    }

    public Integer pulseValidator(Integer pulse) {
        if (pulse == null) {
            throw new IllegalArgumentException("El pulso no puede ser nulo");
        }
        if (pulse < 30 || pulse > 250) {
            throw new IllegalArgumentException("El pulso debe estar entre 30 y 250 pulsaciones por minuto");
        }
        return pulse;
    }

    public Double oxygenLevelValidator(Double oxygenLevel) {
        if (oxygenLevel == null) {
            throw new IllegalArgumentException("El nivel de oxígeno no puede ser nulo");
        }
        if (oxygenLevel < 0.0 || oxygenLevel > 100.0) {
            throw new IllegalArgumentException("El nivel de oxígeno debe estar entre 0 y 100%");
        }
        return oxygenLevel;
    }
}
