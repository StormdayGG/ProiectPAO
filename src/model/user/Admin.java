package model.user;

import model.exception.DateException;
import model.exception.PhoneException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Admin extends User{
    protected int salary;
    protected String employmentDate;

    public Admin()
    {
        salary = -1;
        employmentDate = null;
    }
    public Admin(int salary, String employmentDate) {
        this.salary = salary;
        this.employmentDate = employmentDate;
    }

    public Admin(int id, String firstName, String lastName, String email, String password, String phoneNumber, String birthday, int salary, String employmentDate) throws Exception {
        super(id, firstName, lastName, email, password, phoneNumber, birthday);
        this.salary = salary;
        this.employmentDate = employmentDate;
    }

    @Override
    public void getFromId(Connection con, int user_id) throws SQLException, DateException, PhoneException
    {
        super.getFromId(con, user_id);
        PreparedStatement getAdmin = con.prepareStatement("SELECT * FROM admins WHERE user_id = ?");
        getAdmin.setInt(1, user_id);
        ResultSet res = getAdmin.executeQuery();
        if(res.next())
        {
            setSalary(res.getInt("salary"));
            setEmploymentDate(new SimpleDateFormat("dd/MM/yyyy").format(res.getDate("employment_date")));
        }
        else
            throw new SQLException("No admin with this ID found");
    }

    public void promoteToAdmin(Connection con, int user_id, int salary, String employmentDate) throws SQLException, DateException
    {
        PreparedStatement addAdmin = con.prepareStatement("INSERT INTO admins(user_id, salary, employment_date) VALUES(?, ?, ?)");
        addAdmin.setInt(1, user_id);
        addAdmin.setInt(2, salary);
        try {
            addAdmin.setDate(3, new Date(new SimpleDateFormat("dd/MM/yyyy").parse(employmentDate).getTime()));
        }
        catch(ParseException p)
        {
            throw new DateException("Employment date has invalid format");
        }
        addAdmin.executeUpdate();
    }
    @Override
    public boolean isAdmin() {
        return true;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(String employmentDate) {
        this.employmentDate = employmentDate;
    }

    /*
        SQL commands
         */
    public void removeUser(Connection con, int userId) throws SQLException
    {
        //Removing all orders regarding the user -> all items in shopping cart -> connections in admin and customers -> the user
        PreparedStatement selectOrders = con.prepareStatement("SELECT id FROM orders WHERE customer_id = ?");
        selectOrders.setInt(1, userId);
        ResultSet res = selectOrders.executeQuery();
        while(res.next())
        {
            removeOrder(con, res.getInt(1));
        }

        PreparedStatement deleteCartItems = con.prepareStatement("DELETE FROM shoppingCarts WHERE customer_id = ?");
        deleteCartItems.setInt(1, userId);
        deleteCartItems.executeUpdate();

        PreparedStatement deleteCustomer = con.prepareStatement("DELETE FROM customers WHERE user_id = ?");
        deleteCustomer.setInt(1, userId);
        deleteCustomer.executeUpdate();

        PreparedStatement deleteAdmin = con.prepareStatement("DELETE FROM admins WHERE user_id = ?");
        deleteAdmin.setInt(1, userId);
        deleteAdmin.executeUpdate();

        PreparedStatement deleteUser = con.prepareStatement("DELETE FROM users WHERE id = ?");
        deleteUser.setInt(1, userId);
        deleteUser.executeUpdate();
    }
    public void removeProduct(Connection con, int productId) throws SQLException
    {
        PreparedStatement deleteCartItems = con.prepareStatement("DELETE FROM shoppingCarts WHERE product_id = ?");
        deleteCartItems.setInt(1, productId);
        deleteCartItems.executeUpdate();

        PreparedStatement deleteOrderItems = con.prepareStatement("DELETE FROM orderedProducts WHERE product_id = ?");
        deleteOrderItems.setInt(1, productId);
        deleteOrderItems.executeUpdate();

        PreparedStatement deleteProduct = con.prepareStatement("DELETE FROM products WHERE id = ?");
        deleteProduct.setInt(1, productId);
        deleteProduct.executeUpdate();
    }
    public void removeOrder(Connection con, int orderId) throws SQLException
    {
        PreparedStatement deleteOrderItems = con.prepareStatement("DELETE FROM orderedproducts WHERE order_id = ?");
        deleteOrderItems.setInt(1, orderId);
        deleteOrderItems.executeUpdate();

        PreparedStatement deleteOrder = con.prepareStatement("DELETE FROM orders WHERE id = ?");
        deleteOrder.setInt(1, orderId);
        deleteOrder.executeUpdate();
    }
}
