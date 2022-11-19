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
    private Customer customer;

    public Bank() {
        customer = new Customer();
    }

    /**
     * Contains the application core.
     *
     * @param scanner field to be filled on menu
     */
    public void run(Scanner scanner, CustomerService customerService) {
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
                case 1 -> createCustomer(scanner, customer, customerService);
                case 2 -> findCustomerByNif(scanner, customerService);
                case 3 -> updateCustomer(scanner, customerService);
                case 4 -> deleteCustomer(scanner, customerService);
                case 5 -> findAllCustomers(customerService);
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
     * Creates a new customer.<br>
     * <em>Calls the method <code>create()</code> from <code>CustomerSerice</code></em>
     *
     * @param scanner field to be filled inside each inner method.
     */
    private void createCustomer(Scanner scanner, Customer customer, CustomerService customerService) {
        customerService.create(customer, scanner, customerService);
    }

    /**
     * Finds a customer with a given NIF number. <br>
     * <em>Calls the method <code>findCustomerByNif()</code> from <code>CustomerSerice</code></em>
     *
     * @param scanner field to be filled with the NIF number
     */
    private void findCustomerByNif(Scanner scanner, CustomerService customerService) {
        customerService.findCustomerByNif(scanner);
    }

    /**
     * Updates a customer with a given NIF number.<br>
     * <em>Calls the method <code>update()</code> from <code>CustomerSerice</code></em>
     *
     * @param scanner field to be filled with the NIF number
     */
    private void updateCustomer(Scanner scanner, CustomerService customerService) {
        customerService.update(scanner, customerService);
    }

    /**
     * Deletes a customer with a given NIF number.<br>
     * <em>Calls the method <code>delete()</code> from <code>CustomerSerice</code></em>
     *
     * @param scanner field to be filled with the NIF number
     */
    private void deleteCustomer(Scanner scanner, CustomerService customerService) {
        customerService.delete(scanner);
    }

    /**
     * Displays all customers.
     */
    private void findAllCustomers(CustomerService customerService) {
        customerService.findAll();
    }

    /**
     * Displays a sequence of hyphens in the <code>Object.toString()</code> length.
     * Implies calling in the begining and in the end.
     *
     * @param customerElement object that will be made <code>toString().length()</code>
     */
    public void displayMargin(Customer customerElement) {
        for (int index = 0; index < customerElement.toString().length(); index++) {
            System.out.print("-");
        }
        System.out.println();
    }
}