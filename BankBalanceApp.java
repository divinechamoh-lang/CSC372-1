import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.OptionalDouble;

/**
 * BankBalanceApp
 * Divine Chamoh
 *
 * A simple Swing GUI that lets a user enter a starting balance, then
 * deposit or withdraw funds and view the updated balance. The final
 * balance is shown one last time before the program exits.
 *
 * Refactored version: account logic is separated from the GUI, window
 * construction is split into small methods, and input validation is
 * written once and shared.
 */
public class BankBalanceApp extends JFrame {

    // IMPROVED 7: serialVersionUID added to clear the compiler warning for a JFrame subclass.
    private static final long serialVersionUID = 1L;

    private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance();

    private final Account account;
    private final JLabel balanceLabel;
    private final JTextField amountField = new JTextField(10);

    // IMPROVED 1: Account logic separated from the GUI (model vs. view).
    /** Holds the balance and the rules for changing it; no Swing code. */
    static final class Account {
        private double balance;

        Account(double startingBalance) {
            this.balance = startingBalance;
        }

        double getBalance() {
            return balance;
        }

        void deposit(double amount) {
            balance += amount;
        }

        /** @return true if the withdrawal was made, false if funds were insufficient */
        boolean withdraw(double amount) {
            if (amount > balance) {
                return false;
            }
            balance -= amount;
            return true;
        }
    }

    // IMPROVED 3: Constructor now only coordinates; each step is its own method.
    public BankBalanceApp(double startingBalance) {
        super("Bank Balance Application");
        account = new Account(startingBalance);
        balanceLabel = createBalanceLabel();
        configureFrame();
        add(buildMainPanel());
    }

    // ---------- Input validation ----------

    // IMPROVED 2: One shared validation method replaces two duplicated
    // try/catch blocks, and now also rejects NaN and Infinity.
    static OptionalDouble parseNonNegative(String text) {
        try {
            double value = Double.parseDouble(text.trim());
            if (Double.isFinite(value) && value >= 0) {
                return OptionalDouble.of(value);
            }
        } catch (NumberFormatException ex) {
            // falls through to empty result
        }
        return OptionalDouble.empty();
    }

    // ---------- Window construction ----------

    // IMPROVED 3: Closing the window with the X button now shows the final
    // balance and exits (previously the X button did nothing).
    private void configureFrame() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });
        setSize(360, 200);
        setLocationRelativeTo(null);
    }

    // IMPROVED 3: Small single-purpose builder methods.
    private JLabel createBalanceLabel() {
        JLabel label = new JLabel(formatBalance(), SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        return label;
    }

    // IMPROVED 3
    private JPanel buildMainPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(balanceLabel);
        panel.add(buildInputPanel());
        panel.add(buildButtonPanel());
        return panel;
    }

    // IMPROVED 3
    private JPanel buildInputPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        return panel;
    }

    // IMPROVED 3
    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(createButton("Deposit", e -> handleDeposit()));
        panel.add(createButton("Withdraw", e -> handleWithdraw()));
        panel.add(createButton("Exit", e -> exitApplication()));
        return panel;
    }

    // IMPROVED 3
    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        return button;
    }

    // ---------- Event handling ----------

    // IMPROVED 4: Each button has its own handler method (lambdas) instead of
    // one actionPerformed method comparing event sources with ==.
    private void handleDeposit() {
        readAmount().ifPresent(amount -> {
            account.deposit(amount);
            refreshDisplay();
        });
    }

    // IMPROVED 4
    private void handleWithdraw() {
        readAmount().ifPresent(amount -> {
            if (account.withdraw(amount)) {
                refreshDisplay();
            } else {
                showMessage(this, "Insufficient funds for this withdrawal.",
                        "Transaction Denied", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    // IMPROVED 4
    private void exitApplication() {
        showMessage(this, "Final balance: " + CURRENCY.format(account.getBalance()),
                "Goodbye", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    // ---------- Helpers ----------

    // IMPROVED 2: Reads the amount field, showing the error dialog if invalid.
    private OptionalDouble readAmount() {
        OptionalDouble amount = parseNonNegative(amountField.getText());
        if (amount.isEmpty()) {
            showMessage(this, "Please enter a valid, non-negative amount.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
        return amount;
    }

    private String formatBalance() {
        return "Balance: " + CURRENCY.format(account.getBalance());
    }

    private void refreshDisplay() {
        balanceLabel.setText(formatBalance());
        amountField.setText("");
    }

    // IMPROVED 5: One place for message dialogs instead of repeating
    // JOptionPane.showMessageDialog(...) in every method.
    private static void showMessage(Component parent, String message, String title, int messageType) {
        JOptionPane.showMessageDialog(parent, message, title, messageType);
    }

    // ---------- Program start ----------

    // IMPROVED 6: Startup dialog loop moved out of main() into its own method.
    private static OptionalDouble promptForStartingBalance() {
        while (true) {
            String input = JOptionPane.showInputDialog(null,
                    "Enter your starting bank balance:", "Account Setup", JOptionPane.PLAIN_MESSAGE);
            if (input == null) {
                return OptionalDouble.empty();
            }
            OptionalDouble balance = parseNonNegative(input);
            if (balance.isPresent()) {
                return balance;
            }
            showMessage(null, "Please enter a valid, non-negative number.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    // IMPROVED 6
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> promptForStartingBalance().ifPresentOrElse(
                startingBalance -> new BankBalanceApp(startingBalance).setVisible(true),
                () -> System.exit(0)));
    }
}
