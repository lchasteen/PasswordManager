/*
 * OrcCoveragePanel.java
 *
 * Created on September 21, 2008, 6:19 AM
 */


package passwordmanager;

import SecurityProject.EncryptedFile;
import SecurityProject.Validation;
import javax.swing.DefaultListModel;




/**
 *
 * @author  Lane and Janet
 */
public class PasswordFrame extends javax.swing.JFrame {
    
    
    public PasswordFrame(){
        
        
        
        try{
            loginF = new LoginForm(null, true);
            if(!loginF.showLoginDialog())System.exit(0);
            
            // Get a reference to the EncryptedFile.
            FILE = this.loginF.getFile();
            if(FILE == null)System.exit(0);
        }catch(Exception e){            
            System.exit(0);
        }
        
        initComponents();
        initLogic();
    }
    
    
    
    
    /** 
     * This method verifies day, month, and year then sets form variables. 
     */    
    private void initLogic(){
        
        String []   desc;
        boolean     res;
        
        
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.MODEL = new DefaultListModel();
        
        if(this.getFile().containsPayload()){            
            res = this.getFile().initializeEncryptedFile();
            desc = this.getFile().getDescriptionValues();
            
            // Add one element at a time from the description string desc
            // into the listbox model.  Each description represents a password
            // and description pair contained in the encrypted file.
            if(desc != null && res){
                for(int i = 0; i < desc.length; i++)this.MODEL.add(i, desc[i]);
            }
        }
        
        this.nameList.setModel(MODEL);        
        this.pack();        
    }
    
    private boolean deleteDescription(){
        int valueSelected = -1; 
        
        valueSelected = this.nameList.getSelectedIndex();
        
        if(!MODEL.isEmpty() && valueSelected >= 0){
            // Delete the item from the list box.
            MODEL.remove(valueSelected);
            // Delete the item from the file.
            this.getFile().removeItem(this.getDescription());
            this.clearText();
        }
        
        return true;
    }
    
    /*
     * This method sets a new password using the EncryptedFile for the entry selected.
     */
    private boolean newPassword(){
        
        String pwd, newPwd;
       
        pwd = this.getPassword();
        newPwd = this.getNewPassword();
        
        if(pwd == null || pwd.length() < 1)return false;
        if(newPwd == null || newPwd.length() < 1) return false;
        
        // Check to see if the password and new password are the same.
        if( !pwd.equals(newPwd)){
            Validation.pMsg("Your new password must be the same as your password value!");            
            return false;
        }        
        // If they are the same then add the item to the file.
        if(!this.getFile().addItem(this.getDescription(), this.getPassword())){        
            Validation.pMsg("Error adding description!  You may have already have used this descripiton value!");            
            return false;        
        }
        // Now add the item to the list.
        if(!this.addToList(this.getDescription())) return false;
        
        return true;
    }
    
    
    private boolean changePassword(){
        
        if(!this.getDescription().isEmpty() && !this.getPassword().isEmpty() && !this.getNewPassword().isEmpty() && !this.getConfirm().isEmpty()){
            // Check to see if the new password matches the confirmation password
            if(!this.getNewPassword().equals(this.getConfirm())){
                Validation.pMsg("Your new password does not match the password to verify.");
                return false;
            }
            // Now change the password.
            if(!this.getFile().changeItemPassword(this.getDescription(), this.getPassword(), this.getConfirm())){
                Validation.pError("Error changing password!");
                return false;
                
            }
        }
        return true;
    }
    
    
    private boolean selectListItem(){
        String desc; 
        int selValue = -1;
        
        selValue = this.nameList.getSelectedIndex();
        if(! this.MODEL.isEmpty() && selValue >= 0){
            desc = (String) this.nameList.getSelectedValue();
            if(desc != null || !desc.isEmpty()){
                setNewDescription(desc);
                return true;            
            }
        }
        return false;
    }
    
    
    private boolean addToList(String description){
        
        if(description == null || description.isEmpty() || description.length() < 1)return false;        
        if(MODEL.contains(description))return false;       
        MODEL.addElement( description);
       
        
        return true;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        newPasswordTF = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        descriptionTF = new javax.swing.JTextField();
        passwordTF = new javax.swing.JPasswordField();
        confirmTF = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        nameList = new javax.swing.JList();
        changeButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        newButton = new javax.swing.JButton();
        showLB = new javax.swing.JLabel();
        showTF = new javax.swing.JTextField();
        deleteButton = new javax.swing.JButton();
        changeCB = new javax.swing.JCheckBox();

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("New");

        newPasswordTF.setEditable(false);
        newPasswordTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newPasswordTFActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Password");

        descriptionTF.setEditable(false);

        passwordTF.setEditable(false);
        passwordTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordTFActionPerformed(evt);
            }
        });

        confirmTF.setEditable(false);
        confirmTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmTFActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Description");

        addButton.setMnemonic('A');
        addButton.setText("Add Item");
        addButton.setEnabled(false);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        nameList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        nameList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        nameList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                nameListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(nameList);

        changeButton.setMnemonic('C');
        changeButton.setText("Change");
        changeButton.setEnabled(false);
        changeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeButtonActionPerformed(evt);
            }
        });

        exitButton.setMnemonic('x');
        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Description");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Verify");

        newButton.setMnemonic('N');
        newButton.setText("New");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });

        showLB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        showLB.setText("Show Password");
        showLB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                showLBMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                showLBMouseExited(evt);
            }
        });

        showTF.setEditable(false);
        showTF.setFocusable(false);
        showTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showTFActionPerformed(evt);
            }
        });

        deleteButton.setMnemonic('D');
        deleteButton.setText("Delete");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        changeCB.setMnemonic('h');
        changeCB.setText("Change");
        changeCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeCBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(changeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                        .addComponent(exitButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(showLB, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(showTF, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(newButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(confirmTF, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(newPasswordTF, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(passwordTF, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(descriptionTF, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(changeCB)))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabel3, jLabel4, jLabel6});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showLB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(descriptionTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(newButton)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changeCB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newPasswordTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(changeButton)
                    .addComponent(deleteButton)
                    .addComponent(exitButton))
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void passwordTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordTFActionPerformed

    private void newPasswordTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newPasswordTFActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_newPasswordTFActionPerformed

    private void confirmTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_confirmTFActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        // TODO add your handling code here:
        this.clearText();
        setNewTextEditable(true);
        this.descriptionTF.requestFocus();
    }//GEN-LAST:event_newButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        if(this.newPassword()){
            setNewTextEditable(false);
            this.clearText();
        }
        
        
    }//GEN-LAST:event_addButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_exitButtonActionPerformed

    private void showTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_showTFActionPerformed

    private void showLBMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showLBMouseEntered
        // TODO add your handling code here:
        String  txt;
        txt = this.descriptionTF.getText();
        if(txt != null || !txt.isEmpty() || txt.length() > 1){
            this.showTF.setText(this.getFile().getPasswordValue(txt));
        }
    }//GEN-LAST:event_showLBMouseEntered

    private void showLBMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showLBMouseExited
        // TODO add your handling code here:
        this.showTF.setText("");
    }//GEN-LAST:event_showLBMouseExited

    private void nameListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_nameListValueChanged
        this.selectListItem();
    }//GEN-LAST:event_nameListValueChanged

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        this.deleteDescription();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void changeCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeCBActionPerformed
        // TODO add your handling code here:
        if(this.changeCB.isSelected() && !this.getDescription().isEmpty()){
            this.confirmTF.setEditable(true);
            this.newPasswordTF.setEditable(true);
            this.changeButton.setEnabled(true);
        }else{
            this.confirmTF.setEditable(false);
            this.newPasswordTF.setEditable(false);
            this.changeButton.setEnabled(false);
            this.changeCB.setSelected(false);
        }
    }//GEN-LAST:event_changeCBActionPerformed

    private void changeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeButtonActionPerformed
        // TODO add your handling code here:
        if(this.changePassword()){
            this.clearText();            
            this.setNewTextEditable(false);            
            this.selectListItem();
        }
        
    }//GEN-LAST:event_changeButtonActionPerformed
    
    private void clearText(){
        this.descriptionTF.setText("");
        this.passwordTF.setText("");
        this.confirmTF.setText("");
        this.newPasswordTF.setText("");
    }
    
    private void setNewDescription(String description){
        this.descriptionTF.setText(description);
        this.passwordTF.setText(this.getFile().getPasswordValue(description));        
        this.deleteButton.setEnabled(true);
    }
    
    private void setNewTextEditable(boolean editable){
        this.descriptionTF.setEditable(editable);
        this.passwordTF.setEditable(editable);
        this.newPasswordTF.setEditable(editable);
        this.addButton.setEnabled(editable);
        this.deleteButton.setEnabled(false);
        this.changeButton.setEnabled(false);
        this.confirmTF.setEditable(false);
        this.changeCB.setSelected(false);
        
    }
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        
       
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){                
                new PasswordFrame().setVisible(true);
            }
        });
        
        
        
        
        
        
    }
    
    
    private String                                  getPassword(){return String.copyValueOf(this.passwordTF.getPassword());}
    private String                                  getConfirm(){return String.copyValueOf(this.confirmTF.getPassword());}
    private String                                  getNewPassword(){return String.copyValueOf(this.newPasswordTF.getPassword());}    
    private String                                  getDescription(){return this.descriptionTF.getText().trim();}
   
    private String                                  DAY;
    private String                                  MONTH; 
    private String                                  YEAR; 
    public static int                               DEFAULT_WIDTH = 450;
    public static int                               DEFAULT_HEIGHT = 336;            
    LoginForm                                       loginF;
    EncryptedFile                                   FILE;
    
    
    private EncryptedFile                           getFile(){return this.FILE;}
    DefaultListModel                                MODEL;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton changeButton;
    private javax.swing.JCheckBox changeCB;
    private javax.swing.JPasswordField confirmTF;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField descriptionTF;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList nameList;
    private javax.swing.JButton newButton;
    private javax.swing.JPasswordField newPasswordTF;
    private javax.swing.JPasswordField passwordTF;
    private javax.swing.JLabel showLB;
    private javax.swing.JTextField showTF;
    // End of variables declaration//GEN-END:variables
    
}
