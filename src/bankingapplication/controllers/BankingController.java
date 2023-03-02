/**
 * @author rratajczak
 * @createdOn 2/27/2023 at 11:07 AM
 * @projectName Final-BankingApplication
 * @packageName bankingapplication.controllers;
 */
package bankingapplication.controllers;

import bankingapplication.models.User;
import bankingapplication.views.BankingUI;

import java.util.ArrayList;
import java.util.List;

public class BankingController
{
    private final FileManager fileManager = new FileManager();
    private final List<User> users = new ArrayList<>();

    private boolean currentlyRunning = true;

    private void loginToAccount() {
        String[] loginInfo = BankingUI.displayLoginMenu();
    }

    private void registerAccount() {
        String[] registrationInfo = BankingUI.displayRegisterMenu();
    }
    private void bankApp(User account) {
      // THE THINGY it is the thingy that does the thing when you do the thing, and it does the thing
    }

    /**
     * Displays the main menu and prompts user for a response
     */
    public void run() {
        do {
            for (String userFolder : fileManager.getAllFilesInFolder()) {
                users.add(fileManager.createUserFromUserFolder(userFolder));
            }
            int response = BankingUI.displayMainMenu();
            switch (response) {
                case 1 -> loginToAccount();
                case 2 -> registerAccount();
                case 3 -> currentlyRunning = false;
            }
        } while (currentlyRunning);
    }
}
