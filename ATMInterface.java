import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }
}

class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public void deposit(double amount) {
        account.deposit(amount);
    }

    public boolean withdraw(double amount) {
        return account.withdraw(amount);
    }

    public double checkBalance() {
        return account.getBalance();
    }
}

public class ATMInterface extends JFrame {
    private JTextField amountField;
    private JLabel messageLabel;
    private ATM atm;

    public ATMInterface(ATM atm) {
        super("ATM Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        this.atm = atm;

        amountField = new JTextField(10);
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton balanceButton = new JButton("Check Balance");
        messageLabel = new JLabel("");

        setLayout(new GridLayout(5, 1));
        add(new JLabel("Enter Amount:"));
        add(amountField);
        add(withdrawButton);
        add(depositButton);
        add(balanceButton);
        add(messageLabel);

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performWithdraw();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performDeposit();
            }
        });

        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });
    }

    private void performWithdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (atm.withdraw(amount)) {
                messageLabel.setText("Withdrawal successful. Remaining balance: $" + atm.checkBalance());
            } else {
                messageLabel.setText("Insufficient funds or invalid amount.");
            }
        } catch (NumberFormatException ex) {
            messageLabel.setText("Invalid amount. Please enter a numeric value.");
        }
        amountField.setText("");
    }

    private void performDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            atm.deposit(amount);
            messageLabel.setText("Deposit successful. New balance: $" + atm.checkBalance());
        } catch (NumberFormatException ex) {
            messageLabel.setText("Invalid amount. Please enter a numeric value.");
        }
        amountField.setText("");
    }

    private void checkBalance() {
        double balance = atm.checkBalance();
        messageLabel.setText("Current balance: $" + balance);
    }

    public static void main(String[] args) {
        BankAccount userAccount = new BankAccount(1000.0);
        ATM atm = new ATM(userAccount);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ATMInterface(atm).setVisible(true);
            }
        });
    }
}