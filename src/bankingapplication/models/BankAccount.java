/**
 * @author rratajczak
 * @createdOn 2/28/2023 at 11:35 AM
 * @projectName Final-BankingApplication
 * @packageName bankingapplication.models;
 */
package bankingapplication.models;

public abstract class BankAccount
{
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount >= 0) {
            throw new IllegalArgumentException("'amount' cannot be less than or equal to 0");
        }
        setBalance(getBalance() + amount);
    }

    public void withdraw(double amount) {
        if (amount > getBalance() || amount <= 0) {
            throw new IllegalArgumentException("'amount' cannot be greater than balance or less than or equal to zero");
        }
        setBalance(getBalance() - amount);
    }

    public void transfer(BankAccount sender, BankAccount receiver, double amount) {
        if (sender == null || receiver == null) {
            throw new IllegalArgumentException("Either BankAccount cannot be null");
        }
        if (amount > sender.getBalance()) {
            throw new IllegalArgumentException("'amount' cannot be greater than sender's current balance");
        }
        sender.withdraw(amount);
        receiver.deposit(amount);
    }
}
