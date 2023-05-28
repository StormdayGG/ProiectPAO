package model.product;

public class ProductEntry {
    protected int productId;
    protected int quantity;

    public ProductEntry() {
        productId = -1;
        quantity = 0;
    }

    public ProductEntry(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
