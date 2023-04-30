package Products;

import Companies.Company;

public class CosmeticProduct extends Product{
    protected String bodyPart;
    protected String applicationMethod;
    protected String color;
    protected String quantity;

    public CosmeticProduct()
    {
        this.bodyPart = null;
        this.applicationMethod = null;
        this.color = null;
        this.quantity = null;
    }
    public CosmeticProduct(String bodyPart, String applicationMethod, String color, String quantity) {
        this.bodyPart = bodyPart;
        this.applicationMethod = applicationMethod;
        this.color = color;
        this.quantity = quantity;
    }

    public CosmeticProduct(int id, String name, float price, Company producer, String bodyPart, String applicationMethod, String color, String quantity) {
        super(id, name, price, producer);
        this.bodyPart = bodyPart;
        this.applicationMethod = applicationMethod;
        this.color = color;
        this.quantity = quantity;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getApplicationMethod() {
        return applicationMethod;
    }

    public void setApplicationMethod(String applicationMethod) {
        this.applicationMethod = applicationMethod;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
