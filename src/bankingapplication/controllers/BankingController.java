/**
 * @author rratajczak
 * @createdOn 2/27/2023 at 11:07 AM
 * @projectName Final-BankingApplication
 * @packageName bankingapplication.controllers;
 */
package bankingapplication.controllers;

import bankingapplication.models.User;
import bankingapplication.models.accounts.BankAccount;
import bankingapplication.models.accounts.CheckingAccount;
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
        String[] registerInfo = BankingUI.displayRegisterMenu();
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getUserName().toLowerCase().equals(registerInfo[0].toLowerCase())){
                Console.writeLn("Username is already taken, Please try again", Console.TextColor.RED);
                registerAccount();
            }
        }
        User user = new User(registerInfo[0], registerInfo[1]);
        fileManager.saveUser(user);
        users.add(user);
        boolean loginNow = Console.getBooleanInput("Account Created, Would you like to Login? (yes/no)", "yes", "no", Console.TextColor.YELLOW);
        if(loginNow == true){
            loginToAccount();
        }
    }

    private void bankApp(User user){
        int input = BankingUI.mainBank(user);
        switch (input){
            case 1 -> withdraw(user);//withdraw
            case 2 -> deposit(user);//deposit
            case 3 -> accountOpen(user);//open account
            case 4 -> accountClose(user);//close account
            case 5 -> run();
        }
    }

    private void withdraw(User user) {

    }

    private void deposit(User user) {

    }

    private void accountOpen(User user){
        String accountName = BankingUI.openAccount();
        for (int i = 0; i < user.getAccounts().size(); i++) {
            if(accountName == user.getAccounts().get(i).getName()){
                Console.writeLn("");
            }
        }
    }

    private void accountClose(User user) {

    }

    /**
     * Displays the main menu and prompts user for a response
     */
    public void run() {
        do {
            if(users.size() < fileManager.getAllFilesInFolder().size())
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
