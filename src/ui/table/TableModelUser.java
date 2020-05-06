/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.table;

import domain.User;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author veljko
 */
public class TableModelUser extends AbstractTableModel {

    private List<User> users;
    private String[] columnNames = new String[]{"ID", "Username", "Active"};

    public TableModelUser(List<User> users) {
        this.users = users;
    }
    

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case (0):
                return users.get(rowIndex).getId();
            case (1):
                return users.get(rowIndex).getUsername();
            case (2):
                return users.get(rowIndex).isActive();
            default:
                return "n/a";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
       return columnIndex==2 ? Boolean.class : String.class;
    }
    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex==2;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        users.get(rowIndex).setActive((Boolean)aValue);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    
   
}
