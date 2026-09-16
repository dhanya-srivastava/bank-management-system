package bank;

/**
 * Represents a bank customer. A customer may hold multiple accounts,
 * but for simplicity each Account stores its owner's id/name directly
 * and this class is used mainly at registration time.
 */
public class Customer {

    private final String customerId;
    private String name;
    private String contactNumber;
    private String email;

    public Customer(String customerId, String name, String contactNumber, String email) {
        this.customerId = customerId;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%s, name=%s, contact=%s, email=%s]",
                customerId, name, contactNumber, email);
    }
}
