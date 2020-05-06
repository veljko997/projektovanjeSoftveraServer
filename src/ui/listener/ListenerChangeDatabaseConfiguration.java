/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.listener;

import controller.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import ui.form.FrmDatabaseConfiguration;

/**
 *
 * @author Veljko
 */
public class ListenerChangeDatabaseConfiguration implements ActionListener{

    private final FrmDatabaseConfiguration frmDatabaseConfiguration;

    public ListenerChangeDatabaseConfiguration(FrmDatabaseConfiguration frmDatabaseConfiguration) {
        this.frmDatabaseConfiguration = frmDatabaseConfiguration;
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Controller.getInstance().writeUrl(frmDatabaseConfiguration.getjTxtUrl().getText().trim());
        Controller.getInstance().writeUsername(frmDatabaseConfiguration.getjTxtUsername().getText().trim());
        Controller.getInstance().writePassword(frmDatabaseConfiguration.getJtxtPassword().getText().trim());
        JOptionPane.showMessageDialog(frmDatabaseConfiguration, "Succesfully changed database properties", "Database", 1);
        frmDatabaseConfiguration.dispose();
    }
    
}
