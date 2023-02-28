/**
 * @author rratajczak
 * @createdOn 2/27/2023 at 11:07 AM
 * @projectName Final-BankingApplication
 * @packageName bankingapplication.controllers;
 */
package bankingapplication.controllers;

import bankingapplication.models.User;

import java.util.ArrayList;
import java.util.List;

public class BankingController
{
    FileManager fileManager = new FileManager();
    List<User> accounts = new ArrayList<>();

    public void run() {
        // Read all the files
        User master = new User("Master","Admin", 1000000);
    }
    
    public void readAccounts() {
        accounts = new ArrayList<>();
        for
    }
}
