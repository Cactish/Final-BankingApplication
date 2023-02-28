/**
 * @author rratajczak
 * @createdOn 2/27/2023 at 11:09 AM
 * @projectName Final-BankingApplication
 * @packageName bankingapplication.models;
 */
package bankingapplication.models;

import java.util.Random;

public class User
{
    private final int MAX_PASSWORD = 10;

    private String userName;
    private String password;
    private double balance;

    private Random random = new Random();

    public User(String userName, String password, double balance) {
        setUserName(userName);
        setPassword(password);
        setBalance(balance);
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
        if(password == null || password.isBlank() || password.length() >= 10){
            System.out.println("Invalid password, create random password?");
            boolean randomPassword = true; // Have user be prompted if they want to create a random password
            if(randomPassword = true){
                this.password = getStrongPassword(MAX_PASSWORD, true);
                System.out.println(password); //Have jframe show them the password for them to copy
            }else{
                this.password = password;
            }
        }
    }
    public double getBalance(){
        return balance;
    }
    public void setBalance(Double balance){
        if(balance == 0 || balance < 0){
            System.out.println("Invalid Balance"); //JFRAME
            return;
        }
        this.balance = balance;
    }
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
}
