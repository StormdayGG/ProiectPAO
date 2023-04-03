package User;

public class Customer extends User{
    protected String shippingAddress;
    protected ProductEntry[] shoppingCart;

    public Customer()
    {
        shippingAddress = null;
        shoppingCart = null;
    }

    public Customer(String shippingAddress, ProductEntry[] shoppingCart) {
        this.shippingAddress = shippingAddress;
        this.shoppingCart = shoppingCart;
    }

    public Customer(int id, String firstName, String lastName, String email, String password, String phoneNumber, int[] birthday, String shippingAddress, ProductEntry[] shoppingCart) throws Exception {
        super(id, firstName, lastName, email, password, phoneNumber, birthday);
        this.shippingAddress = shippingAddress;
        this.shoppingCart = shoppingCart;
    }
}
