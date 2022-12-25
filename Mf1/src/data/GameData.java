package data;

import game_structure.Cell;
import game_structure.Player;
import game_structure.Ship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameData {
    private Player p1;
    private Player p2;

    private Map<Player, Cell> playerBoard = new HashMap<>();
    private Map<Player, List<Ship>> playerShips = new HashMap<>();
    private Map<Player, List<Cell>> cantBeat = new HashMap<>();
    private Map<Player, List<Cell>> mines = new HashMap<>();


    public GameData() {

    }
    public GameData(Player p1, Player p2) {
        playerShips.put(p1, new ArrayList<>());
        playerShips.put(p2, new ArrayList<>());

        cantBeat.put(p1, new ArrayList<>());
        cantBeat.put(p2, new ArrayList<>());

        mines.put(p1, new ArrayList<>());
        mines.put(p2, new ArrayList<>());


    }



    public Map<Player, List<Cell>> getMines() {
        return mines;
    }

    public void setMines(Map<Player, List<Cell>> mines) {
        this.mines = mines;
    }

    public Player getP1() {
        return p1;
    }

    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public Player getP2() {
        return p2;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }

    public Map<Player, Cell> getPlayerBoard() {
        return playerBoard;
    }

    public void setPlayerBoard(Map<Player, Cell> playerBoard) {
        this.playerBoard = playerBoard;
    }

    public Map<Player, List<Ship>> getPlayerShips() {
        return playerShips;
    }

    public void setPlayerShips(Map<Player, List<Ship>> playerShips) {
        this.playerShips = playerShips;
    }

    public Map<Player, List<Cell>> getCantBeat() {
        return cantBeat;
    }

    public void setCantBeat(Map<Player, List<Cell>> cantBeat) {
        this.cantBeat = cantBeat;
    }
}
