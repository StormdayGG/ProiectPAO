package Orders;
import Users.Customer;
import Products.ProductEntry;
public class Order {
    protected int id;
    protected Customer customer;
    protected ProductEntry[] orderedProducts;
    protected int[] dateTime;
    protected float totalCost;
    protected String address;
    protected float discount;

    public Order() {
        id = -1;
        customer = null;
        orderedProducts = null;
        dateTime = null;
        totalCost = -1;
        address = null;
        discount = 0;
    }
    public Order(int id, Customer customer, ProductEntry[] orderedProducts, int[] dateTime, float totalCost, String address) {
        this.id = id;
        this.customer = customer;
        this.orderedProducts = orderedProducts;
        this.dateTime = dateTime;
        this.totalCost = totalCost;
        this.address = address;
        this.discount = 0;
    }
    //To Do: Add Verification for dateTime and discount -------------------

    public Order(int id, Customer customer, ProductEntry[] orderedProducts, int[] dateTime, float totalCost, String address, float discount) {
        this.id = id;
        this.customer = customer;
        this.orderedProducts = orderedProducts;
        this.dateTime = dateTime;
        this.totalCost = totalCost;
        this.address = address;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ProductEntry[] getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(ProductEntry[] orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public int[] getDateTime() {
        return dateTime;
    }

    public void setDateTime(int[] dateTime) {
        this.dateTime = dateTime;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getDiscountedAmount() {
        return discount * totalCost;
    }
}
