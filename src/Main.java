import com.mysql.cj.xdevapi.Client;
import model.company.Company;
import model.exception.DateException;
import model.exception.OrderException;
import model.exception.PhoneException;
import model.exception.ShoppingCartException;
import model.order.Order;
import model.product.Microphone;
import model.product.Product;
import model.product.ProductEntry;
import model.user.Admin;
import model.user.Customer;
import model.user.User;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void continueAsCustomer(Connection con, Scanner input, Customer customer) throws SQLException, ShoppingCartException
    {
        System.out.println("Welcome, " + customer.getFirstName() + " " + customer.getLastName() + "!");
        while(true)
        {
            System.out.println("Main Menu: ");
            System.out.println("Show all products: \"products\"");
            System.out.println("Show shopping cart: \"cart\"");
            System.out.println("Add to cart: \"buy\" (will begin secondary dialogue)");
            System.out.println("Remove from cart: \"undo_buy\" (will begin secondary dialogue)");
            System.out.println("Buy products in cart: \"order\"");
            System.out.println("Exit: \"exit\"");

            String userInput = input.nextLine();
            switch(userInput) {
                case "products":
                    Statement stm = con.createStatement();
                    ResultSet results = stm.executeQuery("SELECT id FROM products");
                    while(results.next())
                    {
                        Product currentProduct = new Product();
                        currentProduct.getFromId(con, results.getInt(1));
                        currentProduct.printInfo(con);
                    }
                    break;
                case "cart":
                    double totalPrice = 0;
                    for(int i = 0; i < customer.getShoppingCartSize(); ++ i)
                    {
                        ProductEntry shoppingCartItem = customer.getShoppingCartItem(i);
                        Product currentProduct = new Product();
                        currentProduct.getFromId(con, shoppingCartItem.getProductId());
                        double itemPrice = currentProduct.getPrice() * shoppingCartItem.getQuantity();
                        System.out.println(String.valueOf(i + 1) + ": " + currentProduct.getName() + "(id: " + String.valueOf(currentProduct.getId()) + ") - " + String.valueOf(shoppingCartItem.getQuantity()) + " | " + String.valueOf(itemPrice));
                        totalPrice += itemPrice;
                    }
                    System.out.println("Total Price: " + String.valueOf(totalPrice));
                    break;
                case "buy":
                    System.out.println("\tProduct ID: ");
                    int productId = input.nextInt();
                    System.out.println("\tQuantity: ");
                    int quantity = input.nextInt();
                    userInput = input.nextLine();
                    if(quantity < 0)
                    {
                        System.out.println("Error adding product to cart: quantity must be positive");
                        break;
                    }

                    try
                    {
                        Product testProduct = new Product();
                        testProduct.getFromId(con, productId);
                        customer.addToCart(productId, quantity);
                        customer.updateCart(con);
                    }
                    catch(SQLException exc)
                    {
                        System.out.println("Error adding product to cart: " + exc.getMessage());
                    }
                    break;
                case "undo_buy":
                    System.out.println("\tProduct ID: ");
                    productId = input.nextInt();
                    System.out.println("\tQuantity: ");
                    quantity = input.nextInt();
                    userInput = input.nextLine();
                    if(quantity < 0)
                    {
                        System.out.println("Error removing product from cart: quantity must be positive");
                        break;
                    }
                    try
                    {
                        Product testProduct = new Product();
                        testProduct.getFromId(con, productId);
                        customer.removeFromCart(productId, quantity);
                        customer.updateCart(con);
                    }
                    catch(SQLException exc)
                    {
                        System.out.println("Error from the database: " + exc.getMessage());
                    }
                    catch(ShoppingCartException exc)
                    {
                        System.out.println("Error removing product from cart: " + exc.getMessage());
                    }
                    break;
                case "order":
                    try
                    {
                        System.out.println("Discount percentage: ");
                        double discount = input.nextDouble();
                        input.nextLine();
                        System.out.println("Use default address? (Y/N)");
                        userInput = input.nextLine();
                        Order myOrder;
                        if(userInput.equals("Y"))
                            myOrder = customer.placeOrder(discount);
                        else
                        {
                            System.out.println("Custom Address: ");
                            userInput = input.nextLine();
                            myOrder = customer.placeOrder(userInput, discount);
                        }
                        int orderId = myOrder.insertIntoTable(con);
                        customer.updateCart(con);
                        System.out.println("Order placed with id: " + String.valueOf(orderId) + " | Final Cost: " + myOrder.getDiscountedAmount());
                    }
                    catch(OrderException exc)
                    {
                        System.out.println("Error placing order: " + exc.getMessage());
                    }
                    catch(DateException e)
                    {
                        System.out.println("Error with the date system during order placement: " + e.getMessage());
                    }
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Invalid input, please try again");
                    break;
            }
        }
    }
    public static void continueAsAdmin(Connection con, Scanner input, Admin admin) throws SQLException, ShoppingCartException {
        System.out.println("Welcome, " + admin.getFirstName() + " " + admin.getLastName() + "!");
        System.out.println("Admin Dashboard");
        while (true)
        {
            System.out.println("Main Menu: ");
            System.out.println("Promote user to admin: \"add_admin\" (will begin secondary dialogue)");
            System.out.println("Remove user: \"remove_user\" (will begin secondary dialogue)");
            System.out.println("Remove product: \"remove_product\" (will begin secondary dialogue)");
            System.out.println("Remove order: \"remove_order\" (will begin secondary dialogue)");
            System.out.println("Exit: \"exit\"");

            String userInput = input.nextLine();
            switch(userInput) {
                case "add_admin":
                    System.out.println("Id of user you would like to promote to admin:");
                    int user_id = input.nextInt();
                    System.out.println("Salary:");
                    int salary = input.nextInt();
                    System.out.println("Employment Date: (dd/MM/yyyy)");
                    input.nextLine();
                    String employmentDate = input.nextLine();
                    try {
                        admin.promoteToAdmin(con, user_id, salary, employmentDate);
                    }
                    catch(DateException ex)
                    {
                        System.out.println("Error with employment date: " + ex.getMessage());
                    }
                    catch(SQLException ex)
                    {
                        System.out.println("Error promoting user: " + ex.getMessage());
                    }
                    break;
                case "remove_user":
                    System.out.println("Id of user you want to remove: ");
                    user_id = input.nextInt();
                    System.out.println("Doing this will also remove all records of orders they made and all items in their shopping cart. Are you sure you want to continue? (Y/N)");
                    input.nextLine();
                    userInput = input.nextLine();
                    if(!userInput.equals("Y"))
                    {
                        System.out.println("Input differnt from Y, cancelling procedure");
                        break;
                    }
                    admin.removeUser(con, user_id);
                    break;
                case "remove_product":
                    System.out.println("Id of order you want to remove: ");
                    int product_id = input.nextInt();
                    System.out.println("Doing this will also remove it from records of orders that included it, which might lead to the totals being wrong, and from the shopping carts of users. Are you sure you want to continue? (Y/N)");
                    input.nextLine();
                    userInput = input.nextLine();
                    if(!userInput.equals("Y"))
                    {
                        System.out.println("Input differnt from Y, cancelling procedure");
                        break;
                    }
                    admin.removeProduct(con, product_id);
                    break;
                case "remove_order":
                    System.out.println("Id of order you want to remove from history: ");
                    int order_id = input.nextInt();
                    input.nextLine();
                    admin.removeOrder(con, order_id);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Invalid input, please try again");
                    break;
            }
        }
    }
    public static void main(String[] args) throws SQLException, DateException, ClassNotFoundException, OrderException, ShoppingCartException, PhoneException {
        Connection mysqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pao_schema", "admin", "admin");
        System.out.println("Hello! Please type R to register as a client, C to login as a client or A to login as an admin");
        Scanner userInput = new Scanner(System.in);
        boolean validInput = false;
        while(!validInput)
        {
            String text = userInput.nextLine();
            switch (text) {
                case "R":
                    System.out.println("Beginning client registration:");
                    System.out.println("First Name:");
                    String firstName = userInput.nextLine();
                    System.out.println("Last Name:");
                    String lastName = userInput.nextLine();
                    System.out.println("Email:");
                    String email = userInput.nextLine();
                    System.out.println("Phone Number: (must be 10 digits)");
                    String phoneNumber = userInput.nextLine();
                    System.out.println("Password:");
                    String password = userInput.nextLine();
                    System.out.println("Birthday: (dd/MM/yyyy format)");
                    String birthday = userInput.nextLine();
                    System.out.println("Shipping Address:");
                    String shippingAddress = userInput.nextLine();

                    Customer newCustomer = new Customer(firstName, lastName, email, password, phoneNumber, birthday, shippingAddress);
                    newCustomer.insertIntoTable(mysqlConnection);

                    System.out.println("Registration Successful! Rerun the app to login!");

                    validInput = true;
                    break;
                case "C":
                    Customer myCustomer = new Customer();
                    boolean loggedIn = false;
                    while(!loggedIn)
                    {
                        System.out.println("Log In as Customer:");
                        System.out.println("First Name:");
                        firstName = userInput.nextLine();
                        System.out.println("Last Name:");
                        lastName = userInput.nextLine();
                        System.out.println("Password:");
                        password = userInput.nextLine();
                        try
                        {
                            myCustomer.getFromId(mysqlConnection, myCustomer.login(mysqlConnection, firstName, lastName, password));
                            loggedIn = true;
                        }
                        catch(SQLException ex)
                        {
                            System.out.println("Error during login:");
                            System.out.println(ex.getMessage());
                            System.out.println("Please try again");
                            System.out.println();
                        }
                    }
                    continueAsCustomer(mysqlConnection, userInput, myCustomer);
                    validInput = true;
                    break;
                case "A":
                    Admin myAdmin = new Admin();
                    loggedIn = false;
                    while(!loggedIn)
                    {
                        System.out.println("Log In as Admin:");
                        System.out.println("First Name:");
                        firstName = userInput.nextLine();
                        System.out.println("Last Name:");
                        lastName = userInput.nextLine();
                        System.out.println("Password:");
                        password = userInput.nextLine();
                        try
                        {
                            myAdmin.getFromId(mysqlConnection, myAdmin.login(mysqlConnection, firstName, lastName, password));
                            loggedIn = true;
                        }
                        catch(SQLException ex)
                        {
                            System.out.println("Error during login:");
                            System.out.println(ex.getMessage());
                            System.out.println("Please try again");
                            System.out.println();
                        }
                    }
                    continueAsAdmin(mysqlConnection, userInput, myAdmin);
                    validInput = true;
                    break;
                default:
                    System.out.println("Error: input not recognized. Please try again.");
            }
        }
        mysqlConnection.close();
    }
}