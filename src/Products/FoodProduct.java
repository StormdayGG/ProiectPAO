package Products;

import Companies.Company;

public class FoodProduct extends Product{
    protected String type;
    protected String container;
    protected float weight;
    protected String[] allergicFactors;

    public FoodProduct() {
        this.type = null;
        this.container = null;
        this.weight = 0;
        this.allergicFactors = null;
    }

    public FoodProduct(String type, String container, float weight, String[] allergicFactors) {
        this.type = type;
        this.container = container;
        this.weight = weight;
        this.allergicFactors = allergicFactors;
    }

    public FoodProduct(int id, String name, float price, Company producer, String type, String container, float weight, String[] allergicFactors) {
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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String[] getAllergicFactors() {
        return allergicFactors;
    }

    public void setAllergicFactors(String[] allergicFactors) {
        this.allergicFactors = allergicFactors;
    }
}
