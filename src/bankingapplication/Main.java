package bankingapplication;

import bankingapplication.controllers.BankingController;

public class Main
{
    public static void main(String[] args) {
        new BankingController().runLoginMenu();
    }
}