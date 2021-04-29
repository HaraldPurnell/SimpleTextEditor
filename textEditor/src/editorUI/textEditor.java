/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editorUI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.inet.jortho.SpellChecker;
import com.inet.jortho.FileUserDictionary;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Harald Purnell
 */
public class textEditor extends javax.swing.JFrame {

    private final FileFilter filter;
    private final JFileChooser chooser;
    private final JFrame diaFrame;
    
    /**
     * Creates new form textEditor
     */
    public textEditor() {
        this.filter = new FileNameExtensionFilter("Text Files", "txt","rtf","doc");
        this.chooser = new JFileChooser();
        this.diaFrame = new JFrame();
        initComponents();
        initSpellChecker();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textBody = new javax.swing.JTextPane();
        Save = new javax.swing.JButton();
        clearAll = new javax.swing.JButton();
        fileType = new javax.swing.JComboBox<>();
        fileTypeLabel = new javax.swing.JLabel();
        openFile = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Text Editor");

        jScrollPane1.setViewportView(textBody);

        Save.setText("Save");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        clearAll.setText("New");
        clearAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllActionPerformed(evt);
            }
        });

        fileType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "txt", "rtf", "doc" }));

        fileTypeLabel.setText("Save File Type");

        openFile.setText("Open");
        openFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(clearAll)
                        .addGap(18, 18, 18)
                        .addComponent(openFile)
                        .addGap(18, 18, 18)
                        .addComponent(fileTypeLabel)
                        .addGap(18, 18, 18)
                        .addComponent(fileType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Save)
                        .addGap(0, 620, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearAll)
                    .addComponent(openFile)
                    .addComponent(fileTypeLabel)
                    .addComponent(fileType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Save))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /**
     * This method is called from within the constructor to initialize the spell checker.
     */
    private void initSpellChecker() 
    {
        String userDictionaryPath = "/dictionary/";
        SpellChecker.setUserDictionaryProvider(new FileUserDictionary(userDictionaryPath)); 
        SpellChecker.registerDictionaries(getClass().getResource(userDictionaryPath), "en");
        
        SpellChecker.register(textBody);
    }
    
    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed

        /* Natural Support: txt, rtf, doc 
        TODO: Library Support: pdf (PDDocument), docx
        */
        
        configureFileChooser(chooser, filter);
        
        int userSelection = chooser.showSaveDialog(diaFrame);
        
        String fileExt = fileType.getSelectedItem().toString(); // Save file type
        String fileCont = textBody.getText();

        if (userSelection == JFileChooser.APPROVE_OPTION) {

            try (FileWriter fw = new FileWriter(chooser.getSelectedFile()+"."+fileExt)) {
                fw.write(fileCont);
                fw.flush();
                fw.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(diaFrame, "Error!");
            }
            
        }
    }//GEN-LAST:event_SaveActionPerformed

    // Button: "New" is synomous with delete all
    private void clearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllActionPerformed
        textBody.setText("");
    }//GEN-LAST:event_clearAllActionPerformed

    private void openFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileActionPerformed

        configureFileChooser(chooser, filter);
        
        int userSelection = chooser.showOpenDialog(diaFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile()))){
                
                String currentLine;
                String finalOutput = "";

                while ((currentLine = reader.readLine()) != null) 
                    finalOutput = finalOutput + currentLine + "\n";
                
                
                textBody.setText(finalOutput);
                
                //String currentLine = reader.readLine();

                reader.close();

            } catch (IOException ex) {
                Logger.getLogger(textEditor.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        
    }//GEN-LAST:event_openFileActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(textEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(textEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(textEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(textEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new textEditor().setVisible(true);
        });
    }
    
    // Configuration settings for file filter
    public void configureFileChooser(JFileChooser fc, FileFilter ff)
    {
        fc.addChoosableFileFilter(ff);//ff added to filechooser
        fc.setFileFilter(ff);//st ff as default selection
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);//User must select a file not folder
        fc.setMultiSelectionEnabled(false); // Disables selection of multiple files
        fc.setAcceptAllFileFilterUsed(false); // Disables "Accept All" filter
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Save;
    private javax.swing.JButton clearAll;
    private javax.swing.JComboBox<String> fileType;
    private javax.swing.JLabel fileTypeLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton openFile;
    private javax.swing.JTextPane textBody;
    // End of variables declaration//GEN-END:variables
}
