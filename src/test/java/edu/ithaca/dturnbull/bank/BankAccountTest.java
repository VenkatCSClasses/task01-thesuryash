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
    void isEmailValidTest(){
        // Valid email addresses
        assertTrue(BankAccount.isEmailValid("a@b.com"));
        assertTrue(BankAccount.isEmailValid("test.email@domain.com"));
        assertTrue(BankAccount.isEmailValid("user.name+tag+sorting@example.com"));
        assertTrue(BankAccount.isEmailValid("firstname.lastname@domain.com"));
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
        assertFalse(BankAccount.isEmailValid("@missingusername.com")); // Missing username
        assertFalse(BankAccount.isEmailValid("username@.com")); // Starts with dot after '@'
        assertFalse(BankAccount.isEmailValid("username@domain..com")); // Consecutive dots
        assertFalse(BankAccount.isEmailValid("username@domain")); // Missing top-level domain
        assertFalse(BankAccount.isEmailValid("username@domain.c")); // Top-level domain too short
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