/**
 * BankAccountTest.java
 *
 * Driver/test class that exercises BankAccount and CheckingAccount
 * with several test cases, including normal deposits/withdrawals and
 * an overdraft scenario.
 */
public class BankAccountTest {

    public static void main(String[] args) {

        System.out.println("=== Test Case 1: BankAccount basic operations ===");
        BankAccount account1 = new BankAccount("Jane", "Doe", 1001);
        account1.accountSummary();

        account1.deposit(500.00);
        System.out.println("\nAfter deposit of $500:");
        account1.accountSummary();

        account1.withdrawal(150.00);
        System.out.println("\nAfter withdrawal of $150:");
        account1.accountSummary();

        System.out.println("\n=== Test Case 2: BankAccount withdrawal exceeding balance ===");
        account1.withdrawal(10000.00);
        account1.accountSummary();

        System.out.println("\n=== Test Case 3: CheckingAccount normal withdrawal ===");
        CheckingAccount account2 = new CheckingAccount("John", "Smith", 2002, 1.75);
        account2.deposit(200.00);
        System.out.println("After deposit of $200:");
        account2.displayAccount();

        System.out.println("\nWithdrawing $50:");
        account2.processWithdrawal(50.00);
        account2.displayAccount();

        System.out.println("\n=== Test Case 4: CheckingAccount overdraft withdrawal ===");
        System.out.println("Withdrawing $300 (exceeds balance of $150):");
        account2.processWithdrawal(300.00);
        account2.displayAccount();

        System.out.println("\n=== Test Case 5: Setters/Getters ===");
        account2.setFirstName("Jonathan");
        account2.setLastName("Smithson");
        account2.setInterestRate(2.25);
        System.out.println("Updated name: " + account2.getFirstName() + " " + account2.getLastName());
        System.out.println("Updated interest rate: " + account2.getInterestRate() + "%");
        System.out.println("Balance via getBalance(): $" + account2.getBalance());
    }
}
