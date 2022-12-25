package game_structure;

import java.util.HashMap;
import java.util.Map;

public class Cell {
    private Map<Direction, Cell> next;

    public Cell() {
        next = new HashMap<>();
    }

    public Cell(Map<Direction, Cell> next) {
        this.next = next;
    }

    public Cell get(Direction d){
        return next.getOrDefault(d, null);
    }

    public void add(Direction d, Cell c){
        next.put(d, c);
    }

    public Map<Direction, Cell> getNext() {
        return next;
    }


}
