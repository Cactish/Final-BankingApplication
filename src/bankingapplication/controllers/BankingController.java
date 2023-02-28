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

    /**
     * Reads all the files in "C:/accounts" and creates a new User object that is then added to accounts array
     */
    public void readAccounts() {
        accounts = new ArrayList<>();
        for (String fileName : fileManager.getAllFilesInFolder()) {
            String[] accountInfo = fileManager.readData(fileName).split("\r\n");
            accounts.add(new User(fileName, accountInfo[0], Double.parseDouble(accountInfo[1])));
        }
    }
    
    private void loginToAccount() {
        for (int i = 0; i < accounts.size(); i++) {
            String[] account = BankingUI.displayLoginMenu();
            if(account[1] == accounts.get(i).getUserName()){
                if(account[2] != accounts.get(i).getPassword()){
                    Console.writeLn("Wrong Password, Try Again", Console.TextColor.RED);
                    loginToAccount();
                }else{
                    //run the mobile bank app with the given account
                    bankApp(accounts.get(i));
                }
            }
            Console.writeLn("Invalid Username, Try Again", Console.TextColor.RED);
            loginToAccount();
        }
    }
    
    // region Create New Account
    public static String getStrongPassword(int length, boolean includeSymbols) {
        Random random = new Random();
        if (length == 0) {
            throw new IllegalArgumentException("Length cannot be 0");
        }

        String charsToChooseFrom = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        if (includeSymbols) {
            charsToChooseFrom += "!#$%&<>@";
        }

        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            password.append(charsToChooseFrom.charAt(random.nextInt(charsToChooseFrom.length())));
        }
        return password.toString();
    }

    private void registerAccount() {
        
    }
    // endregion
    public void bankApp(User account){
      //THE THINGY
    }
    public void run() {
        boolean running = true;
        do {
            readAccounts();
            int response = BankingUI.displayMainMenu();
            switch (response) {
                case 1 -> loginToAccount();
                case 2 -> registerAccount();
                case 3 -> running = false;
            } 
        } while (running);
    }
}
