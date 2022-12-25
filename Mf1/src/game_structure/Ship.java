package game_structure;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private List<Cell> cells;
    private List<Cell> damaged;

    public Ship(List<Cell> cells) {
        this.cells = cells;
        this.damaged = new ArrayList<>();
    }

    public void push(Cell c){
        int index = getIndex(c);

        if(index == -1)
            return;

        damaged.add(c);
    }
    private int getIndex(Cell c){
        for (int i = 0; i < cells.size(); i++) {
            if(cells.get(i).hashCode() == c.hashCode())
                return i;
        }
        return -1;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public List<Cell> getDamaged() {
        return damaged;
    }

    public int countDamaged(){
        return damaged.size();
    }

    public int countNotDamaged(){
        return cells.size();
    }

    @Override
    public String toString() {
        return "Ship{" +
                "cells=" + cells +
                ", damaged=" + damaged +
                '}'+"\n";
    }
}
