package bankingapplication.models.accounts;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>abstract bank account class</p>
 * Has a name and balance and allows Users to view, deposit, withdraw, and transfer from the balance
 * @author Ryan Ratajczak
 */
public abstract class BankAccount
{
    private String name;
    private double balance;
    private List<String> transactions = new ArrayList<>();

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("'name' cannot be null or blank");
        }
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    private void setBalance(double balance) {
        this.balance = balance;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void addTransaction(String transaction) {
        if (transaction == null || transaction.isBlank()) {
            throw new IllegalArgumentException("'transaction' message cannot be null or blank");
        }
        this.transactions.add(transaction);
    }

    public String deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("'amount' cannot be less than or equal to 0");
        }
        setBalance(getBalance() + amount);
        return "+ $" + amount;
    }

    public String withdraw(double amount) {
        if (amount > getBalance() || amount <= 0) {
            throw new IllegalArgumentException("'amount' cannot be greater than balance or less than or equal to zero");
        }
        setBalance(getBalance() - amount);
        return "- $" + amount;
    }

    public String transfer(BankAccount sender, BankAccount receiver, double amount) {
        if (sender == null || receiver == null) {
            throw new IllegalArgumentException("Either BankAccount cannot be null");
        }
        if (amount > sender.getBalance()) {
            throw new IllegalArgumentException("'amount' cannot be greater than sender's current balance");
        }
        sender.withdraw(amount);
        receiver.deposit(amount);
        return sender.name + "->" + receiver.name;
    }
}
