package Users;

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

    public User(int id, String firstName, String lastName, String email, String password, String phoneNumber, int[] birthday) throws Exception {
        if(birthday.length != 3)
            throw new Exception("Error creating user: invalid birthday. Number of elements different from 3");
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

    public void setPassword(String oldPassword, String newPassword, String rePassword) throws Exception{
        if(this.password == null || this.password.equals(oldPassword))
        {
            if(newPassword.equals(rePassword))
            {
                this.password = newPassword;
            }
            else
                throw new Exception("Passwords do not match");
        }
        else
            throw new Exception("Old Password is incorrect");
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws Exception{
        if(isValidPhoneNumber(phoneNumber))
            this.phoneNumber = phoneNumber;
        else
            throw new Exception("Invalid Phone Number");
    }

    public int[] getBirthday() {
        return birthday;
    }

    public void setBirthday(int[] birthday) {
        this.birthday = birthday;
    }

    public boolean isValidPhoneNumber(String phoneNumber)
    {
        return true;
    }

    public boolean isAdmin()
    {
        return false;
    }
}
