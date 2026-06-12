/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mongi
 */
public class Login {
    
   
    String Username;
    String Password;
    String firstName;
    String lastName;
    String cell;
    
    //Method 1 Checks if username is not > 5 characters and has and contains an underscore
    public boolean checkUsername(String Username) {
        return Username.contains("_") && Username.length() <=5 ;
    }
    
    //Method 2 checks if the password rules are met
    //password must have at least 8 characters, a capital letter, a digit and a special character
   
    public boolean checkPasswordComplexity(String Password) {
        if (Password.length() < 8) {
            return false;
        }
         
        boolean capitalLetter = false;
        boolean digit = false;
        boolean specialCharacter = false;
        
        for(int i = 0; i < Password.length(); i++) {
            char ch = Password.charAt(i); 
        
        if (Character.isUpperCase(ch)) {
            capitalLetter = true;
        }else if(Character.isDigit(ch)){
            digit = true;
        }else if (!Character.isLetterOrDigit(ch)){
            specialCharacter = true;
        }
        }
        return capitalLetter && digit && specialCharacter;
   }
    //Method 3 regex
    //Regex pattern from Java Guides Regex Tutorial
//Source: https://www.javaguides.net/2019/12/java-regex-tutorial-regular-expressions.html
//pattern \+27\d{9} means: "+27" followed by exactly 9 digits
   
    public boolean checkCellPhoneNumber(String cell) {
       return cell != null && cell.matches("\\+27\\d{9}");
    }
   
           //Method 4 registration message
    
           public String registerUser(String Username, String Password, String firstName, String lastName, String cell) {
             
               if (!checkUsername(Username)) {
                   return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
                   
               }
               if(!checkPasswordComplexity(Password)) {
                   return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
               }
               if (!checkCellPhoneNumber(cell)){
                   return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
               }
               
               this.Username = Username;
               this.Password = Password;
               this.firstName = firstName;
               this.lastName = lastName;
               this.cell = cell;
               
               return "Welcome " + firstName + ", " + lastName + " " + "it is great to see you.";
           }
           //Method 5 verifies login details
    public boolean loginUser(String Username, String Password) {
        return this.Username.equals(Username) && this.Password.equals(Password);
    }
           //Method 6 Login message
           public String returnLoginStatus(String Username, String Password) {
               if(loginUser(Username, Password)) {
                   return firstName + " " + lastName + " It's great to see you again";
               }else {
                   return "Username or Password is incorrect";
               }
           }
}

