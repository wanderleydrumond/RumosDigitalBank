package pt.drumond.rumosdigitalbank.app;

import pt.drumond.rumosdigitalbank.HelloApplication;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.service.CustomerService;

import java.util.Scanner;

/**
 * Application controller class.
 */
public class Bank {
    /**
     * Object used to give acces to methods from <code>Customer</code><br>
     * <em>Used on insert methods.</em>
     */

    private CustomerService customerService;

    public Bank() {

        customerService = new CustomerService();
        customerService.loadDatabase();
    }

    /**
     * Contains the application core.
     *
     * @param scanner field to be filled on menu
     * @param bank    management class object instance
     */
    public void run(Scanner scanner, Bank bank) {
        boolean flag = false;
        do {
            System.out.print("""
                    ╭═════════════════════════════════$═══╮
                         RUMOS DIGITAL BANK MANAGEMENT
                    ╰═══€═════════════════════════════════╯
                    Choose your option:
                    0. Quit application
                    1. Insert new client
                    2. Search client by NIF
                    3. Update client by NIF
                    4. Delete client by NIF
                    5. Display all clients
                                    
                    Option:\040""");

            switch (Integer.parseInt(scanner.nextLine())) {
                case 1 -> {
                    Customer customer = customerService.createCustomer(scanner, bank);
                    System.out.println("Client successfully created");
                    displayMargin(customer);
                    System.out.println(customer);
                    displayMargin(customer);
                }
                case 2 -> {
                    Customer customer = customerService.getCustomerByNif(scanner);
                    displayMargin(customer);
                    System.out.println(customer);
                    displayMargin(customer);
                }
                case 3 -> {
                    Customer customer = customerService.updateCustomer(scanner, bank);
                    System.out.println("Client successfully updated");
                    displayMargin(customer);
                    System.out.println(customer);
                    displayMargin(customer);
                }
                case 4 -> System.out.println(customerService.deleteCustomerByNif(scanner, bank) ? "Customer deleted successffully" : "Operation canceled");
                case 5 -> customerService.getAllCustomers();
                default -> HelloApplication.main(null);
            }
            System.out.print("Do you want to perform another operation? (Y)es/(N)o: ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                flag = true;
            } else {
                HelloApplication.main(null);
            }
        } while (flag);
    }

    /**
     * Displays a sequence of hyphens in the <code>Object.toString()</code> length.
     * Implies calling in the begining and in the end.
     *
     * @param customer object that will be made <code>toString().length()</code>
     */
    public void displayMargin(Customer customer) {
        for (int index = 0; index < customer.toString().length(); index++) {
            System.out.print("-");
        }
        System.out.println();
    }
}