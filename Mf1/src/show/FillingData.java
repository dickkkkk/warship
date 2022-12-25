package show;

import java.util.HashMap;
import java.util.Map;

public class FillingData {

    //key = size
    //val = count
    private Map<Integer, Integer> countShips;
    private int countBomns;

    public FillingData() {
        setDefult();
    }

    public void setDefult(){
        countBomns = 4;
        countShips = new HashMap<>();
        countShips.put(1, 4);
        countShips.put(2, 3);
        countShips.put(3, 2);
        countShips.put(4, 1);
    }

    public int getMaxShip(){
        int max = -1;
        for(Integer i: countShips.keySet()){
            if(countShips.get(i) > 0 && max < i)
                max = i;
        }
        return max;
    }

    public void addShip(int size){

        countShips.replace(size, countShips.get(size)+1);
    }
    public void removeShip(int size){

        if(countShips.get(size) == 0)
            return;
        countShips.replace(size, countShips.get(size)-1);
    }

    public void addBomb(){
        countBomns++;
    }
    public void removeBomb(){
        countBomns--;
    }

    public Map<Integer, Integer> getCountShips() {
        return countShips;
    }
    public int getCountShips(int size) {
        return countShips.getOrDefault(size, 0);
    }

    public int getCountBomns() {
        return countBomns;
    }

    public boolean isEmpty(){
        return countBomns == 0 && getMaxShip()==-1;
    }
}
