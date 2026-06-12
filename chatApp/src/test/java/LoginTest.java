/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
     
    Login login;
    
    @BeforeEach
    public void setUp() {
        login= new Login();
    }
    
    // ========== assertEquals TESTS ==========
    
    @Test
    //Testing username is correctly formatted
        // Test Data: "kyl_1"
    public void testUsernameCorrectlyFormatted() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        String expected = "Welcome Kyle, Smith it is great to see you.";
        assertEquals(expected, result);
    }
    
    @Test
    //Testing username is incorrectly formatted
        // Test Data: "kyle!!!!!!!"
    public void testUsernameIncorrectlyFormatted() { 
        String result = login.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        String expected = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        assertEquals(expected,result);
    }
    
    @Test
    //Testing password meets complexity
        // Test Data: "Ch&&sec@ke99!"
    public void testPasswordMeetsComplexity() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        String expected = "Welcome Kyle, Smith it is great to see you.";
        assertEquals(expected, result);
    }
    
    @Test
     //Testing password does not meet complexity
        // Test Data: "password"
    public void testPasswordDoesNotMeetComplexity() {
        String result = login.registerUser("kyl_1", "password", "Kyle", "Smith", "+27838968976");
        String expected = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        assertEquals(expected, result);
    }
    
    @Test
    //Testing cell phone is correctly formatted
        // Test Data: +27838968976
    public void testCellPhoneCorrectlyFormatted() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        String expected = "Welcome Kyle, Smith it is great to see you.";
        assertEquals(expected, result);
    }
    
    @Test
    //Testing cell phone is incorrectly formatted
        // Test Data: "08966553"
    public void testCellPhoneIncorrectlyFormatted() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "08966553");
        String expected = "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        assertEquals(expected, result);
    }
    
    // ========== assertTrue/assertFalse TESTS ==========
    
    @Test
     //Testing login successful
        // First register a user
    public void testLoginSuccessful() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        // Then test login
        boolean result = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(result);
    }
    
    @Test
    //Testing login failed"
        // First register a user
    public void testLoginFailed() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        // Then test login with wrong password
        boolean result = login.loginUser("kyl_1", "wrongpassword");
        assertFalse(result);
    }
    
    @Test
    //Testing checkUsername returns true
    public void testUsernameCorrectlyFormattedReturnsTrue() {
        boolean result = login.checkUsername("kyl_1");
        assertTrue(result);
    }
    
    @Test
     //Testing checkUsername returns false
    public void testUsernameIncorrectlyFormattedReturnsFalse() {
        boolean result = login.checkUsername("kyle!!!!!!!");
        assertFalse(result);
    }
    
    @Test
     //Testing checkPasswordComplexity returns true
    public void testPasswordMeetsComplexityReturnsTrue() {
        boolean result = login.checkPasswordComplexity("Ch&&sec@ke99!");
        assertTrue(result);
    }
    
    @Test
    //Testing checkPasswordComplexity returns false
    public void testPasswordDoesNotMeetComplexityReturnsFalse() {
        boolean result = login.checkPasswordComplexity("password");
        assertFalse(result);
    }
    
    @Test
    //Testing checkCellPhoneNumber returns true"
    public void testCellPhoneCorrectlyFormattedReturnsTrue() {
        boolean result = login.checkCellPhoneNumber("+27838968976");
        assertTrue(result);
    }
    
    @Test
     //Testing checkCellPhoneNumber returns false
    public void testCellPhoneIncorrectlyFormattedReturnsFalse() {
        boolean result = login.checkCellPhoneNumber("08966553");
        assertFalse(result);
    }
}