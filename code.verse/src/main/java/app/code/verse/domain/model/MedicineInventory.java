package app.code.verse.domain.model;

public class MedicineInventory {
    private String idMedicine;
    private String name;
    private int stock;
    private double price;

    public MedicineInventory() {
    }

    public String getIdMedicine() {
        return idMedicine;
    }

    public void setIdMedicine(String idMedicine) {
        this.idMedicine = idMedicine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}