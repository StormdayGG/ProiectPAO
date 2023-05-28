package model.product;
import model.company.Company;

import java.sql.*;

public class Product {
    protected int id;
    protected String name;
    protected double price;
    protected int producerId;

    public Product() {
        id = -1;
        name = null;
        price = -1;
        producerId = -1;
    }

    public Product(String name, double price, int producerId) {
        this.name = name;
        this.price = price;
        this.producerId = producerId;
    }

    public Product(int id, String name, double price, int producerId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.producerId = producerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProducerId() {
        return producerId;
    }

    public void setProducerId(int producerId) {
        this.producerId = producerId;
    }

    public void printInfo(Connection con) throws SQLException
    {
        Company comp = new Company();
        comp.getFromId(con, getProducerId());
        System.out.println("Product " + String.valueOf(getId()) + ": " + getName() + " | " + getPrice() + " euros" + ", made by " + comp.getName());
    }

    public void getFromId(Connection con, int product_id) throws SQLException {
        PreparedStatement getProduct = con.prepareStatement("SELECT * FROM products WHERE id = ?");
        getProduct.setInt(1, product_id);
        ResultSet res = getProduct.executeQuery();
        if(res.next())
        {
            setId(res.getInt("id"));
            setName(res.getString("name"));
            setPrice(res.getDouble("price"));
            setProducerId(res.getInt("producer_id"));
        }
        else
            throw new SQLException("No product with this ID found");
    }

    public int insertIntoTable(Connection con) throws SQLException
    {
        PreparedStatement insertProduct = con.prepareStatement("INSERT INTO products(name, price, producer_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        insertProduct.setString(1, name);
        insertProduct.setDouble(2, price);
        insertProduct.setInt(3, producerId);
        insertProduct.executeUpdate();
        ResultSet results = insertProduct.getGeneratedKeys();
        if(results.next())
        {
            setId(results.getInt(1));
            return results.getInt(1);
        }
        else
            throw new SQLException("Error Creating Product, No ID Obtained");
    }
}
