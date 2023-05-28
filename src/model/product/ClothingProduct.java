package model.product;

import model.company.Company;

public class ClothingProduct extends Product{
    protected String color;
    protected String material;
    protected String type;
    protected String size;
    protected String style;

    public ClothingProduct()
    {
        this.color = null;
        this.material = null;
        this.type = null;
        this.size = null;
        this.style = null;
    }

    public ClothingProduct(String color, String material, String type, String size, String style) {
        this.color = color;
        this.material = material;
        this.type = type;
        this.size = size;
        this.style = style;
    }

    public ClothingProduct(int id, String name, double price, int producer, String color, String material, String type, String size, String style) {
        super(id, name, price, producer);
        this.color = color;
        this.material = material;
        this.type = type;
        this.size = size;
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
