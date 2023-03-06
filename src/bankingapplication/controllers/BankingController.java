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
import bankingapplication.models.accounts.SavingsAccount;
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
        for (User user : users) {
            if (!loginInfo[0].equals(user.getUserName())) {
                continue;
            }
            if (loginInfo[1].equals(user.getPassword())) {
                runUserMenu(user);
            } else {
                BankingUI.errorIncorrectPassword();
            }
        }
    }

    private void registerAccount() {
        String[] registerInfo = BankingUI.displayRegisterMenu();
        for (User value : users) {
            if (value.getUserName().equalsIgnoreCase(registerInfo[0])) {
                BankingUI.errorUsernameTaken();
                registerAccount();
            }
        }
        User user = new User(registerInfo[0], registerInfo[1]);
        fileManager.saveUser(user);
        users.add(user);
        if (BankingUI.loginNow()) {
            runUserMenu(user);
        }
    }

    private BankAccount openAccount() {
        String[] accountInfo = BankingUI.displayOpenAccount();
        if (accountInfo[0].equals("1")) {
            return new CheckingAccount(accountInfo[1], Double.parseDouble(accountInfo[2]));
        } else {
            return new SavingsAccount(accountInfo[1], Double.parseDouble(accountInfo[2]));
        }
    }

    private List<BankAccount> getCheckingOrSavingsAccounts(User user) {
        List<BankAccount> accounts = new ArrayList<>();
        String accountTypes = (BankingUI.displayAccountTypeSelection() == 1 ? "CheckingAccount" : "SavingsAccount");
        for (BankAccount account : user.getAccounts()) {
            if (account.getClass().getSimpleName().equals(accountTypes)) {
                accounts.add(account);
            }
        }
        return accounts;
    }

    private void viewAccount(User user) {
        List<BankAccount> accounts = getCheckingOrSavingsAccounts(user);
        BankAccount account = BankingUI.displayAccountSelection(accounts);
        BankingUI.displayAccount(account);
    }

    private void openAccount(User user) {
        BankAccount newAccount = openAccount();
        for (BankAccount account : user.getAccounts()) {
            String accountType = account.getClass().getSimpleName();
            String newAccountType = newAccount.getClass().getSimpleName();
            if (accountType.equals(newAccountType) && account.getName().equals(newAccount.getName())) {
                BankingUI.errorAccountAlreadyExists();
                return;
            }
        }
        user.openAccount(newAccount);
    }

    private void closeAccount(User user) {
        BankAccount accountToBeRemoved;
    }

    /**
     * Displays the main banking menu
     * @param user the User that is to have their info displayed
     */
    private void runUserMenu(User user) {
        boolean inUse = true;
        do {
            int input = BankingUI.displayUserMenu(user);
            switch (input) {
                case 1:
                    viewAccount(user);
                    break;
                case 2:
                    openAccount(user);
                    break;
                case 3:
                    break;
                case 4:
                    inUse = false;
                    break;
            }
            fileManager.saveUser(user);
        } while (inUse);
    }

    /**
     * Displays the main menu and prompts user for a response
     */
    public void runLoginMenu() {
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
