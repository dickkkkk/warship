package service;

import data.GameData;
import game_structure.Cell;
import game_structure.Direction;
import game_structure.Player;

import java.awt.*;

public class SubmarineService {
    public static Player useSubmarine(GameData data, Player tmp, Player enemy, Point p, int row, int col){
        Cell c = Auxiliary.getCellByPoint(data, enemy, p, row, col);
        return useSubmarine(data, tmp, enemy, c);
    }
    public static Player useSubmarine(GameData data, Player tmp, Player enemy, Cell c){
        for (Direction d1:Direction.values()) {
            for (Direction d2:Direction.values()) {
                Cell cell = Auxiliary.getCell(c, d1, d2);
                if(data.getMines().get(enemy).contains(cell)){
                    data.getMines().get(enemy).remove(cell);
                    return tmp;
                }
            }
        }

        for (Direction d:Direction.values()) {
            Cell cell = Auxiliary.getCell(c, d, null);
            while (cell != null){
                Player p = ShipService.shoot(data, tmp, enemy, cell);
                if(p.equals(tmp))
                    break;

                cell = Auxiliary.getCell(c, d, null);
            }
        }

        return tmp;
    }
}
