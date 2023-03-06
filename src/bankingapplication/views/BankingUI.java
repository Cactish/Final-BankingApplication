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

public class BankingUI
{
    public static int displayMainMenu() {
        Console.writeLn("Banking Manager:", Console.TextColor.YELLOW);
        return Console.getIntInput("""
                                           1. Login
                                           2. Register
                                           3. Exit""", 1, 3);
    }

    public static int displayUserMenu(User account) {
        Console.writeLn("\nWelcome " + account.getUserName() + ", what would you like to do?", Console.TextColor.YELLOW);
        return Console.getIntInput("""
                1. View an account
                2. Open a new account
                3. Close an account
                4. Log out""", 1, 4);
    }

    public static String[] displayLoginMenu() {
        String username = Console.getStringInput("\nUsername: ", false, Console.TextColor.YELLOW);
        String password = Console.getStringInput("\nPassword: ", false, Console.TextColor.YELLOW);
        return new String[]{username, password};
    }

    public static String[] displayRegisterMenu() {
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

    public static boolean loginNow() {
        return Console.getBooleanInput("\nUser created, would you like to login? (yes/no)", "yes", "no", Console.TextColor.YELLOW);
    }

    public static int displayAccountTypeSelection() {
        Console.writeLn("\nSelect an account type:", Console.TextColor.YELLOW);
        return Console.getIntInput("1. Checking\n2. Savings", 1, 2);
    }

    public static BankAccount displayAccountSelection(List<BankAccount> accounts) {
        Console.writeLn("\nSelect an account:", Console.TextColor.YELLOW);
        for (int i = 0; i < accounts.size(); i++) {
            Console.writeLn((i + 1) + ". " + accounts.get(i).getName());
        }
        int accountChoice = Console.getIntInput("", 1, accounts.size(), Console.TextColor.YELLOW);
        return accounts.get(accountChoice - 1);
    }

    public static void displayAccount(BankAccount account) {
        Console.writeLn(account.toString(), Console.TextColor.YELLOW);
    }

    public static String[] displayOpenAccount() {
        int accountType = displayAccountTypeSelection();
        String accountName = Console.getStringInput("\nEnter a name for the account:", false, Console.TextColor.YELLOW);
        int deposit;
        do {
            deposit = Console.getIntInput("\nEnter a deposit (minimum $20):", Console.TextColor.YELLOW);
        } while (deposit < 20);
        Console.writeLn((accountType == 1 ? "Checking" : "Savings") + " account '" + accountName + "' created", Console.TextColor.GREEN);
        return new String[]{"" + accountType, accountName, "" + deposit};
    }

    // region Status Messages and Error Messages
    public static void errorUsernameTaken() {
        Console.writeLn("Username is already taken, Please try again", Console.TextColor.RED);
    }
    public static void errorIncorrectPassword() {
        Console.writeLn("Incorrect password!", Console.TextColor.RED);
    }

    public static void errorAccountAlreadyExists() {
        Console.writeLn("That account already exists!", Console.TextColor.RED);
    }
    // endregion
}
