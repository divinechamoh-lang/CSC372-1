/**
 * CheckingAccount.java
 *
 * Subclass of BankAccount that adds an interest rate attribute and
 * allows overdraft withdrawals. Any withdrawal that exceeds the
 * available balance is permitted, but a $30 overdraft fee is applied
 * and the resulting negative balance is reported to the user.
 */
public class CheckingAccount extends BankAccount {

    private static final double OVERDRAFT_FEE = 30.0;

    private double interestRate;

    public CheckingAccount(String firstName, String lastName, int accountID, double interestRate) {
        super(firstName, lastName, accountID);
        this.interestRate = interestRate;
    }

    /**
     * Processes a withdrawal that allows overdrafts. If the withdrawal
     * amount exceeds the current balance, the account is charged a
     * $30 overdraft fee, the balance goes negative, and a message is
     * displayed noting that a fee has been assessed.
     */
    public void processWithdrawal(double amount) {
        if (amount < 0) {
            System.out.println("Withdrawal amount cannot be negative.");
            return;
        }

        double currentBalance = getBalance();

        if (amount > currentBalance) {
            double newBalance = currentBalance - amount - OVERDRAFT_FEE;
            setBalance(newBalance);
            System.out.println("Overdraft occurred! A $" + OVERDRAFT_FEE
                    + " overdraft fee has been assessed.");
            System.out.printf("New (negative) balance: $%.2f%n", newBalance);
        } else {
            setBalance(currentBalance - amount);
            System.out.printf("Withdrawal successful. New balance: $%.2f%n", getBalance());
        }
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Displays all superclass attributes plus the interest rate.
     */
    public void displayAccount() {
        accountSummary();
        System.out.printf("Interest Rate:  %.2f%%%n", interestRate);
    }
}
