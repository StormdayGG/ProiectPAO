package model.company;
import model.exception.PermissionException;
import model.user.User;
public class Company {
    protected int id;
    protected String name;
    protected String address;
    protected String iban;
    protected String bankName;

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
}
