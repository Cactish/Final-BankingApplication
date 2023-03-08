/**
 * @author rratajczak
 * @createdOn 2/27/2023 at 11:13 AM
 * @projectName Final-BankingApplication
 * @packageName bankingapplication.models.accounts;
 */
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
        StringBuilder returnString = new StringBuilder("\n---Checking Account: " + getName() + "---\n");
        returnString.append("Balance: $").append(getBalance()).append("\n\n---Transaction History---");
        for (int i = getTransactions().size() - 1; i >= 0; i--) {
            returnString.append("\n").append(getTransactions().get(i));
        }
        returnString.append("\n");
        return returnString.toString();
    }
}
