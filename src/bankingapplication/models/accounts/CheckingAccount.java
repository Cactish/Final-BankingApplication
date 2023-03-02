package bankingapplication.models.accounts;
/**
 * <p>Extends BankAccount</p>
 * Simple checking account that doesn't differ from BankAccount
 * @see BankAccount
 */
public class CheckingAccount extends BankAccount
{
    public CheckingAccount(String name, double deposit) {
        setName(name);
        setBalance(deposit);
    }

    @Override
    public String toString() {
        String returnString = "\n---Checking Account: " + getName() + "---\n";
        returnString += "Balance: $" + getBalance();
        for (String transaction : getTransactions()) {
            returnString += transaction;
        }
        returnString += "\n";
        return returnString;
    }
}
