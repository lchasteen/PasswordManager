/*
 * Validation.java
 *
 * Created on June 2, 2007, 5:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Lane and Janet
 */
package SecurityProject;

import javax.swing.JOptionPane;





public class Validation {
    
    
 /** This method converts a string name into a name format string
  * such as making the first letter of each word uppercase. */
 public static String convertToName(String name){
     
     String converted = "";
     char upper;
     
     if(name.isEmpty())return null;
     if(name.length()< 1)return null;
     if(name.compareTo("") == 0) return null;
     
     // trim name and convert first letter to upper case
     name = name.trim();     
     upper = name.toUpperCase().charAt(0);          
     converted = String.valueOf(upper).concat(name.substring(1,name.length()).toLowerCase());
     
     // if string is only one letter return it uppercase
     if(converted.length() == 1){      
         //System.out.println(converted);    
         return converted;
     }// end if
     // make any letters uppercase after any white spaces     
        try {          
            for(int i = 1; i < converted.length(); i++){
                // if character is a white space and length is greater than i + 1
                // we do not want to go beyond array length
                if(converted.charAt(i) == ' ' && (converted.length() > i + 1)){
                    String temp = converted.substring(i+1,i+2).toUpperCase();
                    converted = replaceStringAt(converted,i+1,temp);
                }// end if        
            }// end for
        } catch(Exception e) {
            return null;
        }// end catch     
     return converted;
 }//****************************************************************************
 
 /** This method replaces the string strToModify at the specified position pos with
  * the string replaceWith. */     
 public static String replaceStringAt(String strToModify, int pos, String replaceWith){     
     return strToModify.substring(0,pos) + replaceWith + strToModify.substring(pos + 1);
 }//****************************************************************************

 /** This method checks to see if the String vDate is in the correct syntax
 *  mm/dd/yyyy. */    
 public static boolean validateDate(String vDate){        
        
        if(vDate.isEmpty())return false;
        if(vDate.length()!= 10)return false;                
        
        // validates vDate to mm/dd/yyyy
        // validates to make sure the syntax is correct        
        String strNumber = "1234567890";        
        int mm = 0,dd = 0,yyyy =0;
        //month
        
        if(validateChar(vDate.charAt(0),strNumber)) return false;
        if(validateChar(vDate.charAt(1),strNumber)) return false;
        if(validateChar(vDate.charAt(2),"/")) return false;
        //date
        if(validateChar(vDate.charAt(3),strNumber)) return false;
        if(validateChar(vDate.charAt(4),strNumber)) return false;
        if(validateChar(vDate.charAt(5),"/")) return false;
        //year
        if(validateChar(vDate.charAt(6),strNumber)) return false;
        if(validateChar(vDate.charAt(7),strNumber)) return false;
        if(validateChar(vDate.charAt(8),strNumber)) return false;
        if(validateChar(vDate.charAt(9),strNumber)) return false;        
        
        
        // check to see if the date is logically correct such as are there there are not 52 days in december
        mm = Integer.valueOf(vDate.substring(0,2));
        dd = Integer.valueOf(vDate.substring(3,5));
        yyyy = Integer.valueOf(vDate.substring(6,10));        
        
        if(mm > 12) return false;        
        if(Validation.isDayValid(dd,mm - 1,yyyy))return false;
        
        return true;
 }//****************************************************************************
    
    
 /** This method checks to see if any characters in strInvalid are located
 * in strReturn if so then returns false. */
 public static boolean validateString(String strReturn, String strInvalid){
        // fucntion checks to see if any characters in strInvalid are located in strReturn
        if(strReturn.isEmpty())return false;
        if(strReturn.length()< 1)return false;
        if(strInvalid.isEmpty())return false;
        if(strInvalid.length()< 1)return false;
        
        for(int i = 0; i < strReturn.length(); i++){
            for(int j = 0; j < strInvalid.length(); j++){      
                if(strReturn.charAt(i) == strInvalid.charAt(j)){        
                    return false;
                } // end if(strReturn.charAt(i) == ' ' then){
            }// end for(int j = 0; j < strInvalid.length(); j++){
        } // end for(int i = 0; i < strReturn.length(); i++){    
        
        return true;
 }//****************************************************************************
    
 
  /** This method checks to see if any characters in strToContain are the only 
   * characters located in strReturn if so then returns true. */
 public static boolean isOnlyInString(String strReturn, String strToContain){
        // fucntion checks to see if strings meet initial requirements
        if(strReturn.isEmpty())return false;
        if(strReturn.length()< 1)return false;
        if(strToContain.isEmpty())return false;
        if(strReturn.length()<1)return false;
        
        boolean exitFlag = true;
        int j = 0;
        
        // this is the outer loop
        outerloop:
        for(int i = 0; i < strReturn.length(); i++){
           // must go through whole string to see if 
           // the characters are there
           if(!exitFlag)return false;
           for(j = 0; j < strToContain.length(); j++){
               if(strReturn.charAt(i) == strToContain.charAt(j)){
                   exitFlag = true;
                   continue outerloop;
               }
               exitFlag = false;                            
           }    
        } // end for(int i = 0; i < strReturn.length(); i++){    
        if(!exitFlag) return false;
        
        return true;
 }//****************************************************************************
 
 /** This method checks to see if any characters in strToContain are located
 * int strReturn if so then returns true. */ 
 public static boolean isInString(String strReturn, String strToContain){
        // fucntion checks to see if strings meet initial requirements
        if(strReturn.isEmpty() || strReturn.length()< 1)return false;        
        if(strToContain.isEmpty() || strReturn.length()<1)return false;
        
        for(int i = 0; i < strReturn.length(); i++){
           // must go through whole string to see if 
           // the characters are there        
           for(int j = 0; j < strToContain.length(); j++){
               if(strReturn.charAt(i) == strToContain.charAt(j)){
                   return true;
               }// end if               
           } // end for j   
        } // end for i                
        return false;
 }//****************************************************************************
 
 /** This method checks to see if string email is a valid email address. */
 public static boolean validateEmail(String email){
     
     if(email.isEmpty())return false;
     if(email.length()< 1)return false;               
     // check to see if email ends with .com     
     if(!email.endsWith(".com"))return false;     
     // email connot contain spaces
     if(email.contains(" "))return false;
     
     
     return true;
 }//****************************************************************************
    
 /** This method checks to see if the string strPos is a valid
 * position code of range 1-4. */
 public static boolean validatePosition(String strPos){
        if(!validateNumber(strPos,1,1,1,4)) return false;
        if(strPos.contains(" "))return false;
        return true;
 }//****************************************************************************
    
 /** This method checks to see if the string strWWID is a valid WWID. */     
 public static boolean validateWWID(String strWWID){
        if(!validateNumber(strWWID,4,12))return false;        
        return true;
 }//****************************************************************************

  /** This method checks to see if the string strVID is a valid VID. */     
 public static boolean validateVID(String strVID){
        if(!validateNumber(strVID,1,12))return false;        
        return true;
 }//****************************************************************************

  /** This method checks to see if the string strCID is a valid CID. */     
 public static boolean validateCID(String strCID){
        if(!validateNumber(strCID,1,12))return false;        
        return true;
 }//****************************************************************************

 /** This method checks to see if the string strPrivilege is a valid
 * number and within the specified range of PRIVILEGE_MIN and PRIVILEGE_MAX. */
 public static boolean validatePrivilege(String strPrivilege){        
        
        if(strPrivilege.isEmpty())return false;
        if(strPrivilege.length()< 3)return false;             
        
        // check to see if the number is valid and within range
        if(!validateNumber(strPrivilege, PRIVILEGE_STRING_LENGTH , PRIVILEGE_STRING_LENGTH, PRIVILEGE_MIN, PRIVILEGE_MAX))return false;   
        return true;
 }//****************************************************************************
        
 /** This method checks to see if the character in charReturn are located
 * in strInvalid if so then returns true. */
 public static boolean validateChar(char charReturn, String strInvalid){
        
        // fucntion checks to see if any characters in strInvalid are equal to strReturn        
        for(int j = 0; j < strInvalid.length(); j++){      
            if(charReturn == strInvalid.charAt(j)){                
                return false;
            } // end if(strReturn.charAt(i) == ' ' then){
        }// end for(int j = 0; j < strInvalid.length(); j++){      
         return true;
 }//****************************************************************************
    
 /** This method validates the string vTime to see if it is in the correct time
 * format such as 2359 to 0000. */
 public static boolean validateTime(String vTime){
        
        if(vTime.isEmpty())return false;
        if(vTime.length()<4)return false;
        if(vTime.contains(" "))return false;
        
        String strNumber = "1234567890";        
        
        // vTime has to be all numbers
        if(validateChar(vTime.charAt(0),strNumber)) return false;
        if(validateChar(vTime.charAt(1),strNumber)) return false;
        if(validateChar(vTime.charAt(2),strNumber)) return false;
        if(validateChar(vTime.charAt(3),strNumber)) return false;
        // additonal validating for symbols and letters this is not necessary
        if(!validateString(vTime,TIME_INVALID))return false;
        
        // validate for logical errors such as 2360 or -0000 or 2817
        int hh = 0,mm = 0;
        
        // check to see if vTime is logically correct
        // for military time such as the hours are less than 23
        // and minutes are less that 60
        hh = Integer.valueOf(vTime.substring(0,2));
        mm = Integer.valueOf(vTime.substring(2,4));
        System.err.println(String.valueOf(hh)+ " "+ String.valueOf(mm));
        if((hh > 23) || (hh < 0))return false;
        if((mm > 59) || (mm < 0))return false;
        
        return true;
 }//****************************************************************************
    
 /** This method checks to see if strShift is the correct shift format
 * A,B,C,or D. */
 public static boolean validateShift(String strShift){
        
        if(strShift.isEmpty())return false;
        if(strShift.length()<1)return false;
        if(strShift.length()>1)return false;
        if(strShift.contains(" "))return false;   
                
        // check to see if strShift is A,B,C, or D
        if(strShift.compareTo("A") == 0) return true;
        else if(strShift.compareTo("B") == 0) return true;
        else if(strShift.compareTo("C") == 0) return true;
        else if(strShift.compareTo("D") == 0) return true;
        else return false;
                
 }//****************************************************************************
    
 /** This method validates strName as to make sure that it 
 * is in the correct format with no numbers or symbols. */
 public static boolean validateName(String strName){        
        if(strName.isEmpty())return false;
        if(strName.length() < 1)return false;        
        if(strName.contains(" ")) return false;
        
        if(!validateString(strName,NAME_INVALID))return false;
        
        return true;
 }//****************************************************************************

    
 /**
     * This method validates a string as a valid integer based on isOnlyInString and checks to see if it is in range
     * based on its length minimun length and maximum length.
     */
 public static boolean validateNumber(String strNum, int minLen, int maxLen){
        
        if(strNum.isEmpty())return false;
        if(strNum.length() < minLen)return false;
        if(strNum.length() > maxLen)return false;        
        if(strNum.contains(" "))return false;
        if(!isOnlyInString(strNum,NUMBER_ONLY))return false;
        
        return true;
 }//****************************************************************************
    
    
 /**
     * This method validates a string as a valid integer based on isOnlyInString and checks to see if it is in range
     * based on its length minimum length and maximum length, minimum range and maximum range.
     */
 public static boolean validateNumber(String strNum, int minLen, int maxLen, int minRange, int maxRange){
        int valueToCheck = 0;
        
        if(strNum.isEmpty())return false;
        // check range of string length
        if(strNum.length() < minLen)return false;
        if(strNum.length() > maxLen)return false;        
        if(strNum.contains(" "))return false;
        
        if(!isOnlyInString(strNum,NUMBER_ONLY))return false;
        
        // convert to integer and test for range
        valueToCheck = Integer.valueOf( strNum);
        if(valueToCheck < minRange) return false;
        if(valueToCheck > maxRange) return false;
        
        
        return true;
 }//****************************************************************************
    
    /** Method checks to see if day is valid for the month and year. */
    public static boolean isDayValid(int iDay, int iMonth, int iYear){
    // Checks to see if iDay is in iMonth for iYear. 
    // For example there is not 29 days in Febuary in 2007. 

    if((iMonth > 11) || (iMonth < 0)) return false;
    
    if((iDay < 1) || (iDay > 32)) return false;

    int idom [] = {31,28,31,30,31,30,31,31,30,31,30,31};   
    // is leap year
    if(((iYear % 4) == 0) && (iMonth == 1)){
      if(iDay > 29 ) return false;            
    }
    else{        
      if(iDay > idom[iMonth] )return false;        
    }            
    return true;
}//*************************************************************************

    /*
     * This method breaks up a StringBuffer strToDivide into its substrings
     * based on the String regex which is an XML style tag without the <> characters.
     * Example: breakUpString("a", strToDivide) where strToDivide = "<a>This</a><a>String</a>
     * Result: {"This","String"}
     * 
     */
    public static String[]  breakUpString(String regex, String toDivide){            
        if(regex.isEmpty() || regex.length() <= 1)return null;
        if(toDivide == null) return null;

        String startTag = "", endTag = "";
        String [] strRet;

        // Create tags: Enclose String regex with <regex> or </regex>.
        startTag = "<" + regex.trim() + ">";
        endTag = "</" + regex.trim() + ">";            


        strRet = toDivide.split(endTag); // Split the string into substrings; using ending tag.
        for(int i = 0; i < strRet.length;  i++) strRet[i] = strRet[i].substring(startTag.length()); // Get rid of the starting tag.                          

        return strRet;
    }

     /*
     * This method breaks up a StringBuffer strToDivide into its substrings
     * based on the String regex which is an XML style tag without the <> characters.
     * Example: breakUpString("a", strToDivide) where strToDivide = "<a>This</a><a>String</a>
     * Result: {"This","String"}
     * 
     */
    public static String[]  breakUpString(String regex, StringBuffer strToDivide){            
        if(regex.isEmpty() || regex.length() <= 1)return null;
        if(strToDivide == null) return null;
        String tempStr;
        String [] strRet;

        tempStr = strToDivide.toString();

        strRet = Validation.breakUpString(regex, tempStr);
        return strRet;
    }


    public static String[]  breakUpStringFromTag(String regex, StringBuffer strToDivide){

        if(regex.isEmpty() || regex.length() <= 1)return null;
        if(strToDivide == null) return null;

        String startTag = "", endTag = "", toDivide;
        String [] strRet;

        // Create tags: Enclose String regex with <regex> or </regex>.
        startTag = "<" + regex.trim() + ">";
        endTag = "</" + regex.trim() + ">";            

        toDivide = strToDivide.toString();            
        strRet = toDivide.split(endTag); // Split the string into substrings; using ending tag.
        for(int i = 0; i < strRet.length;  i++) strRet[i] = strRet[i] + endTag; // add ending tag.                          

        return strRet;
    }
    
    
    /** This method uses JOptionPane.showMessageDialog to show an error message. */
    public static boolean pError(String msg){        
        JOptionPane.showMessageDialog(null,msg,"Error",JOptionPane.ERROR_MESSAGE);
        return true;
    }//*************************************************************************

    /** This method uses JOptionPane.showMessageDialog to show an error message. */
    public static boolean pMsg(String msg){        
        JOptionPane.showMessageDialog(null,msg,"Error",JOptionPane.INFORMATION_MESSAGE);
        return true;
    }//*************************************************************************
    
  
    public static String DATE_INVALID = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_-=+{[}};':,<.>`~\\|\"";
    public static String TIME_INVALID = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ/!@#$%^&*()_-=+{[}};':,<.>`~\\|\"";
    public static String NAME_INVALID = "/!@#$%^&*()_-=+{[}};':,<.>`~\\|\"1234567890";
    public static String NUMBER_ONLY = "1234567890";
    public static String APHA_ONLY = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String PASSWORD = "~\\/|.";
    
    //public static int PASSWORD_MIN_LENGTH = 6;
    //public static int PASSWORD_MAX_LENGTH = 100;
    public static int PRIVILEGE_STRING_LENGTH = 3;
    public static int PRIVILEGE_MAX = 400;
    public static int PRIVILEGE_MIN = 100;
}
