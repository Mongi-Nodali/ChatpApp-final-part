 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp;

/**
 * 
 * @author mongi
 */
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

//JSON file imports
import java.io.FileWriter;    //Allows writing to files
import java.io.IOException;   //Handles file errors
import java.nio.file.Files;   //Reads files
import java.nio.file.Paths;   //Gets file path
import org.json.JSONArray;    //Stores multiple messages in JSON format
import org.json.JSONObject;  //Stores one message in JSON format
import java.nio.file.Path;

public class ChatApp {
  
 //static variables so all methods can use the variables(access them) 
static String Username;
static String Password;
static String cell;
static String firstName;
static String lastName;

//Part2 variables
static int messageLimit= 0;   //Maximum number of messages a user can send 
static int messageCount = 0;  //Counter for messages sent so far 
static String message;
static String recipientsCell;
static String disregard;
static String hash;
static int choice2;
static Scanner input = new Scanner(System.in);

//part 3 variables
static int disregardedCount = 0;
static int storedCount = 0;

//part 3 Arrays to store message data
 static String[] sentMessages;
 static String[] disregardedMessages;
 static String[] storedMessages;
 static String[] messageHashes;
 static long[] messageIDs;
 static String[] sentRecipients;


public static void main(String[] args) throws Exception {
        
        
//Login system lets the user choose an option
     
     boolean registered = false;
     int choice;
     while (true) {
      System.out.println("--------LOGIN SYSTEM--------");
      System.out.println("1. Register");
      System.out.println("2. Login");
      System.out.println("3. Exit");
      System.out.print(" Choose option:");
      
       choice = input.nextInt();
      input.nextLine();
      
       if (choice == 1) { //if they pick 1 take to the registration
           register();
           registered = true;
       }else if (choice == 2) {
           if(!registered) {
           System.out.println("You cannot login without first registering");
           }else{
               login();
               break;
           }
        }else if (choice == 3) {
            System.out.println("GOODBYE!!");
            break;
        }else{
            System.out.println("INVALID INPUT");
        }
    }
     //Part 2 choice system
     System.out.println("\n-------Welcome to QuickChat-------");
     
     System.out.println("How many messages do you want to send?: ");
     messageLimit = input.nextInt();
     input.nextLine();
     
    // Part 3 Initialize arrays with extra space
    //https://www.geeksforgeeks.org/dsa/parallel-array/
int totalSize = messageLimit + 100;
sentMessages = new String[totalSize];
disregardedMessages = new String[totalSize];
storedMessages = new String[totalSize];
messageHashes = new String[totalSize];
messageIDs = new long[totalSize];
sentRecipients = new String[totalSize];
 
// Read JSON file into array
JSONArray arr = getStoredMessages();
int loadCount = Math.min(arr.length(), totalSize);

// Reset counters
 messageCount = 0;
storedCount = 0;
disregardedCount = 0;

for (int i = 0; i < loadCount; i++) {
    JSONObject msg = arr.getJSONObject(i);
    String messageContent = msg.getString("Message");
    String messageType = msg.getString("messageType");
    
    // ONLY "sent" messages count toward message limit
    if (messageType.equals("sent")) {
        messageCount++;
    }
    
    // "stored" messages go to storedMessages array
    if (messageType.equals("stored")) {
        storedMessages[i] = messageContent;
        storedCount++;
    } else {
        storedMessages[i] = null;
    }    
    // "disregarded" messages go to disregardedMessages array
    if (messageType.equals("disregarded")) {
        disregardedMessages[disregardedCount] = messageContent;
        disregardedCount++;
    }
    
    // ALL messages go to sentMessages for history display
    sentMessages[i] = messageContent;
    sentRecipients[i] = msg.getString("Recipient");
    messageHashes[i] = msg.getString("Message Hash");
    messageIDs[i] = msg.getLong("messageID");
    
}
messageCount =0;

//Validate recipient number using REGEX
    System.out.println("\n Cellnumber should start with (+27)");
    while(true) {
        System.out.println("Enter recipients cellphone number: ");
        recipientsCell = input.nextLine();
                
        //REGEX Pattern
        String regex = "^\\+27[0-9]{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(recipientsCell);
        
        if (matcher.matches()) {
            if (recipientsCell.equals(cell)) {
                System.out.println("You cannot send a message to yourself");
            } else {
            System.out.println("Cell number added successfully");
            break;
            }
        } else {
            System.out.println("Cellphone number incorrectly formatted. Must start with +27 followed by 9 digits");
        }
    }
    
    //Main menu continues until user quits
     while(true) {
         System.out.println("-------QUICKCHAT MENU-------");
         System.out.println("1. Send messages");
      System.out.println("2. Show recently sent messages");
      System.out.println("3. Quit");
      System.out.println("4. Stored Messages");
      System.out.print(" Choose option:");
      
      choice2 = input.nextInt();
      input.nextLine();
      
      if (choice2 == 1){
          messages();
          
      }else if (choice2 == 2){
          showStoredMessages();
          } else if (choice2 == 3) {
              System.out.println("Total messages sent: " + messageCount);
              System.out.println("GOODBYE!!");
              break;
          } else if (choice2 == 4) {
              storedMessagesMenu();
          }else {
              System.out.println("INVALID OPTION");
          }
      
     }
     }

     //REGISTER METHODD
      public static void register() {
      System.out.println("\n --------REGISTRATION---------");
      
     System.out.print("Enter first name: ");
      firstName = input.nextLine();
           
        System.out.print("Enter last name: ");
        lastName = input.nextLine();

        //input Username
        //Use a while loop so the user can reenter the details until true
        System.out.println("\n Username should be less than 5 characters and contain and underscore(_)");
        while (true) {
        System.out.print("Enter username: ");
        Username = input.nextLine();
    
        // Check if the username has an underscore and has 5 characters or less
        if (Username.length() <= 5 && Username.contains("_")) {
            System.out.println("Username successfully captured");
            break;
        }else{
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        } 
        }
        
         //input Pasword 
         System.out.println("\n Password contain at least 8 characters, contain a capital letter, a digit and a special character");
         
        while (true) {
        System.out.print("Enter password:");
        Password = input.nextLine();
        
        boolean capitalLetter = false;
        boolean digit = false;
        boolean specialCharacter = false;
        
         //Check if password meets the requirements
        //use a for loop then an if statement 
        for(int i = 0; i < Password.length(); i++) {
            char ch = Password.charAt(i); //Checks each character one by one
        
        if (Character.isUpperCase(ch)) {
            capitalLetter = true;
        }else if(Character.isDigit(ch)){
            digit = true;
        }else if (!Character.isLetterOrDigit(ch)){
            specialCharacter = true;
        }
        }
        
        if(Password.length() >= 8 && capitalLetter && digit && specialCharacter) {
            System.out.println("Password succefully captured");
            break;
             } else {
            System.out.println("Password is not correcctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number and a special character.");
        }
        }
        
        //Enter cellphone number
      System.out.println("\n Cellnumber should start with (+27) and contain 9 digits after");
        while(true) {
       System.out.print("Enter cellphone number: ");
       cell = input.nextLine();
       
       
       boolean digits = true;
       
       for(int i = 3; i < cell.length(); i++) {
            char ch = cell.charAt(i);
           
           if(!Character.isDigit(ch)){
           digits = false;
           break;
           }
       }
           if (cell.length() == 12 && digits && cell.startsWith("+27")) {
               System.out.println("Cell number added succesfully");
               break;
           } else {
               System.out.println("Cellphone number incorrectly formatted or does not contain international code");
           }
        }
      
        System.out.println("\nRegistration successful");
      }  
         //LOGIN METHOD
            public static void login() {
           System.out.println("\n---------LOGIN----------");
           
           while(true) {
           System.out.print("Enter username: ");
          String loginUsername = input.nextLine();
           
           
           System.out.print("Enter password: ");
          String loginPassword = input.nextLine(); 
           
           if (loginUsername.equals(Username) && loginPassword.equals(Password)) {
             System.out.println("Welcome back " + firstName + " " + lastName + " " + "It's great to see you again");
            break;
           } else {
               System.out.println("Wrong username or password entered");
           }
       }
           System.out.println("\nLogin successful");
          
        }
        //MESSAGES METHOD
          public static void messages() throws Exception {
              System.out.println("\n------MESSAGES------");
              
              //Check if message limit has been reached
             if(messageCount >= messageLimit) {
                 System.out.println("You have reached your message limit of " + messageLimit + " messages");
                  return;
              }
             
              
              for (int i = messageCount; i < messageLimit; i++){
                  System.out.println("\nEnter your message (MAX 250 characters): ");
                  message = input.nextLine();
              
              if (message.length() <= 250) {
                  
                  //options
                  System.out.println("\nChoose whether to:");
                  System.out.println("1. Send the Message");
                  System.out.println("2. Disregard Message(Delete message)");
                  System.out.println("3. Store Message or send later");
                  System.out.println("Enter choice: ");
                  
                  int messageChoice = input.nextInt();
                  input.nextLine();
                  
                  if(messageChoice == 1){
                      
                  //messageID
                  //source for the messageID: https://www.baeldung.com/java-generate-random-long-float-integer-double
                  //used long because int is too small and can't store a 10 digit number
                  long messageId = 1000000000L + (long)(Math.random() * 9000000000L);
                
                  //Get the first 2 digits of the message ID
                  //source for using substrings: https://www.geeksforgeeks.org/java/substring-in-java/?utm_source=chatgpt.com
                  String firstTwo = String.valueOf(messageId).substring(0,2);
                  
                  //get first and last words from a message
                  //sources used for getting first and last word:https://stackoverflow.com/questions/4672806/java-simplest-way-to-get-last-word-in-a-string
                  // and:https://codebun.com/write-a-java-program-to-find-the-first-and-last-word-in-the-sentence/
                   String currentMessage = message;
                  String[] words = currentMessage.trim().split("\\s+");
                  String firstWord = words[0];
                  String lastWord = words[words.length - 1];
                  
                  //firstTwo:messageCount:fistwordlastWord
                  //also coverts to uppercase
                  hash = (firstTwo + ":" + messageCount + ":" + firstWord + lastWord).toUpperCase();
                  
                  //Display message details
                  System.out.println("------------MESSSAGE SENT---------");
                  System.out.println("Message ID: " + messageId);
                  System.out.println("Message Hash: " + hash);
                  System.out.println("Recipient: " + recipientsCell);
                  System.out.println("Message: " + message);
                  System.out.println("Message was successfully sent");
                
                //Saves recipient 
                String messageSentTo = recipientsCell;
                System.out.println("\nDo you want to change recipient? (1 for yes, 0 for no): ");
                int changeRecipient = input.nextInt();
                input.nextLine();
                if (changeRecipient == 1) {
    while(true) {
        System.out.print("Enter new recipient cell number: ");
        recipientsCell = input.nextLine();
        String regex = "^\\+27[0-9]{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(recipientsCell);
        if (matcher.matches()) {
            if(recipientsCell.equals(cell)) {
                System.out.println("You cannot send a message to yourself");
            } else {
            System.out.println("New recipient added successfully");
            break;
            }
        } else {
            System.out.println("Invalid number. Must start with +27 followed by 9 digits");
        }
    }
}
                  //populate part 3 arrays
                  sentMessages[messageCount]= message;
                  messageHashes[messageCount] = hash;
                  messageIDs[messageCount] = messageId;
                  sentRecipients[messageCount] = messageSentTo;
                  
                  messageCount++;
                   storeMessageToJSON(messageId, hash, messageSentTo, message, messageCount, "sent");
                   
              
                  
                  if (messageCount == messageLimit){
                      System.out.println("Message sent (" + messageCount  + "/" + messageLimit + ")");
                      System.out.println("\nAll " + messageLimit + " messages have been sent!");

                     break; 
                  } else {
                      System.out.println("Message sent (" + messageCount  + "/" + messageLimit + ")");
                      
                  }
                  //Ask if user wants to continue or return to menu
                  System.out.println("\nSend another message? (1 for yes, 0 to return to menu): ");
                  int continueChoice = input.nextInt();
                  input.nextLine();
                  
                  if (continueChoice == 0) {
                      break; // exit loop, return to the menu
                  }
                  
                  
                  
                  }else if (messageChoice == 2) {
                      System.out.println("Press 0 to delete the message");
                      disregard = input.nextLine();
                      System.out.println("Message is deleted");
                     
                      //Stores messages that were deleted
                  disregardedMessages[disregardedCount] = message;
                  disregardedCount++;
                    // ALSO save to JSON with type "disregarded" for tracking
                long disregardedId = 1000000000L + (long)(Math.random() * 9000000000L);
                 String firstTwo = String.valueOf(disregardedId).substring(0,2);
                 String[] words = message.trim().split("\\s+");
                 String firstWord = words[0].toUpperCase();
                 String lastWord = words[words.length - 1].toUpperCase();
                 String disregardedHash = (firstTwo + ":" + messageCount + ":" + firstWord + lastWord).toUpperCase();
    
                 // Save to JSON with type "disregarded"
                storeMessageToJSON(disregardedId, disregardedHash, recipientsCell, message, messageCount, "disregarded");
    
                 i--;

              
                      
                  } else if (messageChoice == 3) {
                      //Generate ID for stored message
                     long storedId = 1000000000L + (long)(Math.random() * 9000000000L);
                     //message hash
                     String currentMessage = message;
                     String firstTwo = String.valueOf(storedId).substring(0,2);
                      String[] words = currentMessage.trim().split("\\s+");
                     String firstWord = words[0];
                     String lastWord = words[words.length - 1];
                     String storedHash = (firstTwo + ":" + messageCount + ":" + firstWord + lastWord).toUpperCase();
                     
                     
                     storedMessages[messageCount]= message;
                     sentRecipients[messageCount] = recipientsCell;
                     messageHashes[messageCount] = storedHash;
                     messageIDs[messageCount] = storedId;
                     
                    storedCount++;
                    //save stored message to JSOn file
                      storeMessageToJSON(storedId, storedHash, recipientsCell, message, messageCount, "stored");
                      System.out.println("Message successfully stored");
                  
                   i--;
                  } else {
                      System.out.println("INVALID OPTION");
                  }
                  
              } else {
                  System.out.println("Please enter a message of less than 250 characters");
               
                  i--;
              }
                  
              }
          }
          //JSON file
          //sources used for JSON file: https://blogs.oracle.com/javamagazine/java-json-serialization-jackson/
          //https://www.baeldung.com/gson-save-file
          //https://www.baeldung.com/java-jsonarray-get-value-by-key
            public static void storeMessageToJSON (long messageId,String hash, String recipient, String message, int count, String messageType) throws Exception {
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
                      messageObj.put("messageID", messageId);
                      messageObj.put("Message Hash", hash);
                      messageObj.put("Recipient", recipient);
                      messageObj.put("Message", message);
                      messageObj.put("messageNumber", count);
                      messageObj.put("messageType", messageType);
                      
                      //Add to array
                      messageArray.put(messageObj);
                      
                      //saves to file
                      try (FileWriter file =new FileWriter("messages.txt")) {
                          file.write(messageArray.toString(4));
                          System.out.println("Message saved to text file");
                      }catch (IOException e) {
                          System.out.println("ERROR: " + e.getMessage());
                      }
            }
            public static void showStoredMessages() {
                //shows only stored messages from json file
                try {
                    Path file = Paths.get("messages.txt");
                    //check if file exits
                    if(!Files.exists(file)) {
                        System.out.println("\nNo messages stored yet");
                        return;
                    }
                    //read file content
                    String message = new String(Files.readAllBytes(file));
                    JSONArray array = new JSONArray(message);
                    
                    //check if empty
                    if (array.length() == 0) {
                        System.out.println("No messages stored yet");
                        
                         }
                    
                    System.out.println("\n-----RECENTLY SENT MESSAGES-----");
                    int displayCount = 1;
                    
                    //loop through all stored messages
                    for(int i = 0; i < array.length(); i++) {
                        JSONObject msg = array.getJSONObject(i);
                        System.out.println("Message no: " + displayCount);
                        System.out.println("MessageID: " + msg.getLong("messageID"));
                        System.out.println("Hash: " + msg.getString("Message Hash"));
                        System.out.println("Recipient: " + msg.getString("Recipient"));
                        System.out.println("Message: " + msg.getString("Message"));
                        System.out.println("Type: " + msg.getString("messageType")); 
                        System.out.println();
                        displayCount++;
                    }
                } catch (Exception e) {
                    System.out.println("Error reading stored messages: " + e.getMessage());
                }
                }

            //part 3 stored messages menu 
             public static void storedMessagesMenu() {
             while(true){
            System.out.println("\n------Stored messages menu------");
            System.out.println("1. Display the sender and recipient of all stored messages");
            System.out.println("2. Display the longest stored message");
            System.out.println("3. Search for a messageID and display the corresponding recipient and message");
            System.out.println("4. Search for all the messages stored for a particular recipient");
            System.out.println("5. Delete a message using the message hash");
            System.out.println("6. Display a report that lists the full details of all the stored messages");
            System.out.println("7. Return to Main Menu");
            System.out.println("Choose option: ");
            
            int storedChoice = input.nextInt();
            input.nextLine();
            
            if (storedChoice == 1) {
                displaySenderAndRecipient();
            } else if (storedChoice == 2) {
                displayLongestMessage();
            } else if (storedChoice == 3) {
                searchByMessageID();
            } else if (storedChoice == 4) {
                searchByRecipient(); 
            } else if (storedChoice == 5) {
                deleteByHash();  
            } else if (storedChoice == 6) {
                displayFullReport();
            }else if (storedChoice == 7) {
                return;
            }else {
                System.out.println("Invalid option");
            }
        }
    }
    public static JSONArray getStoredMessages() {
        try {
            Path file = Paths.get("messages.txt");
            if(!Files.exists(file)) {
                return new JSONArray();
                
            }
            String message = new String(Files.readAllBytes(file));
            return new JSONArray(message);
            
    } catch (Exception e) {
        System.out.println("Error reading messages: " + e.getMessage());
        return new JSONArray();
    }
}
public static void displaySenderAndRecipient() {
//Check if there are any stored messages
    boolean hasStoredMessages = false;
    
    for (int i = 0; i < storedMessages.length; i++) {
        if (storedMessages[i] != null) {
            hasStoredMessages = true;
            break;
        }
    }
    //if none are found
    if (!hasStoredMessages) {
        System.out.println("No stored messages found.");
        return;
    }
    
    System.out.println("\n------STORED MESSAGES - Sender and Recipient------");
    
    //Loop through only stored messages 
    for (int i = 0; i < storedMessages.length; i++) {
        if (storedMessages[i] != null) {
            System.out.println("Stored Message no" + (i + 1));
            System.out.println("Recipient: " + sentRecipients[i]);
            System.out.println("Sender: " + firstName + " " + lastName);
            System.out.println("Message: " + storedMessages[i]);  //ADDED - shows actual message
            System.out.println();
        }
    }
}

//Finds and displays the longest stored message 
public static void displayLongestMessage() {
    //Check if there are any stored messages
    boolean hasStoredMessages = false;
    
    for (int i = 0; i < storedMessages.length; i++) {
        if (storedMessages[i] != null) {
            hasStoredMessages = true;
            break;
        }
    }
    
    if (!hasStoredMessages) {
        System.out.println("No stored messages found. Use option 3 when sending to store messages.");
        return;
    }
    
    String longest = "";
    int longestIndex = -1;
    
    //Search the storedMessages array only to find longest message
    for (int i = 0; i < storedMessages.length; i++) {
        if (storedMessages[i] != null && storedMessages[i].length() > longest.length()) {
            longest = storedMessages[i];
            longestIndex = i;
        }
    }
    
    if (longestIndex == -1) {
        System.out.println("No stored messages found.");
        return;
    }
    
    System.out.println("\n--- LONGEST STORED MESSAGE ---");
    System.out.println("Message: " + longest);
    System.out.println("Length: " + longest.length() + " characters");
    System.out.println("Message Hash: " + messageHashes[longestIndex]);
    System.out.println("Message ID: " + messageIDs[longestIndex]);
}

public static void searchByMessageID() {
    System.out.print("Enter Message ID to search: ");
    long searchId = input.nextLong();
    input.nextLine();
    
    boolean found = false;
    
    //Search only through stored messages
    for (int i = 0; i < storedMessages.length; i++) {
        if (storedMessages[i] != null && messageIDs[i] == searchId) {
            System.out.println("\n--- STORED MESSAGE FOUND ---");
            System.out.println("Recipient: " + sentRecipients[i]);
            System.out.println("Message: " + storedMessages[i]);  
            System.out.println("Hash: " + messageHashes[i]);
            found = true;
            break;
        }
    }
    
    if (!found) {
        System.out.println("No stored message found with ID: " + searchId);
    }
}
  public static void searchByRecipient() {
    System.out.print("Enter recipient cell number: ");
    String searchRecipient = input.nextLine();
    boolean found = false;
    
    System.out.println("\n--- STORED MESSAGES FOR " + searchRecipient + " ---");
    
    //Search ONLY through stored messages
    for (int i = 0; i < storedMessages.length; i++) {
        if (storedMessages[i] != null && sentRecipients[i] != null && sentRecipients[i].equals(searchRecipient)) {
            System.out.println("Stored Message no" + (i + 1));
            System.out.println("Message: " + storedMessages[i]);  
            System.out.println("Message ID: " + messageIDs[i]);
            System.out.println("Hash: " + messageHashes[i]);
            System.out.println("---");
            found = true;
        }
    }
    
    if (!found) {
        System.out.println("No stored messages found for: " + searchRecipient);
    }
}
    
public static void deleteByHash() {
    //https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Array.html
    https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/FileWriter.html
    System.out.print("Enter Message Hash to delete: ");
    String searchHash = input.nextLine();
    int foundIndex = -1;
    
    // Find stored message with matching hash
    for (int i = 0; i < storedMessages.length; i++) {
        if (storedMessages[i] != null && messageHashes[i] != null && messageHashes[i].equals(searchHash)) {
            foundIndex = i;
            break;
        }
    }
    
    if (foundIndex == -1) {
        System.out.println("No stored message found with hash: " + searchHash);
        return;
    }
    
    // Confirm deletion
    System.out.println("Message to delete: " + storedMessages[foundIndex]);
    System.out.print("Are you sure? (1 for yes, 0 for no): ");
    int confirm = input.nextInt();
    input.nextLine();
    
    if (confirm == 1) {
    storedMessages[foundIndex] = null;    
    sentMessages[foundIndex] = null;     
    messageHashes[foundIndex] = null;
    sentRecipients[foundIndex] = null;
    messageIDs[foundIndex] = 0;
      // UPDATE the message type to "deleted" in JSON file
try {
    Path file = Paths.get("messages.txt");
    if (Files.exists(file)) {
        String content = new String(Files.readAllBytes(file));
        JSONArray messageArray = new JSONArray(content);
        
        for (int i = 0; i < messageArray.length(); i++) {
            JSONObject msg = messageArray.getJSONObject(i);
            if (msg.getString("Message Hash").equals(searchHash)) {
                msg.put("messageType", "disregarded");  
            }
        }
        
        try (FileWriter fileWriter = new FileWriter("messages.txt")) {
            fileWriter.write(messageArray.toString(4));
        }
    }
        } catch (Exception e) {
            System.out.println("Error updating JSON file: " + e.getMessage());
        }
        
        System.out.println("Message with hash " + searchHash + " has been deleted from both stored and sent messages.");
    } else {
        System.out.println("Deletion cancelled.");
    }
}
    public static void displayFullReport() {
    //Count stored messages
    storedCount = 0;
    for (int i = 0; i < storedMessages.length; i++) {
        if (storedMessages[i] != null) {
            storedCount++;
        }
    }
    
    if (storedCount == 0) {
        System.out.println("No stored messages found");
        return;
    }
    
    System.out.println("\n----FULL STORED MESSAGES REPORT-----");
    System.out.println("Total stored messages: " + storedCount);
    System.out.println("-----------------------------------");
    
    int displayCount = 1;
    for (int i = 0; i < storedMessages.length; i++) {
        if (storedMessages[i] != null) {
            System.out.println("\n--- STORED MESSAGE no" + displayCount + " ---");
            System.out.println("Message ID: " + messageIDs[i]);
            System.out.println("Message Hash: " + messageHashes[i]);
            System.out.println("Recipient: " + sentRecipients[i]);
            System.out.println("Message: " + storedMessages[i]);
            System.out.println("-----------------------------------");
            displayCount++;
        }
    }
}
}



