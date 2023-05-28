package model.company;
import model.exception.PermissionException;
import model.user.User;

import java.sql.*;

public class Company {
    protected int id;
    protected String name;
    protected String address;
    protected String iban;
    protected String bankName;

    public Company()
    {

    }
    public Company(String name, String address, String iban, String bankName)
    {
        this.name = name;
        this.address = address;
        this.iban = iban;
        this.bankName = bankName;
    }

    public Company(int id, String name, String address, String iban, String bankName)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.iban = iban;
        this.bankName = bankName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIban() {
        return iban;
    }

    public String getBankName() {
        return bankName;
    }

    public void editBankInfo(String iban, String bankName, User user) throws PermissionException
    {
        if(!user.isAdmin())
        {
            throw new PermissionException("You need to be an admin to edit bank information");
        }
        else
        {
            this.iban = iban;
            this.bankName = bankName;
        }
    }

    private void setBankInfo(String iban, String bankName)
    {
        this.iban = iban;
        this.bankName = bankName;
    }

    public int insertIntoTable(Connection con) throws SQLException
    {
        PreparedStatement insertCompany = con.prepareStatement("INSERT INTO companies(name, address, iban, bank_name) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        insertCompany.setString(1, name);
        insertCompany.setString(2, address);
        insertCompany.setString(3, iban);
        insertCompany.setString(4, bankName);
        insertCompany.executeUpdate();
        ResultSet results = insertCompany.getGeneratedKeys();
        if(results.next())
        {
            setId(results.getInt(1));
            return results.getInt(1);
        }
        else
            throw new SQLException("Error Creating Company, No ID Obtained");
    }

    public void getFromId(Connection con, int companyId) throws SQLException
    {
        PreparedStatement getCompany = con.prepareStatement("SELECT * FROM companies WHERE id = ?");
        getCompany.setInt(1, companyId);
        ResultSet res = getCompany.executeQuery();
        if(res.next())
        {
            setId(res.getInt("id"));
            setName(res.getString("name"));
            setAddress(res.getString("address"));
            setBankInfo(res.getString("iban"), res.getString("bank_name"));
        }
        else
            throw new SQLException("No company with this ID found");
    }
}
