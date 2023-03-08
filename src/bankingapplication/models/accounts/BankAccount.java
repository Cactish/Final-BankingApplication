/**
 * @author rratajczak
 * @createdOn 2/27/2023 at 11:13 AM
 * @projectName Final-BankingApplication
 * @packageName bankingapplication.models.accounts;
 */
package bankingapplication.models.accounts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>abstract bank account class</p>
 * Has a name and balance and allows Users to view, deposit, withdraw, and transfer from the balance
 */
public abstract class BankAccount
{
    private String name;
    private double balance;

    private double interestRate;
    private LocalDate nextPayday;
    private boolean interestPaid = false;

    private int withdrawals = 6;
    private LocalDate nextWithdrawalsReset;
    private boolean withdrawalsReset = false;
    private final List<String> transactions = new ArrayList<>();

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

    protected void setBalance(double balance) {
        this.balance = Math.round(balance * 100.0) / 100.0;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDate getNextPayday() {
        return nextPayday;
    }

    public void setNextPayday(LocalDate nextPayday) {
        if (nextPayday == null) {
            throw new IllegalArgumentException("'nextPayday' cannot be null!");
        }
        this.nextPayday = nextPayday;
    }

    public boolean isInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(boolean interestPaid) {
        this.interestPaid = interestPaid;
    }

    public int getWithdrawals() {
        return withdrawals;
    }

    public void setWithdrawals(int withdrawals) {
        this.withdrawals = withdrawals;
    }

    public LocalDate getNextWithdrawalsReset() {
        return nextWithdrawalsReset;
    }

    public void setNextWithdrawalsReset(LocalDate nextWithdrawalsReset) {
        if (nextWithdrawalsReset == null) {
            throw new IllegalArgumentException("'nextWithdrawalsReset' cannot be null");
        }
        this.nextWithdrawalsReset = nextWithdrawalsReset;
    }

    public boolean isWithdrawalsReset() {
        return withdrawalsReset;
    }

    public void setWithdrawalsReset(boolean withdrawalsReset) {
        this.withdrawalsReset = withdrawalsReset;
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

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("'amount' cannot be less than or equal to 0");
        }
        setBalance(getBalance() + amount);
        addTransaction("| + $" + amount + " | DEPOSIT | " + LocalDate.now() + " |");
    }

    public void deposit(double amount, boolean silent) {
        if (amount <= 0) {
            throw new IllegalArgumentException("'amount' cannot be less than or equal to 0");
        }
        setBalance(getBalance() + amount);
        if (!silent) {
            addTransaction("| + $" + amount + " | DEPOSIT | " + LocalDate.now() + " |");
        }
    }

    public void withdraw(double amount) {
        if (amount > getBalance() || amount <= 0) {
            throw new IllegalArgumentException("'amount' cannot be greater than balance or less than or equal to zero");
        }
        setBalance(getBalance() - amount);
        addTransaction("| - $" + amount + " | WITHDRAW | " + LocalDate.now() + " |");
    }

    public void withdraw(double amount, boolean silent) {
        if (amount > getBalance() || amount <= 0) {
            throw new IllegalArgumentException("'amount' cannot be greater than balance or less than or equal to zero");
        }
        setBalance(getBalance() - amount);
        if (!silent) {
            addTransaction("| - $" + amount + " | WITHDRAW | " + LocalDate.now() + " |");
        }
    }
}
