/*
 * EncryptionSecurity.java
 *
 * Created on January 31, 2008, 9:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package SecurityProject;

import java.io.UnsupportedEncodingException;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 *
 * @author lane
 */
public final class EncryptionSecurity {

    
    
    /** Creates a new instance of EncryptionSecurity. */
    public EncryptionSecurity() {
        
    }//*************************************************************************
    
    
    /** This method generates a SecretKey from a string based on the salt and iteration. */
    public static SecretKey getKeyFromString(String pwd) throws NoSuchAlgorithmException, InvalidKeySpecException, Exception{
        
        String      rPwd;
        
        pwd = pwd.trim();        
        
        rPwd = EncryptionSecurity.jumbleText(pwd);
        if(rPwd == null)throw new Exception("Error setting key value!");
        
        SecretKey spec = new SecretKeySpec( rPwd.getBytes(), "AES");
        
        return spec;
    }//*************************************************************************
    
    /*
     * This method jumbles the String pwd swaping up every other character and
     * seeding the String to make sure it is 16 characters long.
     */
    private static String jumbleText (String pwd){                
        String      rPwd;
        char[]      eP = new char[16];
        int         k = 0;
        
        if((pwd == null) || pwd.length() < 6) return null;
        
        // make sure that pwd is 16 bytes
        // if not repeat the original password to 
        // make up the difference        
        for(int i = 0; i < 16; i++){
           if(i < pwd.length())eP[i] = pwd.charAt(i);              
           else eP[i] = (char) pwd.charAt(k++);           
        }       
        // swap every other character        
        for(int j = 0; j < 15; j += 2){
            char temp1 = eP[j + 1];            
            char temp2 = eP[j];
            eP[j] = temp1;
            eP[j + 1] = temp2;
        }        
        rPwd = new String(eP);        
        
        return rPwd;
    }
    
    
    /** This method actually hashes the password using an encryption algorithm.
    * Once encrypted it is converted to a string from byte form using Base64 encoding. */
    public static String encode(String plainText) throws NoSuchAlgorithmException, UnsupportedEncodingException, Exception{
        MessageDigest md = null;
        String hash, strRet, jText;
        
        
        if(plainText.length() < 1 || plainText.isEmpty())return null;
        
        // Get the encryption algorithim
        md = MessageDigest.getInstance("SHA"); //step 2
        
        // Jumble the text
        jText = EncryptionSecurity.jumbleText(plainText);
        
        if(jText == null)throw new Exception("Error setting seed value!");
        
        // Update the md.
        md.update(jText.getBytes("UTF-8")); //step 3
        
        
        
        // Encrypt it.
        byte raw[] = md.digest(); //step 4
        
        
        hash = Base64.encodeBytes(raw, Base64.DONT_BREAK_LINES); //step 5        
        // subtract the last character from the hash
        strRet = hash.substring(0,hash.length()- 1); //step 6
        
        return strRet; //step 7
        
    }//*************************************************************************
    
    /*
     * This method encrypts a string using the AES algorithm and the private
     * key.
     */
    public static String encryptText(String arg, Key key) throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException{
        String strRet = "";
        byte [] eData;
        
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, key);
        eData = c.doFinal(arg.getBytes());
        
        strRet = Base64.encodeBytes(eData, Base64.DONT_BREAK_LINES);
        
        return strRet;
    }
    
    
    
    /*
     * This method decrypts a string based on the AES algorithm and the private
     * key.
     */
    public static String decryptText(String arg, Key key) throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException{
        String strRet = "";
        byte [] eData,ret;
        
    
        Cipher c = Cipher.getInstance("AES");

        c.init(Cipher.DECRYPT_MODE, key);
        eData = Base64.decode(arg, Base64.DONT_BREAK_LINES);
        ret = c.doFinal(eData);

        strRet = new String(ret);

        return strRet;
    }
    
    /** This method checks to see if plainText is a valid password. */
    public static boolean validatePlainText(String plainText){
        if(plainText.isEmpty() || plainText.length() < MIN_PLAIN_TEXT_LENGTH || plainText.length() > MAX_PLAIN_TEXT_LENGTH)return false;
        if(!Validation.isInString(plainText,Validation.NUMBER_ONLY))return false;        
        return true;
    }//*************************************************************************
    
    
    /** This method checks to see if plainText is a valid password. */
    public static boolean validateUserName(String plainText){
        if(plainText.isEmpty() || plainText.length() < 6 || plainText.length() > MAX_PLAIN_TEXT_LENGTH)return false;        
        return true;
    }//*************************************************************************
    
    
    /** This method compares the plain text password together to see if they are identical.
     * If not then return false. */
    public static boolean comparePlainText(String pt, String toCompare){
        if(pt.equals(toCompare))return true;        
        return false;
    }//*************************************************************************
    
    /** This method compares String pt with String toCompare and if they are different returns true. */
    public static boolean areDifferent(String pt, String toCompare){
        if(pt.compareTo(toCompare) == 0)return false;
        return true;
    }//*************************************************************************
    
        /*
     * This method changes the current password if the user name and current password
     * entered matches what is on file.
     */
    public static boolean validateChangePassword(String pwd, String newPwd, String changePwd){
        
            // Check the new password and the current one to see if they are different;
            // also, check the new password to see if it is the same as the confirm
            // password and validate the change password to see if it is
            // a legal password.                    
        if(!EncryptionSecurity.comparePlainText(newPwd,changePwd)){            
            Validation.pMsg("Your new password and confirmation password must match.");
            return false;
        }
        if(!EncryptionSecurity.areDifferent(pwd,newPwd)){
            Validation.pMsg("Your new password must be different then your old password.");
            return false;
        }                    
        if(!EncryptionSecurity.validatePlainText(changePwd)){
            Validation.pMsg("Invalid Password!");
            return false;
        }
        return true;
    }
    
    /*
     * This method sets validates the user and the new password and 
     * returs true if the new password if it is valid.
     */
    public static boolean validateNewPassword(String user, String pwd, String newPwd){
       
       
       if(!EncryptionSecurity.validateUserName(user)){
           Validation.pMsg("Invalid user name!");
           return false;
       }
       if(!EncryptionSecurity.validatePlainText(pwd)){
           Validation.pMsg("Invalid Password!");
           return false;
       }
       if(!EncryptionSecurity.comparePlainText(pwd,newPwd)){ 
           
           Validation.pMsg("Your new password and confirmation password must match.");
           return false;
       } 
       
       return true;
    }
    
    /*
     * This method sets validates the new password and sets the new password if
     * it is valid.
     */
    public static boolean validateNewPassword( String pwd, String newPwd){
       
       
       if(!EncryptionSecurity.validatePlainText(pwd)){
           Validation.pMsg("Invalid Password!");
           return false;
       }
       if(!EncryptionSecurity.comparePlainText(pwd,newPwd)){ 
           
           Validation.pMsg("Your new password and confirmation password must match.");
           return false;
       } 
       
       return true;
    }
    
    
    public static String            getPlainTextRequirements(){ return PASSWORD_PARAM_TEXT;}
    
    private static final int        MAX_PLAIN_TEXT_LENGTH = 20;
    private static final int        MIN_PLAIN_TEXT_LENGTH = 6;
    private static final String     PASSWORD_PARAM_TEXT = "Your password must be greater than 6 to 50 characters long and must contain at numeric and alpha numeric characters.";

}
