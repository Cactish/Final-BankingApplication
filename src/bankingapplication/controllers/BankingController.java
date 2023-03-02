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
    private final FileManager fileManager = new FileManager();
    private final List<User> users = new ArrayList<>();

    private boolean currentlyRunning = true;

    private void loginToAccount() {
        String[] loginInfo = BankingUI.displayLoginMenu();
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getUserName().toLowerCase().equals(loginInfo[0]) && users.get(i).getPassword().equals(loginInfo[1])) {
                bankApp(users.get(i));
            }
        }
            Console.writeLn("Username or Password is Incorrect, Please Try Again", Console.TextColor.RED);
    }
    private void registerAccount() {

    }

    private void bankApp(User user){
        int input = BankingUI.mainBank(user);
        switch (input){
            case 1 -> run();
            case 2 -> run();
            case 3 -> run();
            case 4 -> run();
            case 5 -> run();
        }
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
