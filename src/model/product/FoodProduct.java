package model.product;

import model.company.Company;

public class FoodProduct extends Product{
    protected String type;
    protected String container;
    protected double weight;
    protected String[] allergicFactors;

    public FoodProduct() {
        this.type = null;
        this.container = null;
        this.weight = 0;
        this.allergicFactors = null;
    }

    public FoodProduct(String type, String container, double weight, String[] allergicFactors) {
        this.type = type;
        this.container = container;
        this.weight = weight;
        this.allergicFactors = allergicFactors;
    }

    public FoodProduct(int id, String name, double price, int producer, String type, String container, double weight, String[] allergicFactors) {
        super(id, name, price, producer);
        this.type = type;
        this.container = container;
        this.weight = weight;
        this.allergicFactors = allergicFactors;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String[] getAllergicFactors() {
        return allergicFactors;
    }

    public void setAllergicFactors(String[] allergicFactors) {
        this.allergicFactors = allergicFactors;
    }
}
