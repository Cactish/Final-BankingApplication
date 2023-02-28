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
    private final int MAX_PASSWORD = 10;

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
        if (password == null || password.isBlank() || password.length() > MAX_PASSWORD) {
            throw new IllegalArgumentException("NO"); //Going to change this later, maybe
        }
        this.password = password;
    }

    public String toString(){
        return userName + "\r\n" + password;
    }
}
