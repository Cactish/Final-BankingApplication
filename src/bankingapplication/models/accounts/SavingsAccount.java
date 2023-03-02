package bankingapplication.models.accounts;
/**
 * <p>Extends BankAccount</p>
 * unlike a checking account, savings accounts accrue interest and have a maximum amount of with
 * @see BankAccount
 * @author Ryan Ratajczak
 */
public class SavingsAccount extends BankAccount
{
    public final double INTEREST_RATE = 1.5;
    public final int MAX_WITHDRAWALS = 6;

    public SavingsAccount(String name, double deposit) {
        setName(name);
        setBalance(deposit);
        setInterestRate(INTEREST_RATE);
        setWithdrawals(MAX_WITHDRAWALS);
    }

    // This one is used exclusively when reading the SavingsAccount from the file
    public SavingsAccount(String name, double balance, int withdrawals) {
        setName(name);
        setBalance(balance);
        setInterestRate(INTEREST_RATE);
        setWithdrawals(withdrawals);
    }

    @Override
    public String toString() {
        String returnString = "\n---Savings Account: " + getName() + "---\n";
        returnString += "Balance: $" + getBalance() + "\n" +
                        "Interest Rate: " + getInterestRate() + "%\n" +
                        "Withdrawals Remaining: " + getWithdrawals();
        for (String transaction : getTransactions()) {
            returnString += transaction;
        }
        returnString += "\n";
        return returnString;
    }
}
