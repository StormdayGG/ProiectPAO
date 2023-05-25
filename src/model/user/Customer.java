package model.user;
import model.exception.DateException;
import model.exception.OrderException;
import model.exception.ShoppingCartException;
import model.product.ProductEntry;
import model.product.Product;
import model.order.Order;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User{
    protected String shippingAddress;
    protected List<ProductEntry> shoppingCart;

    public Customer()
    {
        shippingAddress = "";
        shoppingCart = new ArrayList<ProductEntry>();
    }

    public Customer(String shippingAddress) {
        this.shippingAddress = shippingAddress;
        this.shoppingCart = new ArrayList<ProductEntry>();
    }

    public Customer(String shippingAddress, List<ProductEntry> shoppingCart) {
        this.shippingAddress = shippingAddress;
        this.shoppingCart = shoppingCart;
        shoppingCart = new ArrayList<ProductEntry>();
    }

    public Customer(int id, String firstName, String lastName, String email, String password, String phoneNumber, int[] birthday, String shippingAddress) throws DateException {
        super(id, firstName, lastName, email, password, phoneNumber, birthday);
        this.shippingAddress = shippingAddress;
        shoppingCart = new ArrayList<ProductEntry>();
    }

    public Customer(int id, String firstName, String lastName, String email, String password, String phoneNumber, int[] birthday, String shippingAddress, List<ProductEntry> shoppingCart) throws DateException {
        super(id, firstName, lastName, email, password, phoneNumber, birthday);
        this.shippingAddress = shippingAddress;
        this.shoppingCart = shoppingCart;
    }

    public void addToCart(Product item, int quantity)
    {
        for(int i = 0; i < shoppingCart.size(); ++ i)
        {
            if(shoppingCart.get(i).getProduct() == item)
            {
                int newQuantity = shoppingCart.get(i).getQuantity() + quantity;
                shoppingCart.set(i, new ProductEntry(item, newQuantity));
                return;
            }
        }
        shoppingCart.add(new ProductEntry(item, quantity));
    }

    public void removeFromCart(Product item, int quantity) throws ShoppingCartException
    {
        for(int i = 0; i < shoppingCart.size(); ++ i)
        {
            if(shoppingCart.get(i).getProduct() == item)
            {
                if(shoppingCart.get(i).getQuantity() < quantity)
                    throw new ShoppingCartException("Insufficient quantity in cart, can not remove requested amount");
                int newQuantity = shoppingCart.get(i).getQuantity() - quantity;
                if(newQuantity > 0)
                    shoppingCart.set(i, new ProductEntry(item, newQuantity));
                else
                    shoppingCart.remove(i);
                return;
            }
        }
        throw new ShoppingCartException("Product not found in cart, could not be removed");
    }

    public int getShoppingCartSize()
    {
        return shoppingCart.size();
    }

    public ProductEntry getCartItem(int index) throws ShoppingCartException
    {
        if(index >= 0 && index < shoppingCart.size())
            return shoppingCart.get(index);
        throw new ShoppingCartException("Invalid index, could not fetch shopping cart item");
    }

    public Order placeOrder(int orderId, String address, float discount) throws OrderException
    {
        try
        {
            return new Order(orderId, this, shoppingCart, address, discount);
        }
        catch(OrderException err)
        {
            throw err;
        }

    }
    public Order placeOrder(int orderId, float discount) throws OrderException
    {
        try
        {
            return placeOrder(orderId, shippingAddress, discount);
        }
        catch(OrderException err)
        {
            throw err;
        }
    }
    public Order placeOrder(int orderId, String address) throws OrderException
    {
        try
        {
            return placeOrder(orderId, address, 0);
        }
        catch(OrderException err)
        {
            throw err;
        }
    }
    public Order placeOrder(int orderId) throws OrderException
    {
        try
        {
            return placeOrder(orderId, shippingAddress, 0);
        }
        catch(OrderException err)
        {
            throw err;
        }
    }
}
