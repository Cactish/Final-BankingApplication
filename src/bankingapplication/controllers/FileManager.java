/**
 * @author rratajczak
 * @createdOn 2/16/2023 at 10:22 AM
 * @projectName GameStuff
 * @packageName gamestuff.controllers;
 */
package bankingapplication.controllers;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileManager
{
    public final static String ROOT_FOLDER = "C:\\Accounts";

    private String getFullFilePath(String fileName) {
        return ROOT_FOLDER + "\\" + fileName + ".txt";
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

    public void writeData(String fileName, String contents, boolean append) {
        createRootFolder();
        BufferedWriter write = null;
        try {
            write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getFullFilePath(fileName), append)));
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

    public String readData(String fileName) {
        BufferedReader read = null;
        String content = "";
        try {
            read = new BufferedReader(new InputStreamReader(new FileInputStream(getFullFilePath(fileName))));
            while (read.ready()) {
                content += read.readLine() + "\r\n";
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

    public List<String> getAllFilesInFolder() {
        createRootFolder();
        File rootFolder = new File(ROOT_FOLDER);
        List<String> files = Arrays.asList(rootFolder.list());
        for (int i = 0; i < files.size(); i++) {
            String fileName = files.get(i);
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            files.set(i, fileName);
        }
        return files;
    }

}
