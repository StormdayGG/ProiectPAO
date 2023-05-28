package model.user;

import model.exception.DateException;
import model.exception.PasswordException;
import model.exception.PhoneException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class User {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;
    protected String phoneNumber;
    protected String birthday;

    public User() {
        id = -1;
        firstName = null;
        lastName = null;
        email = null;
        password = null;
        phoneNumber = null;
        birthday = null;
    }

    public User(String firstName, String lastName, String email, String password, String phoneNumber, String birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }
    public User(int id, String firstName, String lastName, String email, String password, String phoneNumber, String birthday) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    public User(User other)
    {
        this.id = other.getId();
        this.firstName = other.getFirstName();
        this.lastName = other.getLastName();
        this.email = other.getEmail();
        this.password = other.getPassword();
        this.phoneNumber = other.getPhoneNumber();
        this.birthday = other.getBirthday();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws PhoneException{
        if(isValidPhoneNumber(phoneNumber))
            this.phoneNumber = phoneNumber;
        else
            throw new PhoneException("Invalid Phone Number");
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday){
        this.birthday = birthday;
    }

    public boolean isValidPhoneNumber(String phoneNumber)
    {
        return (phoneNumber.length() == 10);
    }

    public boolean isAdmin()
    {
        return false;
    }

    public int insertIntoTable(Connection con) throws SQLException, DateException
    {
        PreparedStatement insertUser = con.prepareStatement("INSERT INTO users(first_name, last_name, email, password, phone_number, birthday) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        insertUser.setString(1, firstName);
        insertUser.setString(2, lastName);
        insertUser.setString(3, email);
        insertUser.setString(4, password);
        insertUser.setString(5, phoneNumber);
        insertUser.setDate(6, getBirthdayAsDate());
        insertUser.executeUpdate();
        ResultSet results = insertUser.getGeneratedKeys();
        if(results.next())
        {
            setId(results.getInt(1));
            return results.getInt(1);
        }
        else
            throw new SQLException("Error Creating User, No ID Obtained");
    }

    public void getFromId(Connection con, int user_id) throws SQLException, DateException, PhoneException {
        PreparedStatement getUser = con.prepareStatement("SELECT * FROM users WHERE id = ?");
        getUser.setInt(1, user_id);
        ResultSet res = getUser.executeQuery();
        if(res.next())
        {
            setId(res.getInt("id"));
            setFirstName(res.getString("first_name"));
            setLastName(res.getString("last_name"));
            setEmail(res.getString("email"));
            try {
                String bd = res.getDate("birthday").toString();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat stdFormatter = new SimpleDateFormat("dd/MM/yyyy");
                setBirthday(stdFormatter.format(formatter.parse(bd)));
            }
            catch(ParseException p)
            {
                throw new DateException("Error converting birthday format");
            }
            setPassword(res.getString("password"));
            setPhoneNumber(res.getString("phone_number"));
        }
        else
            throw new SQLException("No user with this ID found");
    }

    public int login(Connection con, String firstName, String lastName, String password) throws SQLException
    {
        PreparedStatement getUser = con.prepareStatement("SELECT id FROM users WHERE first_name = ? AND last_name = ? AND password = ?");
        getUser.setString(1, firstName);
        getUser.setString(2, lastName);
        getUser.setString(3, password);
        ResultSet res = getUser.executeQuery();
        if(res.next()) {
            return res.getInt("id");
        }
        else
            throw new SQLException("Invalid credentials, could not log in");
    }

    private Date getBirthdayAsDate() throws DateException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date d = new Date(formatter.parse(birthday).getTime());
            return d;
        }
        catch(ParseException p)
        {
            throw new DateException("Invalid birthday format");
        }
    }
}
