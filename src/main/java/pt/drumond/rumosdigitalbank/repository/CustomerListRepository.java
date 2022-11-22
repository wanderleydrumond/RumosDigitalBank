package pt.drumond.rumosdigitalbank.repository;

import pt.drumond.rumosdigitalbank.model.Customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Database layer.<br>
 * <em>Implementation by <code>ArrayList</code></em>
 */
public class CustomerListRepository {
    /**
     * Database that contains all app customers.
     */
    private ArrayList<Customer> customers;

    public CustomerListRepository() {
        customers = new ArrayList<>();
    }

    /**
     * Adds the given <code>Customer</code> instance to the customers list
     *
     * @param customer instance to be added
     */
    public Customer save(Customer customer) {
        customers.add(customer);

        return customer;
    }

    /**
     * Finds and specific customer in database.
     *
     * @param nif     the customer's identifier
     * @param scanner field to be filled with the customer's NIF number in case of mistyping
     * @return
     * <ul>
     *     <li>the <code>Customer</code> that owns the NIF number</li>
     *     <li>null if the NIF is filled with 0 and the operation is cancelled</li>
     * </ul>
     */
    public Customer findByNif(String nif, Scanner scanner) {
        AtomicBoolean wasNifFound = new AtomicBoolean(false);
        String typedNif = nif;
        while (!wasNifFound.get()) {
            for (Customer customerElement : customers) {
                if (customerElement.getNif().equalsIgnoreCase(typedNif)) {
                    wasNifFound.set(true);
                    return customerElement;
                }
            }
            if (!wasNifFound.get()) {
                System.out.print("Does not exist a client with the given NIF number\nEnter a valid NIF or type 0 to quit: ");
                typedNif = scanner.nextLine();
                if (typedNif.equals("0")) {
                    break;
                }
            }
        }
        return null;
    }

    /**
     * Deletes a customer in database.
     *
     * @param customer the customer object instance to be deleted
     */
    public void delete(Customer customer) {
        customers.removeIf(customerElement -> customerElement.getNif().equals(customer.getNif()));
    }

    /**
     * Finds all customers registered in the database.
     *
     * @return the whole customer's list
     */
    public List<Customer> findAll() {
        return customers;
    }

    /**
     * Generates initial data to fill the Arraylist that's serves as database.
     */
    public void loadDatabase() {
        Customer customer1 = new Customer("987456321", "Jane Doe", "123456", "321654987", "99885544", "someone@email.com", "Lawyer", LocalDate.of(1983, 2, 24));
        Customer customer2 = new Customer("123456789", "John Doe", "654321", "321644481", "99221166", "anything@email.com", "Pilot", LocalDate.of(1973, 12, 12));
        Customer customer3 = new Customer("132456789", "Rosalvo Doe", "123654", "325554937", "99887766", "something@email.com", "Firefighter", LocalDate.of(1985, 8, 2));
        Customer customer4 = new Customer("369258147", "João das Couves", "526341", "222111333", "951951951", "cabbages@email.pt", "seller", LocalDate.of(1972, 1, 22));
        Customer customer5 = new Customer("144774144", "Aang", "540022", "250140320", "981258457", "air@avatar.com", "avatar", LocalDate.of(2005, 1, 22));
        Customer customer6 = new Customer("144774144", "Korra", "541166", "335478852", "999258741", "water@avatar.com", "avatar", LocalDate.of(2011, 1, 22));

        customers.addAll(Arrays.asList(customer1, customer2, customer3, customer4, customer5, customer6));
    }
}