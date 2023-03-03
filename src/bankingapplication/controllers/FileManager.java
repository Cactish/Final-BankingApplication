/**
 * @author rratajczak
 * @createdOn 2/16/2023 at 10:22 AM
 * @projectName Final-BankingApplication
 * @packageName bankingapplication.controllers;
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
    public final static String ROOT_FOLDER = "C:\\BankingApplication\\";

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

    /**
     * Reads file from given path
     * @param path Path to the file
     * @return String of the content in the file
     */
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
    public User createUserFromUserFolder(String userName) {
        User user = null;
        List<BankAccount> accounts = new ArrayList<>();
        if (!getAllFilesInFolder().contains(userName)) {
            System.out.println("user not found");
            return null;
        }
        File userFolder = new File(ROOT_FOLDER + userName + "\\");
        String[] files = userFolder.list();

        // Iterate through files in the User's folder
        assert files != null;
        for (String file : files) {
            String path = userFolder + "\\" + file;
            if (file.equals("Login.txt")) {
                String[] loginInfo = readData(path).split("\r\n");
                user = new User(loginInfo[0], loginInfo[1]);
            }
            // Check if file is savings account or checking account
            BankAccount account;
            if (file.charAt(0) == 'C') {
                String[] accountInfo = readData(path).split("\r\n");
                account = new CheckingAccount(accountInfo[0], Double.parseDouble(accountInfo[1]));
                for (int i = 2; i < accountInfo.length; i++) {
                    account.addTransaction(accountInfo[i]);
                }
                accounts.add(account);
            }
            if (file.charAt(0) == 'S') {
                String[] accountInfo = readData(path).split("\r\n");
                account = new SavingsAccount(accountInfo[0], Double.parseDouble(accountInfo[1]), Integer.parseInt(accountInfo[3]));
                for (int i = 5; i < accountInfo.length; i++) {
                    account.addTransaction(accountInfo[i]);
                }
                accounts.add(account);
            }
        }
        for (BankAccount account : accounts) {
            assert user != null;
            user.openAccount(account);
        }
        return user;
    }

    /**
     * Parses User object and writes it to the appropriate files
     * @param user The User object to be written
     */
    public void saveUser(User user) {
        File userFolder = new File(ROOT_FOLDER + user.getUserName());
        String path = userFolder.getPath() + "\\";
        if(!userFolder.exists()) {
            try {
                userFolder.mkdir();
            } catch (SecurityException ex) {
                System.out.println(ex.getMessage());
            }
        }
        String content;
        content = user.getUserName() + "\n" + user.getPassword();
        writeData(path + "Login.txt", content, false);
        for (BankAccount account : user.getAccounts()) {
            if (account.getClass().getSimpleName().equals("CheckingAccount")) {
                content = account.getName() + "\n" +
                        account.getBalance() + "\n";
                for (String transaction : account.getTransactions()) {
                    content += transaction + "\n";
                }
                writeData(path + "Checking" + account.getName() + ".txt", content, false);
            } else {
                content = account.getName() + "\n" + account.getBalance() + "\n" +
                        account.getInterestRate() + "\n" + account.getWithdrawals() + "\n" +
                        account.isWithdrawalsReset() + "\n";
                for (String transaction : account.getTransactions()) {
                    content += transaction + "\n";
                }
                writeData(path + "Savings" + account.getName() + ".txt", content, false);
            }
        }
    }
}