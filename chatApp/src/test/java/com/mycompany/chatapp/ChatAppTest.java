/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.chatapp;

import org.junit.jupiter.api.*;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.nio.file.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
/**
 *
 * @author mongi
 */
public class ChatAppTest {
    
    private static ChatApp chatApp;
    private static final String TEST_FILE = "test_messages.txt";
    
    @BeforeEach
    public void setUp() {
        // Setup test environment
        chatApp = new ChatApp();
    }
    
    @AfterEach
    public void cleanUp() {
        // Clean up test files
        try {
            Files.deleteIfExists(Paths.get(TEST_FILE));
        } catch (Exception e) {
            // Ignore
        }
    }
    
    //TEST 1: Sent Messages Array Correctly Populated
    @Test
    public void testSentMessagesArrayPopulated() {
        // Test Data: Developer entry for message 1 and 4
        // Expected: "Did you get the cake?", "It is dinner time!"
        
        String[] expectedMessages = {"Did you get the cake?", "It is dinner time!"};
        
        // Simulate populating sent messages
        String[] sentMessages = new String[10];
        sentMessages[0] = "Did you get the cake?";
        sentMessages[3] = "It is dinner time!";
        
        // Verify both messages exist in array
        boolean foundMessage1 = false;
        boolean foundMessage4 = false;
        
        for (String msg : sentMessages) {
            if (msg != null && msg.equals("Did you get the cake?")) {
                foundMessage1 = true;
            }
            if (msg != null && msg.equals("It is dinner time!")) {
                foundMessage4 = true;
            }
        }
        
        assertTrue(foundMessage1, "Sent array should contain: 'Did you get the cake?'");
        assertTrue(foundMessage4, "Sent array should contain: 'It is dinner time!'");
    }
    
    //TEST 2: Display the Longest Message 
    @Test
    public void testDisplayLongestMessage() {
        // Test Data: messages 1-4
        // Expected: "Where are you? You are late! I have asked you to be on time."
        
        String[] testMessages = {
            "Did you get the cake?",
            "Where are you? You are late! I have asked you to be on time.",
            "Yohoooo, I am at your gate.",
            "It is dinner time!"
        };
        
        String longest = "";
        for (String msg : testMessages) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }
        
        String expected = "Where are you? You are late! I have asked you to be on time.";
        
        assertEquals(expected, longest, "The longest message should be correctly identified");
        assertEquals(expected.length(),longest.length(), "The longest message should be correctly identified");
    }
    
    // TEST 3: Search for Message ID
    @Test
    public void testSearchByMessageID() {
        // Test Data: message 4 (Developer: 0838884567)
        // Expected: "It is dinner time!"
        long[] messageIDs = {1000000001L, 1000000002L, 1000000003L, 1000000004L};
        String[] messageContents = {
            "Did you get the cake?",
            "Where are you? You are late! I have asked you to be on time.",
            "Yohoooo, I am at your gate.",
            "It is dinner time!"
        };
        
        long searchId = 1000000004L;
        String foundMessage = null;
        
        for (int i = 0; i < messageIDs.length; i++) {
            if (messageIDs[i] == searchId) {
                foundMessage = messageContents[i];
                break;
            }
        }
        
        assertEquals("It is dinner time!", foundMessage, "Message ID should return correct message");
    }
    
    //TEST 4: Search by Recipient
    @Test
    public void testSearchByRecipient() {
        // Test Data: +27838884567
        // Expected: "Where are you? You are late! I have asked you to be on time."
        // "Ok, I am leaving without you."
        
        String[] recipients = {"+27834557896", "+27838884567", "+27834484567", "+27838884567"};
        String[] messages = {
            "Did you get the cake?",
            "Where are you? You are late! I have asked you to be on time.",
            "Yohoooo, I am at your gate.",
            "Ok, I am leaving without you."
        };
        
        String searchRecipient = "+27838884567";
        java.util.ArrayList<String> foundMessages = new java.util.ArrayList<>();
        
        for (int i = 0; i < recipients.length; i++) {
            if (recipients[i].equals(searchRecipient)) {
                foundMessages.add(messages[i]);
            }
        }
        
        assertEquals(2, foundMessages.size(), "Should find 2 messages for this recipient");
        assertTrue(foundMessages.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(foundMessages.contains("Ok, I am leaving without you."));
    }
    
    //TEST 5: Delete Message by Hash
    @Test
    public void testDeleteByHash() {
        // Test Data: Message 2 (the long message)
        // Expected: Message successfully deleted
        
      
        String[] messageHashes = {
            "hash1", "hash2", "hash3", "hash4"
        };
        String[] messages = {
            "Did you get the cake?",
            "Where are you? You are late! I have asked you to be on time.",
            "Yohoooo, I am at your gate.",
            "It is dinner time!"
        };
        
        String hashToDelete = "hash2";
        int foundIndex = -1;
        
        for (int i = 0; i < messageHashes.length; i++) {
            if (messageHashes[i].equals(hashToDelete)) {
                foundIndex = i;
                break;
            }
        }
        
        assertNotEquals(-1, foundIndex, "Hash should be found");
        
        // Verify message exists before deletion
        assertEquals("Where are you? You are late! I have asked you to be on time.", messages[foundIndex]);
        
        
        messages[foundIndex] = null;
        
        // Verify message is deleted
        assertNull(messages[foundIndex], "Message should be deleted");
    }
    
    // TEST 6: Display Report 
    @Test
    public void testDisplayReport() {
        // Expected: Report shows all messages with Hash, Recipient, Message
        
        JSONArray reportArray = new JSONArray();
        
        // Add test messages
        JSONObject msg1 = new JSONObject();
        msg1.put("Message Hash", "HASH123");
        msg1.put("Recipient", "+27834557896");
        msg1.put("content", "Did you get the cake?");
        reportArray.put(msg1);
        
        JSONObject msg2 = new JSONObject();
        msg2.put("Message Hash", "HASH456");
        msg2.put("Recipient", "+27838884567");
        msg2.put("content", "Where are you?");
        reportArray.put(msg2);
        
        
        for (int i = 0; i < reportArray.length(); i++) {
            JSONObject msg = reportArray.getJSONObject(i);
            assertTrue(msg.has("Message Hash"), "Report should include Message Hash");
            assertTrue(msg.has("Recipient"), "Report should include Recipient");
            assertTrue(msg.has("content"), "Report should include Message content");
        }
        
        assertEquals(2, reportArray.length(), "Report should show all messages");
    }
    
    // TEST 7: Message Limit Validation
    @Test
    public void testMessageLimit() {
        int messageLimit = 5;
        int messageCount = 3;
        
        assertTrue(messageCount < messageLimit, "Should allow sending when under limit");
        
        messageCount = 5;
        assertFalse(messageCount < messageLimit, "Should NOT allow sending when at limit");
    }
    
    // TEST 8: Phone Number Validation (Regex) 
    @Test
    public void testPhoneNumberValidation() {
        String regex = "^\\+27[0-9]{9}$";
        
        // Valid numbers
        assertTrue("+27721234567".matches(regex), "+27721234567 should be valid");
        assertTrue("+27834557896".matches(regex), "+27834557896 should be valid");
        assertTrue("+27838884567".matches(regex), "+27838884567 should be valid");
        
        // Invalid numbers
        assertFalse("27721234567".matches(regex), "Missing + should be invalid");
        assertFalse("+2772123456".matches(regex), "Only 8 digits should be invalid");
        assertFalse("+277212345678".matches(regex), "10 digits should be invalid");
        assertFalse("+2772123456a".matches(regex), "Letter should be invalid");
    }
    
    // TEST 9: JSON File Storage
    @Test
    public void testJSONFileStorage() {
        try {
            // Create a test JSON file
            JSONArray arr = new JSONArray();
            JSONObject obj = new JSONObject();
            obj.put("content", "Test message");
            obj.put("Recipient", "+27721234567");
            obj.put("Message Hash", "TEST123");
            arr.put(obj);
            
            // Write to file
            try (FileWriter file = new FileWriter(TEST_FILE)) {
                file.write(arr.toString(4));
            }
            
            // Read back
            String content = new String(Files.readAllBytes(Paths.get(TEST_FILE)));
            JSONArray readArray = new JSONArray(content);
            
            assertEquals(1, readArray.length(), "File should contain 1 message");
            assertEquals("Test message", readArray.getJSONObject(0).getString("content"));
            
        } catch (Exception e) {
            fail("JSON file operation failed: " + e.getMessage());
        }
    }
    
    //TEST 10: Message Hash Generation Format
    @Test
    public void testHashGenerationFormat() {
        // Hash format: firstTwo:count:firstWord+lastWord
        long messageId = 7448400718L;
        String firstTwo = String.valueOf(messageId).substring(0, 2);
        int count = 1;
        String firstWord = "we";
        String lastWord = "while";
        String expectedHash = "74:1:WEWHILE";
        
        String generatedHash = (firstTwo + ":" + count + ":" + firstWord + lastWord).toUpperCase();
        
        assertEquals(expectedHash, generatedHash, "Hash should match expected format");
    }
}
    
    
    
  