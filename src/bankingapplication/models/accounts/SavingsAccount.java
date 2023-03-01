package bankingapplication.models.accounts;
/**
 * <p>Extends BankAccount</p>
 * unlike a checking account, savings accounts accrue interest and have a maximum amount of with
 * @see BankAccount
 * @author Ryan Ratajczak
 */
public class SavingsAccount extends BankAccount
{
    private double interestRate;
    private int withdrawals = 6;
    private boolean withdrawalsReset = false;

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getWithdrawals() {
        return withdrawals;
    }

    public void setWithdrawals(int withdrawals) {
        this.withdrawals = withdrawals;
    }

    public boolean isWithdrawalsReset() {
        return withdrawalsReset;
    }

    public void setWithdrawalsReset(boolean withdrawalsReset) {
        this.withdrawalsReset = withdrawalsReset;
    }
}
