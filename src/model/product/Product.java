package model.product;
import model.company.Company;
public class Product {
    protected int id;
    protected String name;
    protected double price;
    protected Company producer;

    public Product() {
        id = -1;
        name = null;
        price = -1;
        producer = null;
    }

    public Product(int id, String name, double price, Company producer) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.producer = producer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Company getProducer() {
        return producer;
    }

    public void setProducer(Company producer) {
        this.producer = producer;
    }
}