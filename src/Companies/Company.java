package Companies;
import Users.User;
public class Company {
    protected int id;
    protected String name;
    protected String address;
    protected String iban;
    protected String bankName;

    Company(int id, String name, String address, String iban, String bankName)
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

    public boolean editBankInfo(String iban, String bankName, User user)
    {
        if(!user.isAdmin())
        {
            return false;
        }
        else
        {
            this.iban = iban;
            this.bankName = bankName;
            return true;
        }

    }
}
