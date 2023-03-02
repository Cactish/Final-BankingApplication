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
        deposit(deposit);
    }

    @Override
    public String toString() {
        String returnString = "---Checking Account: " + getName();

        return returnString;
    }
}
