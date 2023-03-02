/**
 * @author rratajczak
 * @createdOn 2/27/2023 at 11:09 AM
 * @projectName Final-BankingApplication
 * @packageName bankingapplication.models;
 */
package bankingapplication.models;

import bankingapplication.models.accounts.BankAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class User
{
    public final int MIN_PASSWORD = 10;

    private String userName;
    private String password;
    private List<BankAccount> accounts = new ArrayList<>();

    public User(String userName, String password) {
        setUserName(userName);
        setPassword(password);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if(userName == null || userName.isBlank()){
            throw new IllegalArgumentException("'userName' cannot be null or blank");
        }
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank() || password.length() < MIN_PASSWORD) {
            throw new IllegalArgumentException("'password' cannot be null, blank, or below " + MIN_PASSWORD + "Characters");
        }
        this.password = password;
    }

    public void openAccount(BankAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("'account' cannot be null");
        }
        this.accounts.add(account);
    }

    public void closeAccount(BankAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("'account' cannot be null");
        }
        accounts.remove(account);
    }

    /**
     * Generates a strong password that fulfils password requirements
     * @return Generated password
     */
    public static String getStrongPassword() {
        Random random = new Random();
        String charsToChooseFrom = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&";
        StringBuilder password = new StringBuilder(15);
        for (int i = 0; i < 15; i++) {
            password.append(charsToChooseFrom.charAt(random.nextInt(charsToChooseFrom.length())));
        }
        return password.toString();
    }

    public String toString() {
        String returnString = "";
        returnString += userName + "\r\n" + password;
        for (BankAccount account : accounts) {
            returnString += account.toString();
        }
        return returnString;
    }
}
