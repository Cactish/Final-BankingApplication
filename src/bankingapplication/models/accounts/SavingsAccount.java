/**
 * @author rratajczak
 * @createdOn 2/27/2023 at 11:13 AM
 * @projectName Final-BankingApplication
 * @packageName bankingapplication.models.accounts;
 */
package bankingapplication.models.accounts;

import edu.neumont.helpers.FormatHelpers;

import java.time.LocalDate;

/**
 * <p>Extends BankAccount</p>
 * unlike a checking account, savings accounts accrue interest and have a maximum amount of withdrawals
 * @see BankAccount
 */
public class SavingsAccount extends BankAccount
{
    public final double INTEREST_RATE = 0.015;
    public final int MAX_WITHDRAWALS = 6;

    public SavingsAccount(String name, double deposit) {
        setName(name);
        setBalance(deposit);
        setInterestRate(INTEREST_RATE);
        setNextPayday(LocalDate.now().plusDays(30));
        setWithdrawals(MAX_WITHDRAWALS);
        setNextWithdrawalsReset(LocalDate.now().plusDays(30));
    }

    // This one is used exclusively when reading the SavingsAccount from the file
    public SavingsAccount(String[] accountInfo) {
        setName(accountInfo[0]);
        setBalance(Double.parseDouble(accountInfo[1]));
        setInterestRate(Double.parseDouble(accountInfo[2]));
        setNextPayday(LocalDate.parse(accountInfo[3]));
        setInterestPaid(Boolean.parseBoolean(accountInfo[4]));
        setWithdrawals(Integer.parseInt(accountInfo[5]));
        setNextWithdrawalsReset(LocalDate.parse(accountInfo[6]));
        setWithdrawalsReset(Boolean.parseBoolean(accountInfo[7]));
    }

    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder("\n---Savings Account: " + getName() + "---\n");
        returnString.append("Balance: $").append(getBalance()).append("\n")
                .append("Interest Rate: ").append(FormatHelpers.toPercentageString(getInterestRate())).append("\n")
                .append("Next Payday: ").append(getNextPayday()).append("\n")
                .append("Withdrawals Remaining: ").append(getWithdrawals()).append("\n")
                .append("Next withdrawals reset: ").append(getNextWithdrawalsReset()).append("\n\n")
                .append("---Transaction History---");
        for (int i = getTransactions().size() - 1; i >= 0; i--) {
            returnString.append("\n").append(getTransactions().get(i));
        }
        returnString.append("\n");
        return returnString.toString();
    }
}
