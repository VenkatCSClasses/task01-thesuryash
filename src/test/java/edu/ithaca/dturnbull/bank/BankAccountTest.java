package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


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

}