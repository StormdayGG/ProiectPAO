package Users;
import Products.ProductEntry;
import Products.Product;
import Orders.Order;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User{
    protected String shippingAddress;
    protected List<ProductEntry> shoppingCart;

    public Customer()
    {
        shippingAddress = null;
        shoppingCart = null;
    }

    public Customer(String shippingAddress) {
        this.shippingAddress = shippingAddress;
        this.shoppingCart = new ArrayList<ProductEntry>();
    }

    public Customer(String shippingAddress, List<ProductEntry> shoppingCart) {
        this.shippingAddress = shippingAddress;
        this.shoppingCart = shoppingCart;
    }


    public Customer(int id, String firstName, String lastName, String email, String password, String phoneNumber, int[] birthday, String shippingAddress, List<ProductEntry> shoppingCart) throws Exception {
        super(id, firstName, lastName, email, password, phoneNumber, birthday);
        this.shippingAddress = shippingAddress;
        this.shoppingCart = shoppingCart;
    }

    public void AddToCart(Product item, int quantity)
    {
        this.shoppingCart.add(new ProductEntry(item, quantity));
    }

    public void RemoveFromCart(Product item, int quantity)
    {
        return;
    }

    public Order placeOrder()
    {
        return new Order();
    }
}
