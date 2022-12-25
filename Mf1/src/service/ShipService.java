package service;

import data.GameData;
import game_structure.Cell;
import game_structure.Direction;
import game_structure.Player;
import game_structure.Ship;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShipService {


    public static Player shoot(GameData data, Player tmp, Player enemy, Point p, int row, int col){
        Cell c = Auxiliary.getCellByPoint(data, enemy, p, row, col);
        return shoot(data, tmp, enemy, c);
    }
    public static Player shoot(GameData data, Player tmp, Player enemy, Cell c){

        if(!canHit(data, enemy, c)) {
            return tmp;
        }

        int s = checkHit(data, enemy, c);
        if(s != -1) {
            data.getPlayerShips().get(enemy).get(s).push(c);
            addHit(data, enemy, c);
            if(data.getPlayerShips().get(enemy).get(s).countNotDamaged() == 0)
                addKillHit(data, enemy, data.getPlayerShips().get(enemy).get(s).getDamaged(), data.getPlayerShips().get(enemy).get(s));
            return tmp;
        }

        data.getCantBeat().get(enemy).add(c);


        return enemy;
    }

    public static int checkHit(GameData data, Player enemy, Cell cell){
        for (int i = 0; i < data.getPlayerShips().get(enemy).size(); i++) {
            Ship s = data.getPlayerShips().get(enemy).get(i);
            if(s.getCells().contains(cell)){
                return i;
            }
        }

        return -1;
    }

    public static void addHited(GameData data, Player p, Cell cell, Ship s){
        Map<Direction, List<Direction>> directionsHit = Map.of(
                Direction.UP, List.of(Direction.LEFT, Direction.RIGHT),
                Direction.DOWN, List.of(Direction.LEFT, Direction.RIGHT)
                );

        Map<Direction, List<Direction>> directionsKill = new HashMap<>();
        List<Direction> directions = new ArrayList<>();
        directions.add(Direction.LEFT);
        directions.add(Direction.RIGHT);
        directions.add(null);

        directionsKill.put(Direction.UP, directions);
        directionsKill.put(Direction.DOWN, directions);

        directions = new ArrayList<>();
        directions.add(null);
        directionsKill.put(Direction.LEFT, directions);
        directionsKill.put(Direction.RIGHT, directions);


        if(s.getCells().isEmpty()){
            data.getCantBeat().get(p).addAll(
                    getAdjacent(data, p, cell, directionsKill
                    ));
        }else {
            data.getCantBeat().getOrDefault(p, new ArrayList<>()).addAll(
                    getAdjacent(data, p, cell, directionsHit
                    ));
        }

    }

    private static List<Cell> getAdjacent(GameData data, Player p, Cell c, Map<Direction, List<Direction>> directions){
        List<Cell> cells = new ArrayList<>();

        for (Direction d1:directions.keySet()) {
            for (Direction d2:directions.get(d1)) {
                Cell cell = Auxiliary.getCell(c, d1, d2);
                if (cell == null || Auxiliary.contains(data, p, cell))
                    continue;
                cells.add(cell);
            }
        }
        return cells;
    }


    private static boolean canHit(GameData data, Player enemy, Cell c){

        boolean b = data.getCantBeat().get(enemy).contains(c);
        if(b)
            return false;

        for (Ship s:data.getPlayerShips().get(enemy)) {
            if (s.getDamaged().contains(c))
                return false;
        }
        return true;
    }

    private static void addKillHit(GameData data, Player p, List<Cell> cells, Ship s){
        Cell cell = null;

        for (Cell c:cells) {
            cell = Auxiliary.getCell(c, Direction.DOWN, null);
            if (cell != null && !s.getDamaged().contains(cell))
                data.getCantBeat().get(p).add(cell);

            cell = Auxiliary.getCell(c, Direction.UP, null);
            if (cell != null&& !s.getDamaged().contains(cell))
                data.getCantBeat().get(p).add(cell);

            cell = Auxiliary.getCell(c, Direction.LEFT, null);
            if (cell != null&& !s.getDamaged().contains(cell))
                data.getCantBeat().get(p).add(cell);

            cell = Auxiliary.getCell(c, Direction.RIGHT, null);
            if (cell != null&& !s.getDamaged().contains(cell))
                data.getCantBeat().get(p).add(cell);
        }



    }


    private static void addHit(GameData data, Player p, Cell c){
        Cell cell = null;

        cell = Auxiliary.getCell(c, Direction.UP, Direction.RIGHT);
        if (cell != null)
            data.getCantBeat().get(p).add(cell);

        cell = Auxiliary.getCell(c, Direction.UP, Direction.LEFT);
        if (cell != null)
            data.getCantBeat().get(p).add(cell);

        cell = Auxiliary.getCell(c, Direction.DOWN, Direction.RIGHT);
        if (cell != null)
            data.getCantBeat().get(p).add(cell);

        cell = Auxiliary.getCell(c, Direction.DOWN, Direction.LEFT);
        if (cell != null)
            data.getCantBeat().get(p).add(cell);

    }

}
