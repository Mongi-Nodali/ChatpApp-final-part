/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

/**
 *
 * @author mongi
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.FileWriter;    
import java.io.IOException;   
import java.nio.file.Files;  
import java.nio.file.Paths;   
import org.json.JSONArray;    
import org.json.JSONObject;   

public class Message {
    
    long messageId;
    String hash;
    String recipientsCell;
    String message;
    int messageCount;
    
    //Static variable to store all messages while the program is running
    static String allMessages = "";
    static int totalMessages = 0;
    
    //Method 1: Checks if message ID is not more than 10 characters
    public boolean checkMessageID(long messageId) {
        return String.valueOf(messageId).length() <=10;
    }
    //Method 2: Checks recipient cell nummber using regex
    // Regex pattern: \+27\d{9} means: "+27" followed by exactly 9 digits
    public String checkRecipientCell(String recipientsCell) {
     String regex = "^\\+27[0-9]{9}$";
     Pattern pattern = Pattern.compile(regex);
     Matcher matcher = pattern.matcher(recipientsCell);
     if (matcher.matches()) {
         return "Cell phone number successfully captured";
     } else {
     return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again";
     }
    }
    //Method 3: Creates and returns Message Hash
    public String createMessageHash(long messageId, int messageCount, String message) {
        String firstTwo = String.valueOf(messageId).substring(0,2);
        String[] words = message.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        return(firstTwo + ":" + messageCount + ":" + firstWord + lastWord).toUpperCase();
        
    }
     //Method 4: Allows user to choose send, store or disregard message
    public String sentMessage(int choice) {
        if (choice == 1) {
            return "Message successfully sent.";
           
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
            
        } else if (choice == 3) {
            return "Message successfully stored.";
            
        } else {
            return "Invalid option";
            
        }
      }
    //Method 5: Returns all messages sent
    public static String printMessage() {
        if (allMessages.equals("")) {
            return "No messages have been sent yet";
            
        }
        return allMessages;
    }
    //Method 6: Returns total number of messages sent
    public static int returnTotalMessages() {
        return totalMessages;
        }
    //Method 7: Stores message in JSON file
    public void StoreMessage() {
             JSONArray messageArray = new JSONArray();
                      
                      //reads existing messages if file already exists
                      try {
                          String existingContent = new String(Files.readAllBytes(Paths.get("messages.txt")));
                          messageArray = new JSONArray(existingContent);
                      }catch (IOException e) {
                          // if file doesn't exist it will create a new one
                      }
                      //Create JSON object for the new file
                      JSONObject messageObj = new JSONObject();
                      messageObj.put("messageID", this.messageId);
                      messageObj.put("Message Hash", this.hash);
                      messageObj.put("Recipient", this.recipientsCell);
                      messageObj.put("content", this.message);
                      messageObj.put("messageNumber", this.messageCount);
                      
                      //Add to array
                      messageArray.put(messageObj);
                      
                      //saves to file
                      try (FileWriter file =new FileWriter("messages.txt")) {
                          file.write(messageArray.toString(4));
                          System.out.println("Message saved to JSON file");
                      }catch (IOException e) {
                          System.out.println("ERROR: " + e.getMessage());
                      }
    }
    }
       