/**
 * BankAccount.java
 *
 * Superclass representing a generic bank account. Holds core account
 * data (owner name, account ID, and balance) and provides basic
 * deposit/withdrawal behavior that subclasses can extend or override.
 */
public class BankAccount {

    private String firstName;
    private String lastName;
    private int accountID;
    private double balance;

    /**
     * Constructor. Initializes balance to zero, as required.
     */
    public BankAccount(String firstName, String lastName, int accountID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountID = accountID;
        this.balance = 0.0;
    }

    /**
     * Adds the given amount to the current balance.
     */
    public void deposit(double amount) {
        if (amount < 0) {
            System.out.println("Deposit amount cannot be negative.");
            return;
        }
        balance += amount;
    }

    /**
     * Subtracts the given amount from the current balance.
     * The base class does not allow the balance to go negative.
     */
    public void withdrawal(double amount) {
        if (amount < 0) {
            System.out.println("Withdrawal amount cannot be negative.");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds. Withdrawal denied.");
            return;
        }
        balance -= amount;
    }

    // ---------- Getters and Setters ----------

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    /**
     * Getter to return the balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Protected setter so subclasses (e.g., CheckingAccount) can adjust
     * balance directly when implementing custom withdrawal behavior
     * such as overdrafts.
     */
    protected void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Prints all account information.
     */
    public void accountSummary() {
        System.out.println("----- Account Summary -----");
        System.out.println("Account Holder: " + firstName + " " + lastName);
        System.out.println("Account ID:     " + accountID);
        System.out.printf("Balance:        $%.2f%n", balance);
    }
}
