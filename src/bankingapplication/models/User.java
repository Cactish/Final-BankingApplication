/**
 * @author rratajczak
 * @createdOn 2/27/2023 at 11:09 AM
 * @projectName Final-BankingApplication
 * @packageName bankingapplication.models;
 */
package bankingapplication.models;

import java.util.List;
import java.util.Random;

public class User
{
    public final int MIN_PASSWORD = 10;

    private String userName;
    private String password;
    private List<BankAccount> accounts;

    private Random random = new Random();

    public User(String userName, String password){
        setUserName(userName);
        setPassword(password);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
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

    public void createAccount() {

    }

    /**
     * Generates a strong password that fulfils password requirements
     * @return Generated password
     */
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

    public String toString(){
        return userName + "\r\n" + password;
    }
}
