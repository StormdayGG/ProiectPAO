package model.user;

import model.exception.DateException;
import model.exception.PasswordException;
import model.exception.PhoneException;

public class User {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;
    protected String phoneNumber;
    protected int[] birthday;

    public User() {
        id = -1;
        firstName = null;
        lastName = null;
        email = null;
        password = null;
        phoneNumber = null;
        birthday = null;
    }

    public User(int id, String firstName, String lastName, String email, String password, String phoneNumber, int[] birthday) throws DateException {
        if(!isValidDate(birthday))
            throw new DateException("Invalid birthday");
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
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

    public void setPassword(String oldPassword, String newPassword, String rePassword) throws PasswordException{
        if(this.password == null || this.password.equals(oldPassword))
        {
            if(newPassword.equals(rePassword))
            {
                this.password = newPassword;
            }
            else
                throw new PasswordException("Passwords do not match");
        }
        else
            throw new PasswordException("Old Password is incorrect");
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws PhoneException{
        if(isValidPhoneNumber(phoneNumber))
            this.phoneNumber = phoneNumber;
        else
            throw new PhoneException("Invalid Phone Number");
    }

    public int[] getBirthday() {
        return birthday;
    }

    public void setBirthday(int[] birthday) throws DateException{
        if(isValidDate(birthday))
            throw new DateException("Invalid birthday");
        this.birthday = birthday;
    }

    public boolean isValidDate(int[] date)
    {
        if(date.length != 3)
            return false;
        if(date[1] > 12 || date[1] < 1)
            return false;
        int[] daysOfMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if(date[2] % 400 == 0 || (date[2] % 100 != 0 && date[2] % 4 == 0))
            daysOfMonth[2] = 29;
        if(date[0] > daysOfMonth[date[1]] || date[0] < 1)
            return false;
        return true;
    }

    public boolean isValidPhoneNumber(String phoneNumber)
    {
        return (phoneNumber.length() == 10);
    }

    public boolean isAdmin()
    {
        return false;
    }
}
