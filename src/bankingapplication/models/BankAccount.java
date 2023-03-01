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

    public void deposit() {

    }
}
