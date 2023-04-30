package Users;

public class Admin extends User{
    protected int salary;
    protected int[] employmentDate;

    public Admin()
    {
        salary = -1;
        employmentDate = null;
    }
    public Admin(int salary, int[] employmentDate) {
        this.salary = salary;
        this.employmentDate = employmentDate;
    }

    public Admin(int id, String firstName, String lastName, String email, String password, String phoneNumber, int[] birthday, int salary, int[] employmentDate) throws Exception {
        super(id, firstName, lastName, email, password, phoneNumber, birthday);
        this.salary = salary;
        this.employmentDate = employmentDate;
    }

    @Override
    public boolean isAdmin() {
        return true;
    }

    public void removeUser(int userId) {}
    public void removeProduct(int productId) {}
    public void undoOrder(int orderId) {}
}
