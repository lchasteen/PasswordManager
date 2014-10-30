/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SecurityProject;

/**
 *
 * @author Lane
 */
/**
* This class implements a log file to store error messages.
*/
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;


public final class EncryptedFile{
    
    
  public EncryptedFile(String fileName)throws Exception{            
    // Setup the setup file and encrypted file if necessary, if already setup then
    // just read the contents of the files and verify the usernName and password.
    if(!this.init(fileName, true))throw new Exception("Error occured creating file!");
    
  }
  
  /**
  * Constructor used for creating a new EncryptedFile and changing the password.
  */
  public EncryptedFile(String fileName, String userName, String oldPassword, String newPassword)throws Exception{            
    // Setup the setup file and encrypted file if necessary, if already setup then
    // just read the contents of the files and verify the usernName and password.
    if(!this.init(fileName, true))throw new Exception("Error occured creating file!");
    // If the file is new then save the user name and password into the encrypted file.
    
    if(!this.isNewFile()){   
        // If the file is not new then just validate the username and password.
        if(!this.compareUserName(userName) || !this.compareMasterPasswordPT(oldPassword)){             
            throw new Exception("User name or password is invalid!");        
            
        }else{
            // If the username and the old password are valid then change the password.
            if(!this.changeMasterPassword(oldPassword, newPassword))
                throw new Exception("Error setting password!  New password is not valid!");
        }
    }else throw new Exception("You must setup your user name and password.");    
  }
  
  
  /**
  * Constructor used for creating a new EncryptedFile and entering a new user name
  * and password.
  */
  public EncryptedFile(String fileName, String userName, String newPassword, String confirmPassword, boolean setNew)throws Exception{            
    // Setup the setup file and encrypted file if necessary, if already setup then
    // just read the contents of the files and verify the usernName and password.
    if(!this.init(fileName, true))throw new Exception("Error occured creating file!");
    // If the file is new then save the user name and password into the encrypted file.
   if(setNew){
        // Check to see if the passwords match.
        if(!newPassword.equals(confirmPassword))
            throw new Exception("Passwords must match!");
        // If the file is not new then just validate the username and password.
        if(!this.setUserName(userName)) 
            throw new Exception("User name is invalid!");        
        // Set new password throw error if there is a problem.
        if(!this.setMasterPassword(confirmPassword))            
            throw new Exception("Error setting new password!");        
   }
  }
  
  /*
  * This method sets the class variable FILE_NAME to the specified File.
  */
  private boolean init(String fileName, boolean createFile) throws Exception{
    boolean createNewFile = false;  
    File    newPath;
    String  tempFileName;
    // Create setup file if it doesn't exist then write fileName if it is not null.
    if(!this.initializeSetupFile(fileName))throw new Exception("Error ooccured creating file " + this.SETUP_FILE_NAME + "!");
    
    // Read the file name for the location of the encrypted file if this Stirng
    // is empty then return false, otherwise set the File FILE_NAME to the
    // location.
    if((tempFileName = this.readFileName()) == null)return false;
    
    // Create the File.
    FILE_NAME = new File(tempFileName);     
    // Get the parent directory name and make it into a File.
    newPath = new File(FILE_NAME.getParent());    
    // Make the directory if it does not exist.
    if(!newPath.exists())newPath.mkdir();    
    // Create file if it does not exist and the createFile flag is true.
    if(!FILE_NAME.exists() && createFile){    
        createNewFile = FILE_NAME.createNewFile();
        if(!createNewFile)return false;
        NEW_FILE = true;   
    }
    // If the file exists but is empty then set the NEW_FILE flag to true.
    if(FILE_NAME.length() == 0)NEW_FILE = true;
    
    // Check to see if you can read and write to the file
    if(!FILE_NAME.canWrite())return false;
    if(!FILE_NAME.canRead())return false;
    
    // Create the random access file for reading and writing.
    if(FILE_NAME == null) return false;
    this.RANDOM = new RandomAccessFile(FILE_NAME,"rw");
    if(this.RANDOM == null)return false;
    
    // Set the payload flag if the file contains more than just the password
    // and user name.
    if(FILE_NAME.length() > this.S2_RANGE){
        CONTAINS_PAYLOAD = true;
        
    }
    
    return true;
  }
  
  
  /*
   * This method sets the user name and password for the EncryptedFile object.
   */
  public boolean setUserNamePassword(String username, String password) throws Exception{
      if(!this.isNewFile()){   
        // If the file is not new then just validate the username and password.
        if(!this.compareUserName(username) || !this.compareMasterPasswordPT(password)) 
            throw new Exception("User name or password is invalid!");        
        }else throw new Exception("You must setup your user name and password.");    
      return true;
  }
  
  /*
  * This method creats the setup file if it does not exist.  If the argument
  * newFileName contain a string value and it is a valid file then this method
  * will write it to the setup file and return true.  
  * If the argument newFileName is null or empty and the setup file is empty 
  * then this method will write the default location for the encrypted file to 
  * the setup file and return true, otherwise if the setup file already
  * exists and is not empty and newFileName does not contain any value then this
  * method will return true.  In any other case this method will return false.
  */
  private boolean initializeSetupFile(String newFileName) throws IOException{
        File        nFile,fileToWrite;
        PrintWriter pw;
        
        nFile = new File(this.SETUP_FILE_NAME);
        
        // if nFile is not a file or is null then exit.
        if(nFile == null)return false;
        // Create the file if it does not exist.
        if(!nFile.exists()){
            
            nFile.createNewFile();
        }
        // Now if the file is empty and newFileName is null  
        if((newFileName == null || newFileName.isEmpty()) && (nFile.length() < 1)){
              // Now write the default entry into the file.
              pw = new PrintWriter(nFile);          
              pw.println(this.DEFAULT_ENCRYPTED_FILE_NAME);
              pw.flush();          
              pw.close();              
              return true;
        }
        // If the String argument newFileName contains a value then
        // write the default value for the location of the encrypted file.
        if(newFileName != null && !newFileName.isEmpty()){
              // Check to see if the String newFileName is a valid file.
              // if so then write that to the file.
              fileToWrite = new File(newFileName);              
              if(!fileToWrite.exists())return false; // if the file is not valid return false.
              // Now write new entry into the file.    
              pw = new PrintWriter(nFile);          
              pw.println(newFileName);
              pw.flush();          
              pw.close();              
        }
        
        return true;
    }
    
    /*
     * This method reads the setup file that contains the user defined 
     * 
     */
    private String readFileName() throws FileNotFoundException, IOException{
        
        BufferedReader          in;
        String                  temp;        
        String                  strRet;
        
        // Create the BufferedReader to read the file.
        in = new BufferedReader(new FileReader(this.SETUP_FILE_NAME));
        // Read each line in the file and add the String value to the variable list.        
        if((temp = in.readLine()) != null){
            in.close();
            return temp;
        }
            
        in.close();
        return null;
    }

  
  public boolean initializeEncryptedFile(){
      
      if(!this.getFileContents())return false;
      if(!this.decode())return false;
      return true;
  }
   
  /*
   * This method returns true if the file is a new file or false if it is not.
   */
  public boolean isNewFile(){  return this.NEW_FILE; }
  
  
  /*
   * This method returns true if the file contains a payload and false if not.
   */
  public boolean containsPayload() { return this.CONTAINS_PAYLOAD; }
  
  /**
  * Method used to write to log.
  */
  private boolean encode(){
    String      eMsg;
    
    if(RAW_PW_STRING == null || RAW_PW_STRING.isEmpty() || RAW_PW_STRING.length() < 1) return false;    
    
    try {
        // Seek the location after the main hashed user name and password.
        this.getFile().seek(this.S2_RANGE);            
        // Encrypt the main password string.
        eMsg = EncryptionSecurity.encryptText(RAW_PW_STRING, KEY);
        // Check to see if the result is invalid.
        if(eMsg == null || eMsg.isEmpty() || eMsg.length() < 1)return false;                
        // Write this string to the file.
        this.getFile().writeBytes(eMsg);
        
        // Set the payload flag.
        this.CONTAINS_PAYLOAD = true;        
    } catch (Exception ex) {
        return false;
    }
    return true;
    
  }
  
  private boolean decode(){
      String eMsg;
      // Check to see if the file contains anything.
      if(this.CONTENTS == null || this.CONTENTS.isEmpty() || this.CONTENTS.length() < 1){
          this.CONTAINS_PAYLOAD = false;          
          return false;
      }
      // Get just the encrypted data from the range till the end of the string.
      eMsg = this.CONTENTS.substring(S2_RANGE);
      
      try {
        // Decrypt the text and set the class variable RAW_PW_STRING.
        this.RAW_PW_STRING = EncryptionSecurity.decryptText(eMsg, KEY);
      
        if(this.RAW_PW_STRING == null)return false;
        
        
      }catch (Exception e){
          return false;
      }
      
      return true;
  }
  
  
  /*
   * This method gets the whole contents of the file and stores it into
   * a class variable.
   */
  private boolean getFileContents() {
      String msg;       
   
      try {
          // Check to see if the file contains anything.
          if(this.getFile().length() < 1){
              this.NEW_FILE = true;
              this.CONTAINS_PAYLOAD = false;
              return false;
          }          
          // Get the first position of the file.         
          this.getFile().seek(0);
          // Read the entire contents of the file as a String.
          msg = this.getFile().readLine();
          if(msg == null)return false;
          // Set the class variable with the entire contents of the file.
          this.CONTENTS = msg;
          // Check to see if you read any thing from the file.
          if(msg == null || msg.isEmpty() || msg.length() < 1) {
              this.NEW_FILE = true;
              this.CONTAINS_PAYLOAD = false;
              return false;
          }        
            
         
      }catch(IOException e){
          return false;
      }
      
      return true;
  }



  /**
  * Method closes file.
  */
  public boolean close(){
        try {
            this.getFile().close();
        } catch (IOException ex) {
            return false;
        }
    return true;
  }
  
  
  /*
   * This method checks to see if the old password matches the one on file then
   * sets the hashed value of the new password and adds it to the file.
   */
  private boolean changeMasterPassword(String oldPwd, String newPwd){
      
        String hash, oldHash;

        if((newPwd == null) || newPwd.length()< 1 )return false;            
        if((oldPwd == null) || oldPwd.length()< 1)return false;
        
        
        try{
              // Make sure that pwd is a valid password.
              if(!EncryptionSecurity.validatePlainText(newPwd))return false;
              hash = EncryptionSecurity.encode(newPwd);  // Hash pwd
              
              // Make sure that the old password matches what is on file.
              oldHash = EncryptionSecurity.encode(oldPwd);                 
              if(!this.compareMasterPassword(oldHash))return false;
              
              // Make sure the has is the correct length to write to file
              if(hash.length() < this.S1_RANGE)return false;

              if(this.getFile().length() < this.S1_RANGE)return false;            
              
              if(!this.initializeEncryptedFile())return false;
              
              
              // Decode the password string with the old password.
              if(!this.decode())return false;
              
              // Now create the new key from the new password.
              if(!this.createKey(newPwd))return false;
              // Now encrypt the new password string with the new key.
              if(!this.encode())return false;
              
              
              // Now get the location of the password string.
              this.getFile().seek(this.S1_RANGE);

              // Write the next 27 bytes to the file, the password
              this.getFile().write(hash.getBytes(),0, this.S1_RANGE);//, 28,54);
                  
              
         }catch(Exception e){
              return false;
         }
         return true;
  }
  
  /*
   * This method sets the hashed value of the password and adds it to the file.
   */
  private boolean setMasterPassword(String pwd){
      String hash;
      
      // Check out pwd; is it a valid string?
      if((pwd == null) || pwd.length()< 1 )return false;
      
      // Don't let the user change the password if you have a password string.
      if(this.containsPayload())return false;
      
      try{
          // Make usre that pwd is a valid password.
          if(!EncryptionSecurity.validatePlainText(pwd))return false;
          hash = EncryptionSecurity.encode(pwd);  // Hash pwd
          
          // Make sure the has is the correct length to write to file
          if(hash.length() < this.S1_RANGE)return false;
          
          if(this.getFile().length() < this.S1_RANGE)return false;            
          // Get the location of the ending of the user name
          this.getFile().seek(this.S1_RANGE);
          
          // Write the next 27 bytes to the file, the password
          this.getFile().write(hash.getBytes(),0, this.S1_RANGE);//, 28,54);
          
          // Set the key for encryption.
          if(!this.createKey(pwd))return false;
      }catch(Exception e){
          return false;
      }
      
      return true;
  }
  
  /*
   * This method sets the hashed value of the user name and adds it to the file.
   */
  private boolean setUserName(String user){
      String hash;
      
      
      if(!EncryptionSecurity.validateUserName(user) )return false;            
      
      try{
          
          hash = EncryptionSecurity.encode(user);
          
          // Make sure the hash is the correct length.
          if(hash.length() < this.S1_RANGE) return false;          
          
          // Write first 27 bytes to the file.
          this.getFile().write(hash.getBytes(),0, this.S1_RANGE );
      }catch(IOException e){
          return false;
      }catch(Exception e){
          return false;
      }
      
      return true;
  }
  
  /*
   * This method compares the plain text user, hashes it, the compares that 
   * to the hash value from the file.
   */
  private boolean compareUserName(String user){
      String uHash;
      String userFile;
      
      if(!EncryptionSecurity.validateUserName(user) )return false;            
      
      try {          
          uHash = EncryptionSecurity.encode(user);          
          // Get the file contents.
          if(!this.getFileContents()) return false;                
          userFile = this.CONTENTS.substring(0,S1_RANGE);
          // See if the hashes match.
          if(uHash.equals(userFile))return true;
      } catch (Exception ex) {return false;}
      
      return false;
  }
  
  
  /*
   * This method hashes the plain text password compares it to the 
   * hash value from the file.  Set the key value for encryption.
   */
  private boolean compareMasterPasswordPT(String uPwd){
      String hash;
      
      
      if((uPwd == null) || uPwd.length() < 1)return false;
      try {
            hash = EncryptionSecurity.encode(uPwd);
            if(hash == null)return false;          
            if(!this.compareMasterPassword(hash))return false;
            if(!this.createKey(uPwd))return false;
      } catch (Exception ex) {
            return false;
      }      
      return true;
  }
  
  /*
   * This method compares the hashed pwd to the hash value from the file.
   */
  private boolean compareMasterPassword(String uPwd){
      //String uPwd;
      String userFile;
      
      if((uPwd == null) || uPwd.length() < 1)return false;
      
      try {          
          
          // Get the file contents.
          if(!this.getFileContents())return false;
          userFile = this.CONTENTS.substring(S1_RANGE,S2_RANGE);
          // See if the hashes match.
          if(uPwd.equals(userFile))return true;
         
      } catch (Exception ex) {return false;}
      
      return false;
  }

  /*
   * This method adds a tag enclosed password item to the class variable String
   * payload.
   */
  public boolean addItem(String description, String pwd){
      String    newItem;      
      String [] existDesc;
      if(description.isEmpty() || description == null || description.length() < 1) return false;
      
      // Create the XML tags and enclose the description and password separated
      // by a comma.
      newItem = "<pwd>" + description + ", " + pwd + "</pwd>";      
      
      // Check to see if this is the first item.
      if(RAW_PW_STRING == null || RAW_PW_STRING.isEmpty() ){
          RAW_PW_STRING = newItem;
          
      }else{
          existDesc = this.getDescriptionValues();
          if(existDesc != null){
            // Loop through all the description values stored and check to see if you
            // used them already.
            for(int i = 0; i < existDesc.length; i++ )if(description.equals(existDesc[i]))return false;
          }     
          RAW_PW_STRING = RAW_PW_STRING + newItem;
          
      }      
      // Save the encrypted passwords into the file.
      if(!this.encode())return false;
      
      return true;
  }
  
  public boolean changeItemPassword(String description, String oldPw, String newPw){
      String    remItem, nextItem, lastItem;
      String    sStr, eStr, newItem, actualPw;
      int       start = 0, end = 0;
      long      fileLen = 0L;
      
      if(description.isEmpty() || description == null || description.length() < 1) return false;
      
      // Check to see if this is the first item.
      if(RAW_PW_STRING == null || RAW_PW_STRING.isEmpty() )return false;
      
      // Return false if the old password does not match the new password.
      actualPw = this.getPasswordValue(description);
      if(!oldPw.equals(actualPw))return false;
      
      // Get the start of the string, beginning with <pwd> and the description.
      remItem = "<pwd>" + description;      
      start = this.RAW_PW_STRING.indexOf(remItem);      
      nextItem = this.RAW_PW_STRING.substring(start);
      
      
      newItem = "<pwd>" + description.trim() + ", " + newPw.trim() + "</pwd>";
      
      // Get the index of the end of the string or the start of the next 
      // string.
      end = nextItem.indexOf("</pwd>");
      end += start + "</pwd>".length();
      lastItem = this.RAW_PW_STRING.substring(end);      
      
      // Get the first part of the string and the last part of the string without
      // the part that is to be deleted.
      sStr = this.RAW_PW_STRING.substring(0,start);
      eStr = this.RAW_PW_STRING.substring(end);
      
      // Now insert the new string.
      RAW_PW_STRING = sStr + newItem + eStr;
      try {
        // Seek the location after the main hashed user name and password.
        this.getFile().seek(this.S2_RANGE);            
        fileLen = this.getFile().length();
        // Delete everything excepte the name and password
        this.getFile().setLength(this.S2_RANGE);        
        // Now encode the new string.
        if(!this.encode())return false;
        
       } catch (Exception ex){ return false;}
      
      return true;
  }
  
  /*
   * This method removes an item (description and password) from the file.
   */
  public boolean removeItem(String description){
      String    remItem, nextItem, lastItem;      
      String    sStr, eStr;
      int       start = 0, end = 0;
      long      fileLen = 0L;
      
      if(description.isEmpty() || description == null || description.length() < 1) return false;
      
      // Check to see if this is the first item.
      if(RAW_PW_STRING == null || RAW_PW_STRING.isEmpty() )return false;
      
      // Get the start of the string, beginning with <pwd> and the description.
      remItem = "<pwd>" + description;      
      start = this.RAW_PW_STRING.indexOf(remItem);      
      nextItem = this.RAW_PW_STRING.substring(start);
      
      // Get the index of the end of the string or the start of the next 
      // string.
      end = nextItem.indexOf("</pwd>");
      end += start + "</pwd>".length();
      lastItem = this.RAW_PW_STRING.substring(end);      
      
      // Get the first part of the string and the last part of the string without
      // the part that is to be deleted.
      sStr = this.RAW_PW_STRING.substring(0,start);
      eStr = this.RAW_PW_STRING.substring(end);
      //System.out.println(sStr + eStr);
      // Now delete the string.
      RAW_PW_STRING = sStr + eStr;
      try {
        // Seek the location after the main hashed user name and password.
        this.getFile().seek(this.S2_RANGE);            
        fileLen = this.getFile().length();
        // Delete everything excepte the name and password
        this.getFile().setLength(this.S2_RANGE);        
        // Now encode the new string minus what was deleted.
        if(!this.encode())return false;
        
       } catch (Exception ex){ return false;}
      
      return true;
  }
  
  /*
   * This method returns all the description values from the total XML raw String
   * values contained in the class variable RAW_PW_STRING.
   */ 
  public String [] getDescriptionValues(){
      
      String [] retValues;
      String [] nValues;
      
      
      if(RAW_PW_STRING == null || RAW_PW_STRING.isEmpty())return null;
      
      retValues = Validation.breakUpString("pwd",RAW_PW_STRING);
      if(retValues == null || retValues.length < 1)return null;
      
      nValues = new String[retValues.length];
      if(nValues == null)return null;
      for(int i = 0; i < retValues.length; i++){
          nValues[i] = retValues[i].substring(0,retValues[i].lastIndexOf(","));
          nValues[i] = nValues[i].trim();                 
      }
      return nValues;
  }
  
  
  /*
   * This method returns all the password values from the total XML raw String
   * values contained in the class variable RAW_PW_STRING.
   */ 
  private String [] getPasswordValues(){
      
      String [] retValues;
      String [] nValues;
      
      if(RAW_PW_STRING == null || RAW_PW_STRING.isEmpty())return null;
      
      retValues = Validation.breakUpString("pwd",RAW_PW_STRING);
      if(retValues == null || retValues.length < 1)return null;
      
      nValues = new String[retValues.length];
      if(nValues == null)return null;
      for(int i = 0; i < retValues.length; i++){
          nValues[i] = retValues[i].substring(retValues[i].lastIndexOf(",") + 1);
          nValues[i] = nValues[i].trim();          
      }
      return nValues;
  }
      
  /*
   * This method returns the password value that matches the argument description
   * from the total XML raw String.
   */ 
  public String getPasswordValue(String description){
      
      String [] retValues;
      String nValue;
      
      if(RAW_PW_STRING == null || RAW_PW_STRING.isEmpty())return null;
      
      retValues = Validation.breakUpString("pwd",RAW_PW_STRING);
      if(retValues == null || retValues.length < 1)return null;
      
      //nValues = new String[retValues.length];
      //if(nValues == null)return null;
      for(int i = 0; i < retValues.length; i++){
          // get the description value
          String temp = retValues[i].substring(0, retValues[i].lastIndexOf(","));
          temp = temp.trim();
          if(temp.equals(description)){
              // get the password value
              nValue = retValues[i].substring(retValues[i].lastIndexOf(",") + 1);
              nValue = nValue.trim();
              return nValue;
          }
      }
      return null;
  }    
      
  /*
   * This method creates the SecretKey that will be used to encrypt the passwords.
   */
  private boolean createKey(String pwd){
        try {
            KEY = EncryptionSecurity.getKeyFromString(pwd);
        } catch (NoSuchAlgorithmException ex) {
            return false;
        } catch (InvalidKeySpecException ex) {
            return false;
        } catch (Exception ex) {
            return false;
        }
        
        return true;
  }

  
  // implementation details
  
  private File			FILE_NAME; // The actual file that is used.
  
  // This is the name of the file where the string for the actual file location
  // to read is contained.  Used for user defined file location.
  private String                SETUP_FILE_NAME = "setup.txt";
  
  // This is the name of the default encrypted file.
  private String                DEFAULT_ENCRYPTED_FILE_NAME = "pdata\\plist.ex";
    
  private boolean               NEW_FILE = false;  
  
  private boolean               CONTAINS_PAYLOAD = false;
  private int                   S1_RANGE = 27; 
  private int                   S2_RANGE = 54; 
  private SecretKey             KEY;
  // This is the raw XML string representing the text data for the passwords and
  // description data.
  private String                RAW_PW_STRING;
  
  private RandomAccessFile      RANDOM;
  
  // This is a string value to represent the entire file's contents
  private String                CONTENTS;
  
  private RandomAccessFile      getFile(){ return this.RANDOM;}
  
}