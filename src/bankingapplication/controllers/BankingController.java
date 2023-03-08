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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Main controller for the BankingApplication</p>
 * <p>Utilizes {@link BankingUI} for its UI</p>
 * <em>side note: I dont remember what I wrote for most of these comments, so they may not be perfect</em>
 * @see User
 * @see BankAccount
 * @see BankingUI
 */
public class BankingController
{
    private final FileManager fileManager = new FileManager();
    private final List<User> users = new ArrayList<>();

    /**
     * Verifies that a String does not contain certain characters
     * <p>(because windows doesn't like certain chars in their file names)</p>
     * @param name String to be checked for bad characters
     * @return true if name contains bad characters; false if the name is fine
     */
    private boolean checkForNameIssues(String name) {
        String notAllowedCharacters= "\\/:*?\"<>|";
        for (int i = 0; i < notAllowedCharacters.length(); i++) {
            if (name.indexOf(notAllowedCharacters.charAt(i)) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Iterates through users accounts and returns all the Checking or Savings accounts
     * @param user user to iterate though
     * @return BankAccount list of all the Checking or Savings accounts
     */
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

    /**
     * Returns the BankAccount type as a simple word
     * @param account account to get type of
     * @return "Checking" if class is "CheckingAccount"; "Savings" if class is "SavingsAccount"
     */
    private String getAccountType(BankAccount account) {
        String accountType = account.getClass().getSimpleName();
        if (accountType.equals("CheckingAccount")) {
            return "Checking";
        } else {
            return "Savings";
        }
    }

    // region Login Menu
    /**
     * Prompts the user to log in with a username and password
     */
    private void loginToAccount() {
        if (users.size() == 0) {
            BankingUI.errorNoOtherUsersExist();
            return;
        }
        String[] loginInfo = BankingUI.displayLogin();
        for (User user : users) {
            // If the User is not found in users
            if (user.getUserName().equals(users.get(users.size()-1).getUserName()) && !loginInfo[0].equals(user.getUserName())) {
                BankingUI.errorUserDoesNotExist();
            }
            // If the username is not the same
            if (!loginInfo[0].equals(user.getUserName())) {
                continue;
            }
            // If the password is incorrect, go to the next iteration, otherwise login to said user
            if (!loginInfo[1].equals(user.getPassword())) {
                BankingUI.errorIncorrectPassword();
            } else {
                runMainMenu(user);
                break;
            }
        }
    }

    /**
     * Prompts the user to create a login with a username and password.
     */
    private void registerAccount() {
        String[] registerInfo = BankingUI.displayRegister();
        if (checkForNameIssues(registerInfo[0])) {
            BankingUI.errorInvalidCharacters();
            return;
        }
        for (User value : users) {
            if (value.getUserName().equalsIgnoreCase(registerInfo[0])) {
                BankingUI.errorUsernameTaken();
                registerAccount();
            }
        }
        User user = new User(registerInfo[0], registerInfo[1]);
        fileManager.saveUser(user);
        users.add(user);
        if (BankingUI.statusLoginNow()) {
            runMainMenu(user);
        }
    }

    /**
     * Displays the main menu and prompts user for a response
     */
    public void runLoginMenu() {
        boolean currentlyRunning = true;
        do {
            if(users.size() < fileManager.getAllFilesInFolder().size())
                for (String userFolder : fileManager.getAllFilesInFolder()) {
                    users.add(fileManager.createUserFromUserFolder(userFolder));
                }
            switch (BankingUI.displayLoginMenu()) {
                case 1 -> loginToAccount();
                case 2 -> registerAccount();
                case 3 -> currentlyRunning = false;
            }
        } while (currentlyRunning);
    }
    // endregion

    // region Main Menu
    /**
     * Adds a user defined amount to the account's balance
     * @param account BankAccount to be added to
     */
    private void deposit(BankAccount account) {
        double amount = BankingUI.displayEnterAmount("deposit");
        account.deposit(amount);
    }

    /**
     * Subtracts a user defined amount from the account's balance
     * @param account BankAccount to be withdrawn from
     */
    private void withdraw(BankAccount account) {
        double amount = BankingUI.displayEnterAmount("withdraw");
        if (getAccountType(account).equals("Savings") && account.getWithdrawals() == 0) {
            BankingUI.errorNoMoreWithdrawals();
            return;
        }
        if (getAccountType(account).equals("Savings") && account.getWithdrawals() != 0) {
            account.setWithdrawals(account.getWithdrawals() - 1);
        }
        if (amount > account.getBalance()) {
            BankingUI.errorInsufficientFunds();
            return;
        }
        account.withdraw(amount);
    }

    /**
     * Sends an amount to another BankAccount (receiver account gets prompted in the method)
     * @param user User to have their account displayed
     * @param sender BankAccount that is sending money
     */
    private void transfer(User user, BankAccount sender) {
        User recipientUser = null;
        List<BankAccount> accounts;
        if (BankingUI.displayTransferMenu() == 2) {
            List<User> tempUsers = users;
            tempUsers.remove(user);
            if (users.size() == 1) {
                BankingUI.errorNoOtherUsersExist();
                return;
            }
            recipientUser = BankingUI.displayUserList(tempUsers);
        }
        if (recipientUser != null) {
            accounts = getCheckingOrSavingsAccounts(recipientUser);
        } else {
            accounts = getCheckingOrSavingsAccounts(user);
            accounts.remove(sender);
        }
        if (accounts.isEmpty()) {
            BankingUI.errorNoAccountsExist();
            return;
        }
        BankAccount recipientAccount = BankingUI.displayAccountSelection(accounts);
        double amount = BankingUI.displayEnterAmount("transfer");
        user.transfer(sender, recipientAccount, amount);
        if (recipientUser != null) {
            fileManager.saveUser(recipientUser);
        }
    }

    /**
     * Displays a BankAccount's information and prompts the user for input
     * @param user User that the account is located on
     */
    private void viewAccount(User user) {
        List<BankAccount> accounts = getCheckingOrSavingsAccounts(user);
        if (accounts.isEmpty()) {
            BankingUI.errorNoAccountsExist();
            return;
        }
        BankAccount account = BankingUI.displayAccountSelection(accounts);
        boolean currentlyRunning = true;
        do {
            BankingUI.displayAccount(account);
            switch (BankingUI.displayAccountMenu()) {
                case 1 -> deposit(account);
                case 2 -> withdraw(account);
                case 3 -> transfer(user, account);
                case 4 -> currentlyRunning = false;
            }
            fileManager.saveUser(user);
        } while (currentlyRunning);
    }

    /**
     * Prompts the end user to create a new BankAccount (Savings or Checking) with a name and a balance
     * @param user User to add the new BankAccount to
     */
    private void openAccount(User user) {
        BankAccount newAccount;
        String[] accountInfo = BankingUI.displayOpenAccount();
        if (checkForNameIssues(accountInfo[1])) {
            BankingUI.errorInvalidCharacters();
            return;
        }
        if (accountInfo[0].equals("1")) {
            newAccount = new CheckingAccount(accountInfo[1], Double.parseDouble(accountInfo[2]));
        } else {
            newAccount = new SavingsAccount(accountInfo[1], Double.parseDouble(accountInfo[2]));
        }
        for (BankAccount account : user.getAccounts()) {
            String accountType = getAccountType(account);
            String newAccountType = getAccountType(newAccount);
            if (accountType.equals(newAccountType) && account.getName().equals(newAccount.getName())) {
                BankingUI.errorAccountAlreadyExists();
                return;
            }
        }
        BankingUI.statusAccountSuccessfullyCreated(getAccountType(newAccount), newAccount.getName());
        user.openAccount(newAccount);
    }

    /**
     * Lists out the User's accounts and closes the selected one
     * @param user User to have their accounts displayed
     */
    private void closeAccount(User user) {
        List<BankAccount> accounts = getCheckingOrSavingsAccounts(user);
        if (accounts.isEmpty()) {
            BankingUI.errorNoAccountsExist();
            return;
        }
        BankAccount account = BankingUI.displayAccountSelection(accounts);
        String accountType = account.getClass().getSimpleName();
        if (accountType.equals("CheckingAccount")) {
            accountType = "Checking";
        } else {
            accountType = "Savings";
        }
        if (fileManager.deleteFile(user.getUserName() + "\\" + accountType + account.getName() + ".txt")) {
            BankingUI.statusAccountSuccessfullyDeleted(accountType, account.getName());
            user.closeAccount(account);
            fileManager.saveUser(user);
        }
    }

    /**
     * Displays the main banking menu
     * @param user the User that is to have their info displayed
     */
    private void runMainMenu(User user) {
        boolean currentlyRunning = true;
        for (BankAccount account : user.getAccounts()) {
            if (!account.getClass().getSimpleName().equals("SavingsAccount")) {
                continue;
            }
            // Add interest to the account
            if (LocalDate.now().toString().equals(account.getNextPayday().toString()) && !account.isInterestPaid()) {
                account.deposit(account.getBalance() * account.getInterestRate());
                account.setNextPayday(LocalDate.now().plusDays(30));
            }
            // Reset withdraw limit
            if (LocalDate.now().toString().equals(account.getNextWithdrawalsReset().toString()) && !account.isWithdrawalsReset()) {
                account.setWithdrawalsReset(true);
                account.setNextWithdrawalsReset(LocalDate.now().plusDays(30));
            }
        }
        do {
            switch (BankingUI.displayUserMenu(user)) {
                case 1 -> viewAccount(user);
                case 2 -> openAccount(user);
                case 3 -> closeAccount(user);
                case 4 -> currentlyRunning = false;
            }
            fileManager.saveUser(user);
        } while (currentlyRunning);
    }
    // endregion
}
