/**
 * @author rratajczak
 * @createdOn 2/27/2023 at 11:13 AM
 * @projectName Final-BankingApplication
 * @packageName bankingapplication.views;
 */
package bankingapplication.views;

import bankingapplication.models.User;
import bankingapplication.models.accounts.BankAccount;
import edu.neumont.helpers.Console;

import java.util.List;

/**
 * Contains static methods for displaying information from BankingController
 * @see bankingapplication.controllers.BankingController
 */
public class BankingUI
{
    /**
     * Displays the startup menu for BankingController
     * @return Integer number determining menu choice
     */
    public static int displayLoginMenu() {
        Console.writeLn("\nBanking Manager:", Console.TextColor.YELLOW);
        return Console.getIntInput("""
                                           1. Login
                                           2. Register
                                           3. Exit""", 1, 3);
    }

    /**
     * Displays a menu for the end user to log into
     * @return String[] where the first index is the username and the second index is the password
     */
    public static String[] displayLogin() {
        String username = Console.getStringInput("\nUsername: ", false, Console.TextColor.YELLOW);
        String password = Console.getStringInput("\nPassword: ", false, Console.TextColor.YELLOW);
        return new String[]{username, password};
    }

    /**
     * Displays a menu for the end user to create a new User
     * @return String[] where the first index is the username and the second index is the password
     */
    public static String[] displayRegister() {
        String username = Console.getStringInput("\nCreate a username: ", false, Console.TextColor.YELLOW);
        boolean randomPassword = Console.getBooleanInput("\nWould you like to create a random password? (yes, no)",
                "yes", "no", Console.TextColor.YELLOW);
        String password;
        if (randomPassword) {
            password = User.getStrongPassword();
            Console.writeLn("\nYour password is: " + password , Console.TextColor.GREEN);
        } else {
            do {
                password = Console.getStringInput("\nEnter Password: ", false, Console.TextColor.YELLOW);
                if (password.length() < 10) {
                    password = "";
                    Console.writeLn("Password must be longer than " + User.MIN_PASSWORD + " characters long", Console.TextColor.RED);
                }
            } while (password.isBlank());
        }
        return new String[]{username, password};
    }

    /**
     * Displays a menu for the currently logged-in user
     * @param account User that is currently logged-in
     * @return Integer number determining menu choice
     */
    public static int displayUserMenu(User account) {
        Console.writeLn("\nWelcome " + account.getUserName() + ", what would you like to do?", Console.TextColor.YELLOW);
        return Console.getIntInput("""
                1. View an account
                2. Open a new account
                3. Close an account
                4. Log out""", 1, 4);
    }

    /**
     * Displays a menu for selecting BankAccount type
     * @return 1 for CheckingAccount; 2 for SavingsAccount
     */
    public static int displayAccountTypeSelection() {
        Console.writeLn("\nSelect an account type:", Console.TextColor.YELLOW);
        return Console.getIntInput("1. Checking\n2. Savings", 1, 2);
    }

    /**
     * Displays a menu for the end user to select an account
     * @param accounts List of the users accounts
     * @return the BankAccount that was selected
     */
    public static BankAccount displayAccountSelection(List<BankAccount> accounts) {
        Console.writeLn("\nSelect an account:", Console.TextColor.YELLOW);
        for (int i = 0; i < accounts.size() - 1; i++) {
            Console.writeLn((i + 1) + ". " + accounts.get(i).getName());
        }
        int accountChoice = Console.getIntInput((accounts.size() + ". ") +
                        accounts.get(accounts.size() - 1).getName(), 1, accounts.size());
        return accounts.get(accountChoice - 1);
    }

    /**
     * Displays the BankAccount's info using its toString()
     * @param account the BankAccount to be displayed
     */
    public static void displayAccount(BankAccount account) {
        Console.writeLn("\n--------------------------------------------------", Console.TextColor.BLUE);
        Console.writeLn(account.toString());
        Console.writeLn("--------------------------------------------------", Console.TextColor.BLUE);
    }

    /**
     * Displays an option menu for a BankAccount
     * @return Integer number determining menu choice
     */
    public static int displayAccountMenu() {
        Console.writeLn("Select an option:", Console.TextColor.YELLOW);
        return Console.getIntInput("1. Deposit\n2. Withdraw\n3. Transfer\n4. Back to main menu", 1, 4);
    }

    /**
     * Displays a prompt for the end user to enter an amount (double)
     * @param action used to specify what the amount is for i.e(deposit, withdraw, transfer)
     * @return the amount
     */
    public static double displayEnterAmount(String action) {
        return Console.getDoubleInput("\nEnter amount to " + action + ":", Console.TextColor.YELLOW);
    }

    /**
     * Displays a menu for the end user to create a new BankAccount to be appended to a User
     * @return String[] where index 0 is the account type; index 1 is the name; index 2 is the deposit
     */
    public static String[] displayOpenAccount() {
        int accountType = displayAccountTypeSelection();
        String accountName = Console.getStringInput("\nEnter a name for the account:", false, Console.TextColor.YELLOW);
        double deposit;
        do {
            deposit = Console.getDoubleInput("\nEnter a deposit (minimum $20):", Console.TextColor.YELLOW);
        } while (deposit < 20);
        return new String[]{"" + accountType, accountName, "" + deposit};
    }

    /**
     * Displays a menu for selecting transfer types
     * @return 1 if its between a single User's account; 2 if the transfer is to another User
     */
    public static int displayTransferMenu() {
        Console.writeLn("\nWhere are you transferring to?", Console.TextColor.YELLOW);
        return Console.getIntInput("1. Between my own accounts\n2. To another user");
    }

    /**
     * Displays a list of all the Users in the application
     * @param users list of Users to be displayed
     * @return the selected User
     */
    public static User displayUserList(List<User> users) {
        Console.writeLn("\nWhat user would you like to transfer to?", Console.TextColor.YELLOW);
        for (int i = 0; i < users.size() - 1; i++) {
            Console.writeLn((i + 1) + ". " + users.get(i).getUserName());
        }
        int accountChoice = Console.getIntInput((users.size() + ". ") +
                users.get(users.size() - 1).getUserName(), 1, users.size());
        return users.get(accountChoice - 1);
    }

    // region Status Messages and Error Messages
    public static void errorInvalidCharacters() {
        Console.writeLn("\nName contains invalid characters! (\\ / : * ? < > |)", Console.TextColor.RED);
    }
    public static void errorUsernameTaken() {
        Console.writeLn("\nUsername is already taken, Please try again", Console.TextColor.RED);
    }

    public static void errorUserDoesNotExist() {
        Console.writeLn("\nUser does not exist!", Console.TextColor.RED);
    }

    public static boolean statusLoginNow() {
        return Console.getBooleanInput("\nUser created, would you like to login? (yes/no)", "yes", "no", Console.TextColor.YELLOW);
    }

    public static void errorIncorrectPassword() {
        Console.writeLn("\nIncorrect password!", Console.TextColor.RED);
    }

    public static void errorNoAccountsExist() {
        Console.writeLn("\nThere are no accounts of that type!", Console.TextColor.RED);
    }

    public static void errorAccountAlreadyExists() {
        Console.writeLn("\nThat account already exists!", Console.TextColor.RED);
    }

    public static void errorInsufficientFunds() {
        Console.writeLn("\nInsufficient funds!", Console.TextColor.RED);
    }

    public static void errorNoMoreWithdrawals() {
        Console.writeLn("\nThere are no more withdrawals remaining for this account!", Console.TextColor.RED);
    }

    public static void errorNoOtherUsersExist() {
        Console.writeLn("\nThere are no users!", Console.TextColor.RED);
    }

    public static void statusAccountSuccessfullyCreated(String accountType, String accountName) {
        Console.writeLn( "\n" + accountType + " account '" + accountName + "' created", Console.TextColor.WHITE);
    }

    public static void statusAccountSuccessfullyDeleted(String accountType, String accountName) {
        Console.writeLn("\n" + accountType + " account '" + accountName + "' successfully deleted.", Console.TextColor.WHITE);
    }
    // endregion
}
