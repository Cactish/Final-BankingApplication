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
        loginToAccount();
    }
    private void registerAccount() {
        String[] registerInfo = BankingUI.displayRegisterMenu();
        users.add(new User(registerInfo[0], registerInfo[1]));
        fileManager.writeData(users.get(users.size()-1).getUserName(), users.get(users.size()-1).toString(), false);
        boolean loginConfirm = Console.getBooleanInput("Account Created, Login?(yes, no)", "yes", "no", Console.TextColor.YELLOW);
        if(loginConfirm == true){
            loginToAccount();
        }
    }

    private void bankApp(User user){
        int input = BankingUI.mainBank(user);
        switch (input){
            case 1 -> mainMenu();
            case 2 -> mainMenu();
            case 3 -> mainMenu();
            case 4 -> mainMenu();
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
