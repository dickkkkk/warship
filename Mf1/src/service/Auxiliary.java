package service;

import data.GameData;
import game_structure.Cell;
import game_structure.Direction;
import game_structure.Player;
import game_structure.Ship;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Auxiliary {
    public static Point getPointByCell(GameData data, Player p, Cell c, int row, int col){
        class Init{
            HashSet<Cell> visited = new HashSet<>();
            Point res = null;

            void search(Point tp, Cell tc){
                if (!checkBorders(tp, row, col) || tc == null || visited.contains(tc))
                    return;
                visited.add(tc);

                if(tc.equals(c)){
                    res = new Point(tp.x, tp.y);
                    return;
                }
                search(new Point(tp.x , tp.y + 1), tc.get(Direction.RIGHT));
                search(new Point(tp.x , tp.y - 1), tc.get(Direction.LEFT));
                search(new Point(tp.x+1 , tp.y), tc.get(Direction.DOWN));
                search(new Point(tp.x-1, tp.y), tc.get(Direction.UP));
            }
        }
        Init i = new Init();
        i.search(new Point(0,0), data.getPlayerBoard().get(p));
        return i.res;
    }

    public static Cell getCellByPoint(GameData data, Player p, Point c, int row, int col){
        class Init{
            HashSet<Cell> visited = new HashSet<>();
            Cell res = null;

            void search(Point tp, Cell tc){
                if (!checkBorders(tp, row, col) || tc == null || visited.contains(tc))
                    return;
                visited.add(tc);

                if(tp.equals(c)){

                    res = tc;
                    return;
                }
                search(new Point(tp.x , tp.y + 1), tc.get(Direction.RIGHT));
                search(new Point(tp.x , tp.y - 1), tc.get(Direction.LEFT));
                search(new Point(tp.x+1 , tp.y), tc.get(Direction.DOWN));
                search(new Point(tp.x-1, tp.y), tc.get(Direction.UP));
            }
        }
        Init i = new Init();
        i.search(new Point(0,0), data.getPlayerBoard().get(p));

        return i.res;
    }

    public static boolean checkBorders(Point p, int row, int col){
        if(p.x >= row || p.y >= col || p.x < 0 || p.y < 0)
            return false;
        return true;
    }


    public static List<Point> getCells(Point p, JTable table, int row, int col){
        class Init{
            List<Point> visited = new ArrayList<>();
            Point[] directions = {
                    new Point(0, 1),
                    new Point(1, 0),
                    new Point(0, -1),
                    new Point(-1, 0)};

            void search(Point tmp){
                if (!Auxiliary.checkBorders(tmp, row, col) || visited.contains(tmp))
                    return;
                if(Integer.parseInt(table.getValueAt(tmp.x, tmp.y).toString()) == 1)
                    visited.add(tmp);
                else
                    return;

                for (Point p:directions) {
                    search(new Point(tmp.x + p.x, tmp.y + p.y));
                }
            }

        }
        Init i = new Init();
        i.search(p);
        return i.visited;
    }

    public static List<Cell> getCells(GameData data, Player player, List<Point> points,int row, int col){
        List<Cell> cells = new ArrayList<>();

        for (Point p:points) {
            Cell c = getCellByPoint(data, player, p, row, col);
            if(c != null)
                cells.add(c);
        }

        return cells;

    }


    public static boolean contains(List<Point> points, Point point){
        for (Point p:points) {
            if(point.equals(p))
                return true;
        }
        return false;
    }

    public static boolean contains(List<Point> p1, List<Point> p2){
        for (Point p:p1) {
            if(contains(p2, p))
                return true;
        }
        return false;
    }

    public static Cell getCell(Cell c, Direction d1, Direction d2){

        if(c == null)
            return null;

        Cell nc = c;

        if(d1 != null)
            nc = nc.get(d1);

        if(nc == null)
            return null;

        if(d2 != null)
            nc = nc.get(d2);

        return nc;
    }

    public static boolean contains(GameData data, Player p, Cell c){
        if(data.getCantBeat().getOrDefault(p, new ArrayList<>()).contains(c))
            return true;

        for (Ship s:data.getPlayerShips().get(p)) {
            if(s.getDamaged().contains(c) || s.getCells().contains(c))
                return true;
        }

        return false;
    }


    public static boolean checkLoose(GameData data, Player p){
        for (Ship s:data.getPlayerShips().get(p)) {
            if(!s.getCells().isEmpty())
                return true;
        }
        return false;
    }

}
