package edu.ithaca.dturnbull.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void withdrawNegativeAmountTest(){
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        // withdrawing a negative amount should be invalid
        assertThrows(IllegalArgumentException.class, () -> {
            try {
                bankAccount.withdraw(-50);
            } catch (InsufficientFundsException e) {
                // convert checked exception to runtime for lambda compatibility
                throw new RuntimeException(e);
            }
        });
        // balance should remain unchanged
        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawZeroAmountTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(0);
        // withdrawing zero should not change balance
        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void depositTest(){
        BankAccount bankAccount = new BankAccount("a@b.com", 100);
        bankAccount.deposit(50);
        assertEquals(150, bankAccount.getBalance(), 0.001);
    }

    @Test
    void depositNegativeAmountTest(){
        BankAccount bankAccount = new BankAccount("a@b.com", 100);
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-20));
        assertEquals(100, bankAccount.getBalance(), 0.001);
    }

    @Test
    void depositZeroAmountTest(){
        BankAccount bankAccount = new BankAccount("a@b.com", 100);
        bankAccount.deposit(0);
        assertEquals(100, bankAccount.getBalance(), 0.001);
    }

    @Test
    void multipleWithdrawalsTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 500);
        bankAccount.withdraw(100);
        bankAccount.withdraw(150);
        assertEquals(250, bankAccount.getBalance(), 0.001);
    }

    @Test
    void multipleDepositsTest(){
        BankAccount bankAccount = new BankAccount("a@b.com", 100);
        bankAccount.deposit(50);
        bankAccount.deposit(75);
        assertEquals(225, bankAccount.getBalance(), 0.001);
    }

    @Test
    void transferTest() throws InsufficientFundsException{
        BankAccount a = new BankAccount("a@b.com", 200);
        BankAccount b = new BankAccount("b@c.com", 50);

        a.transferTo(b, 75);

        assertEquals(125, a.getBalance(), 0.001);
        assertEquals(125, b.getBalance(), 0.001);
    }

    @Test
    void transferInsufficientFundsTest(){
        BankAccount a = new BankAccount("a@b.com", 30);
        BankAccount b = new BankAccount("b@c.com", 0);

        assertThrows(InsufficientFundsException.class, () -> a.transferTo(b, 100));
        // balances unchanged
        assertEquals(30, a.getBalance(), 0.001);
        assertEquals(0, b.getBalance(), 0.001);
    }

    @Test
    void transferNegativeAmountTest(){
        BankAccount a = new BankAccount("a@b.com", 100);
        BankAccount b = new BankAccount("b@c.com", 0);

        assertThrows(IllegalArgumentException.class, () -> a.transferTo(b, -10));
        assertEquals(100, a.getBalance(), 0.001);
        assertEquals(0, b.getBalance(), 0.001);
    }

    @Test
    void transferToSelfTest() throws InsufficientFundsException{
        BankAccount account = new BankAccount("a@b.com", 200);
        account.transferTo(account, 50);
        assertEquals(200, account.getBalance(), 0.001);
    }

    @Test
    void transferZeroAmountTest() throws InsufficientFundsException{
        BankAccount a = new BankAccount("a@b.com", 100);
        BankAccount b = new BankAccount("b@c.com", 50);
        a.transferTo(b, 0);
        assertEquals(100, a.getBalance(), 0.001);
        assertEquals(50, b.getBalance(), 0.001);
    }

    @Test
    void isAmountValidTest(){
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertTrue(bankAccount.isAmountValid(0));
        assertTrue(bankAccount.isAmountValid(10.5));
        assertFalse(bankAccount.isAmountValid(-0.0001));
    }

    @Test
    void isAmountValidWithInsufficientFundsTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 100);
        assertFalse(bankAccount.isAmountValid(150));
        assertTrue(bankAccount.isAmountValid(100));
    }

    @Test
    void isEmailValidTest(){
        // Valid email addresses
        assertTrue(BankAccount.isEmailValid("a@b.com"));
        assertTrue(BankAccount.isEmailValid("testemail@domain.com"));
        assertTrue(BankAccount.isEmailValid("username+tag+sorting@example.com"));
        assertTrue(BankAccount.isEmailValid("firstnamelastname@domain.com"));

        assertTrue(BankAccount.isEmailValid("testemail@domain.com")); //equivalence class good email
        assertTrue(BankAccount.isEmailValid("username+tag+sorting@example.com")); //equivalence class good email
        assertTrue(BankAccount.isEmailValid("firstnamelastname@domain.com")); //equivalence class good email

        assertTrue(BankAccount.isEmailValid("email@subdomain.domain.com"));
        assertTrue(BankAccount.isEmailValid("firstname+lastname@domain.com"));
        assertTrue(BankAccount.isEmailValid("1234567890@domain.com"));
        assertTrue(BankAccount.isEmailValid("email@domain-one.com"));
        assertTrue(BankAccount.isEmailValid("_______@domain.com"));
        assertTrue(BankAccount.isEmailValid("email@domain.name"));
        assertTrue(BankAccount.isEmailValid("email@domain.co.jp"));
        assertTrue(BankAccount.isEmailValid("firstname-lastname@domain.com"));

        // Invalid email addresses

        assertFalse(BankAccount.isEmailValid("")); // Empty string
        assertFalse(BankAccount.isEmailValid("plainaddress")); // Missing '@'

        assertFalse(BankAccount.isEmailValid("")); // Empty string  equivalence class: empty string: boolean value 
        assertFalse(BankAccount.isEmailValid("plainaddress")); // Missing '@' eq

        assertFalse(BankAccount.isEmailValid("@missingusername.com")); // Missing username
        assertFalse(BankAccount.isEmailValid("username@.com")); // Starts with dot after '@'
        assertFalse(BankAccount.isEmailValid("username@domain..com")); // Consecutive dots
        assertFalse(BankAccount.isEmailValid("username@domain")); // Missing top-level domain
        assertFalse(BankAccount.isEmailValid("username@.domain.com")); // Starts with dot
        assertFalse(BankAccount.isEmailValid("username@domain.com.")); // Ends with dot
        assertFalse(BankAccount.isEmailValid("username@domain,com")); // Invalid character ','
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

    @Test
    void emailValidWithSpecialCharactersTest(){
        assertFalse(BankAccount.isEmailValid("user@name@domain.com")); // Multiple @
        assertFalse(BankAccount.isEmailValid("user name@domain.com")); // Space
        assertTrue(BankAccount.isEmailValid("user.name@domain.com")); // Dot in username
    }

}