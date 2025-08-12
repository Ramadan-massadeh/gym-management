package model;

/**
 * Ramadan masadekh
 * Represents a merchandise item sold at the gym.
 */
public class Merch {
    private int itemId;
    private String name;
    private String type;
    private double price;
    private int quantityInStock;

    public Merch() {
    }

    public Merch(int itemId, String name, String type, double price, int quantityInStock) {
        this.itemId = itemId;
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    // getters and setters
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantityInStock() { return quantityInStock; }
    public void setQuantityInStock(int quantityInStock) { this.quantityInStock = quantityInStock; }

    @Override
    public String toString() {
        return "Merch{id=" + itemId + ", name='" + name + "', price=" + price +
               ", quantity=" + quantityInStock + "}";
    }
}