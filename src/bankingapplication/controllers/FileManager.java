/**
 * @author rratajczak
 * @createdOn 2/16/2023 at 10:22 AM
 * @projectName GameStuff
 * @packageName gamestuff.controllers;
 */
package bankingapplication.controllers;

import bankingapplication.models.User;
import bankingapplication.models.accounts.BankAccount;
import bankingapplication.models.accounts.CheckingAccount;
import bankingapplication.models.accounts.SavingsAccount;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager
{
    public final static String ROOT_FOLDER = "C:\\Accounts\\";

    private String getFullFilePath(String fileName) {
        return ROOT_FOLDER + fileName;
    }

    private void createRootFolder() {
        File rootFolder = new File(ROOT_FOLDER);
        if (!rootFolder.exists()) {
            try {
                rootFolder.mkdir();
            } catch (SecurityException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void createAccountFolder(String accountName) {
        File accountFolder = new File(ROOT_FOLDER + accountName);
        if(!accountFolder.exists()) {
            try {
                accountFolder.mkdir();
            } catch (SecurityException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void writeData(String path, String contents, boolean append) {
        createRootFolder();
        BufferedWriter write = null;
        try {
            write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, append)));
            write.write(contents);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                write.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String readData(String path) {
        BufferedReader read = null;
        StringBuilder content = new StringBuilder();
        try {
            read = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            while (read.ready()) {
                content.append(read.readLine()).append("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content.toString();
    }

    /**
     * Reads through 'ROOT_FOLDER' and returns a List of the names
     * @return String List of file names
     */
    public List<String> getAllFilesInFolder() {
        createRootFolder();
        File rootFolder = new File(ROOT_FOLDER);
        List<String> files = Arrays.asList(rootFolder.list());
        for (int i = 0; i < files.size(); i++) {
            String fileName = files.get(i);
            files.set(i, fileName);
        }
        return files;
    }

    /**
     * Reads all the files in a User's folder and creates a new User object
     * @return The User created with the information read
     */
    public User createUserFromInformation(String userName) {
        User user = null;
        List<BankAccount> accounts = new ArrayList<>();
        if (!getAllFilesInFolder().contains(userName)) {
            System.out.println("user not found");
            return null;
        }
        File userFolder = new File(ROOT_FOLDER + userName + "\\");
        String[] files = userFolder.list();

        // Iterate through files in the User's folder
        for (String file : files) {
            String path = userFolder + "\\" + file;
            if (file.equals("Login.txt")) {
                String[] loginInfo = readData(path).split("\r\n");
                user = new User(loginInfo[0], loginInfo[1]);
            }
            // Check if file is savings account or checking account
            if (file.charAt(0) == 'C') {
                String[] accountInfo = readData(path).split("\r\n");
                accounts.add(new CheckingAccount(accountInfo[0], Double.parseDouble(accountInfo[1])));
            }
            if (file.charAt(0) == 'S') {
                String[] accountInfo = readData(path).split("\r\n");
                accounts.add(new SavingsAccount(accountInfo[0], Double.parseDouble(accountInfo[1])));
            }
        }
        for (BankAccount account : accounts) {
            user.openAccount(account);
        }
        return user;
    }
}
