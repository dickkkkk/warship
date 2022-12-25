package show;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MyRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        JLabel l = (JLabel) super.getTableCellRendererComponent(table, null, isSelected, hasFocus, row, col);

        l.setIcon(new ImageIcon("/Users/sandra/IdeaProjects/Mf1/images/sea.png"));

        if (table.getValueAt(row, col) == null)
            return l;

        int a = Integer.parseInt(table.getValueAt(row, col).toString());
        if(a == 1)
            l.setIcon(new ImageIcon("/Users/sandra/IdeaProjects/Mf1/images/ship.png"));
        else if(a == 2)
            l.setIcon(new ImageIcon("/Users/sandra/IdeaProjects/Mf1/images/hited.png"));
        else if(a == 3)
            l.setIcon(new ImageIcon("/Users/sandra/IdeaProjects/Mf1/images/environment.png"));
        else if(a == 4)
            l.setIcon(new ImageIcon("/Users/sandra/IdeaProjects/Mf1/images/mine.png"));
        else if(a == 5)
            l.setIcon(new ImageIcon("/Users/sandra/IdeaProjects/Mf1/images/killed_mine.png"));
        return l;
    }
}