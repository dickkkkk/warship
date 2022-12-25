package bot;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Bot {
    public static Point getRndMove(JTable table, int row, int col){
        Point p = new Point(new Random().nextInt(row),new Random().nextInt(row));
        while (Integer.parseInt(table.getValueAt(p.x, p.y).toString()) == 2 || Integer.parseInt(table.getValueAt(p.x, p.y).toString()) == 3){
            p = new Point(new Random().nextInt(row),new Random().nextInt(row));
        }
        return p;
    }



}
