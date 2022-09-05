/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templates;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Familia
 */
public class colorTablas extends DefaultTableCellRenderer {
    public int columna;
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean Selected, boolean hasFocus, int row, int col){
        
        super.getTableCellRendererComponent(table, value, Selected, hasFocus, row, col);
        
        String unids = table.getValueAt(row, columna).toString();
        
        if ((Integer.parseInt(unids) > 5)) {
            setBackground(Color.green);
        }else if ((Integer.parseInt(unids) > 3) && (Integer.parseInt(unids) <= 5)) {
            setBackground(Color.yellow);
        }else{
            setBackground(Color.red);
        }
        
        
        return this;
    }
    
}
