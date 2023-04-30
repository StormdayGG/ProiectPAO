package Products;

import Companies.Company;

public class ElectronicProduct extends Product{
    protected String connection;
    protected String color;
    protected int warranty;
    protected int weight;

    public ElectronicProduct()
    {
        connection = null;
        color = null;
        warranty = 0;
        weight = -1;
    }
    public ElectronicProduct(String connection, String color, int warranty, int weight) {
        this.connection = connection;
        this.color = color;
        this.warranty = warranty;
        this.weight = weight;
    }

    public ElectronicProduct(int id, String name, float price, Company producer, String connection, String color, int warranty, int weight) {
        super(id, name, price, producer);
        this.connection = connection;
        this.color = color;
        this.warranty = warranty;
        this.weight = weight;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
