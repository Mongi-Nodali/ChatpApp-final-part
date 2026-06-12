/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    
    private Message message;

    @BeforeEach
    public void setUp() {
        message = new Message();
    }

    @Test
    public void testMessageLengthSuccess() {
        String shortMessage = "Hi Mike, can you join us for dinner tonight?";
        boolean result = shortMessage.length() <= 250;
        assertTrue(result, "Message should be within 250 characters");
    }
    
    @Test
    public void testMessageLengthFaliure() {
        String longMessage = "Hi, I just wanted to chexck in and see how you everything has been going on your side. Nothing hectic has been going on, on my side just school and assignments. i feel like i have been neglecting you a bit because of that and i would like to apologize. Can we grab dinner sometime just to properly catch-up, there is a lot i have to tell you. ";
        int excess = longMessage.length() - 250;
        if (longMessage.length() <= 250) {
            System.out.println("Message ready to send");
        } else {
            System.out.println("Message exceeds 250 characters by " + excess + "; please reduce the size");
        }
        assertEquals(93, excess);
    }
    
    @Test
    public void testRecipientCellFailure() {
        String invalidCell = "08575975889";
        String result = message.checkRecipientCell(invalidCell);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again", result);
    }

    @Test
    public void testMessageHash() {
        int messageCount = 0;
        String content = "Hi mike, can you join us for dinner tonight?";
        long testId = 123456789L;
        String firstTwo = String.valueOf(testId).substring(0,2);
        String hash = message.createMessageHash(testId, messageCount, content);
        String[] words = content.split(" ");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        String expectedHash = firstTwo + ":0:" + firstWord + lastWord;
        assertEquals(expectedHash, hash);
    }

    @Test
    public void testMessageId() {
        long messageId = 1000000000L + (long)(Math.random() * 9000000000L);
        String idString = String.valueOf(messageId);
        assertTrue(idString.length() == 10, "Message Id should be 10 digits");
        System.out.println("Message ID generated:" + messageId);
    }

    @Test
    public void testSendMessageOption() {
        String result = message.sentMessage(1);
        assertEquals("Message successfully sent.", result);
    }
    
    @Test
    public void testDisregardMessageOption() {
        String result = message.sentMessage(2);
        assertEquals("Press 0 to delete the message.", result);
    }
    
    @Test
    public void testStoreMessageOption() {
        String result = message.sentMessage(3);
        assertEquals("Message successfully stored.", result);
    }

    @Test
    public void testMessageIdNotMoreThanTenCharacters() {
        long validId = 1234567890L;
        long invalidId = 12345678901L;
        assertTrue(message.checkMessageID(validId));
        assertFalse(message.checkMessageID(invalidId));
    }

    @Test
    public void testMultipleMessageHashes() {
        String[] testMessages = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hi Keegan, did you receive the payment?",
            "Hello world",
            "Good morning everyone"
        };
        for (int i = 0; i < testMessages.length; i++) {
            long messageId = 1000000000L + (long)(Math.random() * 9000000000L);
            String firstTwo = String.valueOf(messageId).substring(0, 2);
            String[] words = testMessages[i].split(" ");
            String firstWord = words[0];
            String lastWord = words[words.length - 1];
            String expectedHash = (firstTwo + ":" + i + ":" + firstWord + lastWord).toUpperCase();
            String actualHash = message.createMessageHash(messageId, i, testMessages[i]);
            assertEquals(expectedHash, actualHash);
            System.out.println("Message " + (i+1) + " Hash: " + actualHash);
        }
    }
}