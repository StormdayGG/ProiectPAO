import model.company.Company;
import model.exception.DateException;
import model.exception.OrderException;
import model.exception.PhoneException;
import model.order.Order;
import model.product.Microphone;
import model.product.Product;
import model.user.Customer;
import model.user.User;

public class Main {
    public static void main(String[] args) {
        int userId = 0;
        Customer myCustomer;
        try {
            myCustomer = new Customer(userId, "Daniel", "Boni", "danielboni2003@gmail.com", "123456", "0123456789", new int[]{27, 5, 2003}, "Constanta");
            userId += 1;
        }
        catch(DateException ex)
        {
            System.out.println("Couldn't create customer:");
            System.out.println(ex.getMessage());
            return;
        }
        int companyId = 0;
        Company[] myCompanies = {new Company(companyId++, "Compania 1", "Bucharest", "12312241233", "Raffeisen")};
        int productId = 0;
        Product[] myProducts = {new Microphone(productId, "Microphone 1", 100.5, myCompanies[0], "USB", "Black", 2, 4, 100, 200, "audio")};
        myCustomer.addToCart(myProducts[0], 5);
        int orderId = 0;
        try
        {
            Order myOrder = myCustomer.placeOrder(orderId++);
            int x = 0;
        }
        catch (OrderException ex)
        {
            System.out.println("Couldn't place order:");
            System.out.println(ex.getMessage());
            return;
        };
    }
}