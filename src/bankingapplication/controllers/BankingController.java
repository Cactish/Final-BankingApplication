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
    //login to an existing account
    private void loginToAccount() {
        String[] loginInfo = BankingUI.displayLoginMenu();
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println(accounts.get(i));
            if(accounts.get(i).getUserName().toLowerCase().equals(loginInfo[0]) && accounts.get(i).getPassword().equals(loginInfo[1])) {
                bankApp(accounts.get(i));
            }
        }
        Console.writeLn("Username or Password is Incorrect, Please Try Again", Console.TextColor.RED);
        loginToAccount();
    }

    private void registerAccount() {
        String[] registerInfo = BankingUI.displayRegisterMenu();
        accounts.add(new User(registerInfo[0], registerInfo[1]));
        fileManager.writeData(accounts.get(accounts.size()-1).getUserName(), accounts.get(accounts.size()-1).toString(), false);
        boolean loginConfirm = Console.getBooleanInput("Account Created, Login?(yes, no)", "yes", "no", Console.TextColor.YELLOW);
        if(loginConfirm == true){
            loginToAccount();
        }else{
            mainMenu();
        }
    }

    private void bankApp(User account){
        int input = BankingUI.mainBank(account);
        switch (input){
            case 1 -> mainMenu();
            case 2 -> mainMenu();
            case 3 -> mainMenu();
            case 4 -> mainMenu();
            case 5 -> mainMenu();
        }
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
