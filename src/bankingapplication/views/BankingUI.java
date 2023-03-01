/**
 * @author rratajczak
 * @createdOn 2/27/2023 at 11:13 AM
 * @projectName Final-BankingApplication
 * @packageName bankingapplication.views;
 */
package bankingapplication.views;

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
    public static String[] displayLoginMenu(){
        String username = Console.getStringInput("Username: ", false, Console.TextColor.YELLOW);
        String password = Console.getStringInput("Password: ", false, Console.TextColor.YELLOW);
        return new String[]{username, password};
    }

    public static String[] displayRegisterMenu() {
        String username = Console.getStringInput("Create a username: ", false, Console.TextColor.YELLOW);
        String password = Console.getStringInput("Create a password: ", false, Console.TextColor.YELLOW);
        return new String[]{username, password};
    }
}
