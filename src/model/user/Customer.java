package model.user;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import model.exception.DateException;
import model.exception.OrderException;
import model.exception.PhoneException;
import model.exception.ShoppingCartException;
import model.product.ProductEntry;
import model.product.Product;
import model.order.Order;

import java.sql.*;
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
    public Customer(String firstName, String lastName, String email, String password, String phoneNumber, String birthday, String shippingAddress) {
        super(firstName, lastName, email, password, phoneNumber, birthday);
        this.shippingAddress = shippingAddress;
        shoppingCart = new ArrayList<ProductEntry>();
    }
    public Customer(int id, String firstName, String lastName, String email, String password, String phoneNumber, String birthday, String shippingAddress) {
        super(id, firstName, lastName, email, password, phoneNumber, birthday);
        this.shippingAddress = shippingAddress;
        shoppingCart = new ArrayList<ProductEntry>();
    }

    public Customer(int id, String firstName, String lastName, String email, String password, String phoneNumber, String birthday, String shippingAddress, List<ProductEntry> shoppingCart) {
        super(id, firstName, lastName, email, password, phoneNumber, birthday);
        this.shippingAddress = shippingAddress;
        this.shoppingCart = shoppingCart;
    }

    public Customer(User other, String shippingAddress)
    {
        super(other);
        this.shippingAddress = shippingAddress;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<ProductEntry> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<ProductEntry> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public void getFromId(Connection con, int user_id) throws SQLException, DateException, PhoneException
    {
        super.getFromId(con, user_id);
        PreparedStatement getCustomer = con.prepareStatement("SELECT * FROM customers WHERE user_id = ?");
        getCustomer.setInt(1, user_id);
        ResultSet res = getCustomer.executeQuery();
        if(res.next())
        {
            setShippingAddress(res.getString("shipping_address"));
            getCurrentCart(con);
        }
        else
            throw new SQLException("No customer with this ID found");
    }

    public void addToCart(int itemId, int quantity)
    {
        for(int i = 0; i < shoppingCart.size(); ++ i)
        {
            if(shoppingCart.get(i).getProductId() == itemId)
            {
                int newQuantity = shoppingCart.get(i).getQuantity() + quantity;
                shoppingCart.set(i, new ProductEntry(itemId, newQuantity));
                return;
            }
        }
        shoppingCart.add(new ProductEntry(itemId, quantity));
    }

    public void removeFromCart(int itemId, int quantity) throws ShoppingCartException
    {
        for(int i = 0; i < shoppingCart.size(); ++ i)
        {
            if(shoppingCart.get(i).getProductId() == itemId)
            {
                if(shoppingCart.get(i).getQuantity() < quantity)
                    throw new ShoppingCartException("Insufficient quantity in cart, can not remove requested amount");
                int newQuantity = shoppingCart.get(i).getQuantity() - quantity;
                if(newQuantity > 0)
                    shoppingCart.set(i, new ProductEntry(itemId, newQuantity));
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

    public ProductEntry getShoppingCartItem(int index) throws ShoppingCartException
    {
        if(index >= 0 && index < shoppingCart.size())
            return shoppingCart.get(index);
        throw new ShoppingCartException("Invalid index, could not fetch shopping cart item");
    }

    public Order placeOrder(String address, double discount) throws OrderException
    {
        if(shoppingCart.size() == 0)
            throw new OrderException("Empty cart, could not place order");
        try
        {
            Order placedOrder = new Order(id, shoppingCart, address, discount);
            shoppingCart.clear();
            return placedOrder;
        }
        catch(OrderException err)
        {
            throw err;
        }

    }
    public Order placeOrder(double discount) throws OrderException
    {
        try
        {
            return placeOrder(shippingAddress, discount);
        }
        catch(OrderException err)
        {
            throw err;
        }
    }
    public Order placeOrder(String address) throws OrderException
    {
        try
        {
            return placeOrder(address, 0);
        }
        catch(OrderException err)
        {
            throw err;
        }
    }
    public Order placeOrder() throws OrderException
    {
        try
        {
            return placeOrder(shippingAddress, 0);
        }
        catch(OrderException err)
        {
            throw err;
        }
    }

    public void getCurrentCart(Connection con) throws SQLException
    {
        PreparedStatement getCart = con.prepareStatement("SELECT * FROM shoppingCarts WHERE customer_id = ?");
        getCart.setInt(1, id);
        ResultSet results = getCart.executeQuery();
        shoppingCart.clear();
        while(results.next())
        {
            shoppingCart.add(new ProductEntry(results.getInt(2), results.getInt(3)));
        }
    }

    public void updateCart(Connection con) throws SQLException
    {
        PreparedStatement deleteOldCart = con.prepareStatement("DELETE FROM shoppingCarts WHERE customer_id = ?");
        deleteOldCart.setInt(1, id);
        deleteOldCart.executeUpdate();
        PreparedStatement insertNewCart = con.prepareStatement("INSERT INTO shoppingCarts(customer_id, product_id, quantity) VALUES (?, ?, ?)");
        for (ProductEntry productEntry : shoppingCart) {
            insertNewCart.setInt(1, id);
            insertNewCart.setInt(2, productEntry.getProductId());
            insertNewCart.setInt(3, productEntry.getQuantity());
            insertNewCart.executeUpdate();
        }
    }

    public int insertIntoTable(Connection con) throws SQLException, DateException
    {
        int newId = super.insertIntoTable(con);
        PreparedStatement insertCustomer = con.prepareStatement("INSERT INTO customers(user_id, shipping_address) VALUES (?, ?)");
        insertCustomer.setInt(1, newId);
        insertCustomer.setString(2, shippingAddress);
        insertCustomer.executeUpdate();
        updateCart(con);
        return newId;
    }
}
