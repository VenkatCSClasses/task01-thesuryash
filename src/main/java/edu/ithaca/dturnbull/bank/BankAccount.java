package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount < 0){
            throw new IllegalArgumentException("Cannot withdraw negative amount");
        }
        else
            {
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }}
    }

    public void deposit(double amount){
        if (amount < 0){
            throw new IllegalArgumentException("Cannot deposit negative amount");
        }
        balance += amount;
    }

    public void transferTo(BankAccount other, double amount) throws InsufficientFundsException{
        if (isAmountValid(amount)){   
        this.withdraw(amount);
        other.deposit(amount);
    } else{
            throw new IllegalArgumentException("Cannot transfer negative amount");
        }}

    public static boolean isAmountValid(double amount){
        return amount >= 0;
    }

    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1){
            return false;
        }
        //Check periods and comes after @
        if (email.indexOf('.') == -1 ||email.indexOf('@') > email.indexOf('.')){
            return false;
        }
        //Check for empty string
        if (email.isEmpty()){
            return false;             
        }
        
        // Check for consecutive periods
        if (email.contains("..")){
            return false;
        }
        
        // Check for period next to @
        if (email.contains(".@") || email.contains("@.")){
            return false;
        }

        if (email.indexOf('@') == 0 || email.indexOf('.') == 0){
            return false;
        }

        if(email.lastIndexOf('.') == email.length() - 1 || email.lastIndexOf('@') == email.length() - 1){
            return false;
        }
        
        if(email.contains(",")){
            return false;
        }

        return true;
    }
}