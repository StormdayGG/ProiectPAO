package model.order;
import model.exception.DateException;
import model.exception.OrderException;
import model.product.Product;
import model.user.Customer;
import model.product.ProductEntry;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class Order {
    protected int id;
    protected int customerId;
    protected List<ProductEntry> orderedProducts;
    protected String dateTime;
    protected double totalCost;
    protected String address;
    protected double discount;

    public Order() {
        id = -1;
        customerId = -1;
        orderedProducts = null;
        dateTime = null;
        totalCost = -1;
        address = null;
        discount = 0;
    }
    public Order(int customerId, List<ProductEntry> orderedProducts, String address, double discount) throws OrderException {
        this.customerId = customerId;
        this.orderedProducts = new ArrayList<>(orderedProducts);
        this.address = address;
        this.discount = discount;
        this.dateTime = getCurrentDate();
        this.totalCost = 0;
        if(discount > 100)
            throw new OrderException("Discount larger than 100%, impossible");
    }
    public Order(int id, int customerId, List<ProductEntry> orderedProducts, String address, double discount) throws OrderException {
        this.id = id;
        this.customerId = customerId;
        this.orderedProducts = new ArrayList<>(orderedProducts);
        this.address = address;
        this.discount = discount;
        this.dateTime = getCurrentDate();
        this.totalCost = 0;
        if(discount > 100)
            throw new OrderException("Discount larger than 100%, impossible");
    }

    public void getFromId(Connection con, int orderId) throws SQLException
    {
        PreparedStatement getOrder = con.prepareStatement("SELECT * FROM orders WHERE id = ?");
        getOrder.setInt(1, orderId);
        ResultSet res = getOrder.executeQuery();
        if(res.next())
        {
            setId(res.getInt("id"));
            setCustomerId(res.getInt("customer_id"));
            setTotalCost(res.getDouble("total_cost"));
            setDiscount(res.getDouble("discount"));
            setAddress(res.getString("address"));
            setDateTime(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(res.getTimestamp("dateTime")));

            PreparedStatement getProducts = con.prepareStatement("SELECT * FROM orderedProducts WHERE order_id = ?");
            getProducts.setInt(1, id);
            ResultSet results = getProducts.executeQuery();
            orderedProducts.clear();
            while(results.next())
            {
                orderedProducts.add(new ProductEntry(results.getInt(2), results.getInt(3)));
            }
        }
        else
            throw new SQLException("No product with this ID found");
    }

    public void calculateCost(Connection con) throws SQLException
    {
        this.totalCost = 0;
        for(int i = 0; i < this.orderedProducts.size(); ++ i)
        {
            Product newProduct = new Product();
            newProduct.getFromId(con, orderedProducts.get(i).getProductId());
            this.totalCost += newProduct.getPrice() * orderedProducts.get(i).getQuantity();
        }
    }

    public int insertIntoTable(Connection con) throws SQLException, DateException
    {
        calculateCost(con);
        PreparedStatement insertOrder = con.prepareStatement("INSERT INTO orders(customer_id, dateTime, total_cost, address, discount) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        insertOrder.setInt(1, customerId);
        insertOrder.setTimestamp(2, getOrderTimestamp());
        insertOrder.setDouble(3, totalCost);
        insertOrder.setString(4, address);
        insertOrder.setDouble(5, discount);
        insertOrder.executeUpdate();
        ResultSet results = insertOrder.getGeneratedKeys();
        if(results.next())
        {
            setId(results.getInt(1));
            PreparedStatement insertOrderedProduct = con.prepareStatement("INSERT INTO orderedProducts(order_id, product_id, quantity) VALUES (?, ?, ?)");
            for(ProductEntry product: orderedProducts)
            {
                insertOrderedProduct.setInt(1, getId());
                insertOrderedProduct.setInt(2, product.getProductId());
                insertOrderedProduct.setInt(3, product.getQuantity());
                insertOrderedProduct.executeUpdate();
            }
            return results.getInt(1);
        }
        else
            throw new SQLException("Error Creating Order, No ID Obtained");
    }

    private java.sql.Timestamp getOrderTimestamp() throws DateException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            java.sql.Timestamp d = new java.sql.Timestamp(formatter.parse(dateTime).getTime());
            return d;
        }
        catch(ParseException p)
        {
            throw new DateException("Invalid datetime format");
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscountedAmount() {
        return ((100 - discount) / 100) * totalCost;
    }

    public String getCurrentDate()
    {
        return (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
    }
}
