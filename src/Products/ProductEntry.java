package Products;

public class ProductEntry {
    protected Product product;
    protected int quantity;

    public ProductEntry() {
        product = null;
        quantity = 0;
    }

    public ProductEntry(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
