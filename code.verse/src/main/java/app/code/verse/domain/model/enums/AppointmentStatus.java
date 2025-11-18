package app.code.verse.domain.model.enums;

public enum AppointmentStatus {
    SCHEDULED("Programada"),
    COMPLETED("Completada"),
    CANCELLED("Cancelada");

    private final String status;

    AppointmentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
