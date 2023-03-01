/**
 * @author rratajczak
 * @createdOn 2/27/2023 at 11:13 AM
 * @projectName Final-BankingApplication
 * @packageName bankingapplication.views;
 */
package bankingapplication.views;

import bankingapplication.models.User;
import edu.neumont.helpers.Console;

public class BankingUI
{
    public static int displayMainMenu() {
        return Console.getIntInput("""
                                           Banking Manager:
                                           1. Login
                                           2. Create Account
                                           3. Exit
                                           """, 1, 3, Console.TextColor.YELLOW);
    }
    public static int mainBank(User account){
        Console.writeLn("Welcome " + account.getUserName() + " What would you like to do?");
        return Console.getIntInput("""
                1. Withdraw
                2. Deposit
                3. Open Account
                4. Close Account
                5. Back to Menu
                """, Console.TextColor.YELLOW);
    }
    public static String[] displayLoginMenu(){
        String username = Console.getStringInput("Username: ", false, Console.TextColor.YELLOW);
        String password = Console.getStringInput("Password(Case Sensitive): ", false, Console.TextColor.YELLOW);
        return new String[]{username, password};
    }

    public static String[] displayRegisterMenu() {
        String username = Console.getStringInput("Create a username: ", false, Console.TextColor.YELLOW);
        boolean randomPassword = Console.getBooleanInput("Would you like to create a random password? (yes, no)", "yes", "no", Console.TextColor.YELLOW);
        String password = "";
        if(randomPassword == true){
            password = User.getStrongPassword(10, true);
            Console.writeLn("Your password is: " + password , Console.TextColor.RED);
        }else {
            password = Console.getStringInput("Create a password: ", false, Console.TextColor.YELLOW);
        }
        return new String[]{username, password};
    }
}
