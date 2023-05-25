package model.order;
import model.exception.OrderException;
import model.user.Customer;
import model.product.ProductEntry;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class Order {
    protected int id;
    protected Customer customer;
    protected List<ProductEntry> orderedProducts;
    protected String dateTime;
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

    public Order(int id, Customer customer, List<ProductEntry> orderedProducts, String address, float discount) throws OrderException {
        this.id = id;
        this.customer = customer;
        this.orderedProducts = orderedProducts;
        this.address = address;
        this.discount = discount;
        this.dateTime = getCurrentDate();
        this.totalCost = 0;
        for(int i = 0; i < this.orderedProducts.size(); ++ i)
        {
            this.totalCost += orderedProducts.get(i).getProduct().getPrice() * orderedProducts.get(i).getQuantity();
        }
        if(discount > 100)
            throw new OrderException("Discount larger than 100%, impossible");
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

    public List<ProductEntry> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<ProductEntry> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
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

    public String getCurrentDate()
    {
        return (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
    }
}
