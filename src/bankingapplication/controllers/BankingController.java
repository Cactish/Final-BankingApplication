/**
 * @author rratajczak
 * @createdOn 2/27/2023 at 11:07 AM
 * @projectName Final-BankingApplication
 * @packageName bankingapplication.controllers;
 */
package bankingapplication.controllers;

import bankingapplication.models.User;
import bankingapplication.views.BankingUI;
import edu.neumont.helpers.Console;

import java.util.ArrayList;
import java.util.List;

public class BankingController
{
    private FileManager fileManager = new FileManager();
    private List<User> accounts = new ArrayList<>();

    private boolean currentlyRunning = true;

    private void loginToAccount() {
        String[] loginInfo = BankingUI.displayLoginMenu();

    }
    
    // region Create New Account
    private void registerAccount() {

    }
    // endregion
    private void bankApp(User account){
      //THE THINGY
    }

    /**
     * Displays the main menu and prompts user for a response
     */
    private void mainMenu() {
        int response = BankingUI.displayMainMenu();
        switch (response) {
            case 1 -> loginToAccount();
            case 2 -> registerAccount();
            case 3 -> currentlyRunning = false;
        }
    }

    public void run() {
        do {
            accounts = fileManager.readAccounts();
            mainMenu();
        } while (currentlyRunning);
    }
}
