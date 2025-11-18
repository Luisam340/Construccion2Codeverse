package app.code.verse.domain.model;

public class ProcedureInventory {
    private String idProcedure;
    private String name;
    private double cost;

    public ProcedureInventory() {
    }

    public String getIdProcedure() {
        return idProcedure;
    }

    public void setIdProcedure(String idProcedure) {
        this.idProcedure = idProcedure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}