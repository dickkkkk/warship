package service;

import data.GameData;
import game_structure.Cell;
import game_structure.Player;
import game_structure.Ship;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReadData {

    public static void read(JTable table, GameData data, Player p, int row, int col){

        List<Point> points = new ArrayList<>();


        List<Ship> ships = new ArrayList<>();

        int count = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(Integer.parseInt(table.getValueAt(i, j).toString()) != 1)
                    continue;
                List<Point> pts = Auxiliary.getCells(new Point(i, j), table, row, col);
                if(Auxiliary.contains(points, pts)) {
                    System.out.println("+");
                    continue;
                }
                count++;
                points.addAll(pts);
                List<Cell> ship = Auxiliary.getCells(data, p, pts, row, col);
                points.addAll(pts);
                System.out.println(points);
                System.out.println();
                ships.add(new Ship(ship));
            }
        }
        List<Cell> bombs = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int val = Integer.parseInt(table.getValueAt(i, j).toString());
                if(val == 4)
                    bombs.add(Auxiliary.getCellByPoint(data, p, new Point(i, j), row,col));
            }
        }

        data.getPlayerShips().put(p, ships);
        System.out.println(count);
        data.getMines().put(p, bombs);
    }
}
