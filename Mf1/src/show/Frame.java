package show;

import bot.Bot;
import data.GameData;
import game_structure.Cell;
import game_structure.Player;
import service.*;
import show.util.JTableUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Frame extends JFrame {

    private FillingData fillData = new FillingData();

    private boolean defultMode = true;

    private boolean mineMode = false;

    private GameData data;

    private boolean submarine = false;
    private int submarineCount = 2;
    private Player player = new Player("Test");
    private Player bot = new Player("Enemy");

    private Player tmp = player;

    private JPanel panelMain;

    private JTable tableInput;
    private JScrollPane panelPlayer;
    private JScrollPane panelFill;
    private JButton startGameButton;
    private JButton mineModeButton;
    private JButton clearAllButton;
    private JButton defultModeButton;
    private JTextField field1;
    private JTextField field2;
    private JTextField field4;
    private JTextField field3;
    private JTextField fieldBombs;
    private JTable tableEnemy;
    private JScrollPane panelEnemy;
    private JButton nextButton;
    private JButton submarineButton;
    private JTextField textFieldState;
    private JTextField textFieldSubmarine;


    private Point last = null;
    private int row = 10;
    private int col = 10;

    public Frame() {
        this.setTitle("FrameMain");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        JTableUtils.initJTableForArray(tableInput, 50, true, true, false, false);
        JTableUtils.initJTableForArray(tableEnemy, 50, true, true, false, false);
        tableInput.setRowHeight(50);
        tableEnemy.setRowHeight(50);
        JTableUtils.writeArrayToJTable(tableInput, new int[row][col]);
        MyRenderer renderer = new MyRenderer();

        tableInput.setDefaultRenderer(Object.class, renderer);
        tableEnemy.setDefaultRenderer(Object.class, renderer);

        submarineButton.setVisible(false);
        nextButton.setVisible(false);

        panelEnemy.setVisible(false);
        textFieldState.setVisible(false);
        textFieldState.setText("Tmp player: "+tmp.getName());

        addListeners();
        setButtonsColor();

        setEnables();
        setState();
        repaint();
    }





























    private void addListeners(){
        addTableListeners();
        addButtonListeners();
    }
    private void addTableListeners(){
        tableInput.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);


                if(mineMode){
                    Point point = getPoint(tableInput, e);
                    if(e.getButton() == 1 ) {
                        if(canAddMine(point)) {
                            tableInput.setValueAt(4, point.x, point.y);
                            fillData.removeBomb();
                        }
                    }else {
                        if(Integer.parseInt(tableInput.getValueAt(point.x, point.y).toString())== 4){
                            tableInput.setValueAt(0, point.x, point.y);
                            fillData.addBomb();
                        }
                    }
                    setState();
                    return;
                }
                else if(defultMode) {
                    if (e.getButton() == 1) {
                        if (last == null)
                            last = getPoint(tableInput, e);
                        else {
                            Point point = getPoint(tableInput, e);
                            List<Point> cells = getCells(last, point);
                            last = null;
                            if (cells.size() == 0 || !canAddShip(cells))
                                return;

                            fillCells(cells, 1);
                            fillData.removeShip(cells.size());
                            setState();
                        }
                    } else if (e.getButton() == 3) {
                        List<Point> cells = getCells(getPoint(tableInput, e));
                        if (cells.size() == 0)
                            return;
                        fillCells(cells, 0);
                        fillData.addShip(cells.size());
                        setState();
                    }
                }
            }

        });

        tableEnemy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(tmp.equals(player)) {
                    if(!submarine) {
                        tmp = ShipService.shoot(data, player, bot, getPoint(tableEnemy, e), row, col);
                        int[][] arr = Fill.getEnemyTable(data, bot, row, col);
                        JTableUtils.writeArrayToJTable(tableEnemy, Fill.getEnemyTable(data, bot, row, col));
                    }else {
                        tmp = SubmarineService.useSubmarine(data, player, bot, getPoint(tableEnemy, e), row, col);
                        int[][] arr = Fill.getEnemyTable(data, bot, row, col);
                        JTableUtils.writeArrayToJTable(tableEnemy, Fill.getEnemyTable(data, bot, row, col));
                        submarineCount--;
                        submarine = false;
                        textFieldSubmarine.setText("Count: "+submarineCount);
                        setButtonsColor();
                    }
                }

                textFieldState.setText("Tmp player: "+tmp.getName());
                if(Auxiliary.checkLoose(data, bot))
                    System.out.println("�� ��������");
                repaint();

            }

        });
    }
    private void addButtonListeners(){
        clearAllButton.addActionListener(actionEvent -> {
            JTableUtils.writeArrayToJTable(tableInput, new int[row][col]);
            fillData.setDefult();
            last = null;
        });
        submarineButton.addActionListener(actionEvent -> {
            if(submarineCount > 0) {
                submarine = !submarine;
            }else {
                submarine = false;
            }
            setButtonsColor();
        });
        defultModeButton.addActionListener(actionEvent -> {
            setModes();
            defultMode = true;
            setButtonsColor();
        });

        mineModeButton.addActionListener(actionEvent -> {
            setModes();
            if(mineMode)
                defultMode = true;
            else {
                mineMode = true;
            }
            setButtonsColor();
        });
        nextButton.addActionListener(actionEvent -> {
            if(tmp.equals(bot)){
                Point p = Bot.getRndMove(tableEnemy, row, col);
                Cell c = Auxiliary.getCellByPoint(data, player, p, row, col);
                tmp = ShipService.shoot(data, bot, player, c);
                int[][] arr = Fill.getPlayerTable2(data, player, row, col);

                JTableUtils.writeArrayToJTable(tableInput, arr);

            }
            textFieldState.setText("Tmp player: "+tmp.getName());
            if(Auxiliary.checkLoose(data, player))
                System.out.println("�� ���������");
            repaint();
        });

        startGameButton.addActionListener(actionEvent -> {
            if(!fillData.isEmpty()) {
                JTableUtils.writeArrayToJTable(tableInput, Options.get1());

            }
            JTableUtils.writeArrayToJTable(tableEnemy, Options.get2());
            textFieldState.setVisible(true);
            submarineButton.setVisible(true);
            panelFill.setVisible(false);
            panelEnemy.setVisible(true);
            submarineButton.setVisible(true);
            nextButton.setVisible(true);
            panelFill.setVisible(false);
            panelEnemy.setVisible(true);

            data = new GameData(player, bot);

            data.getPlayerBoard().put(player, Fill.createBoard(row));

            ReadData.read(tableInput, data, player, row, col);

            int[][] array = Fill.getPlayerTable(data, player, row, col);


            JTableUtils.writeArrayToJTable(tableInput, array);

            data.getPlayerBoard().put(bot, Fill.createBoard(row));

            //JTableUtils.writeArrayToJTable(tableEnemy, array);
            ReadData.read(tableEnemy, data, bot, row, col);

            setModes();
            last = null;
            setButtonsColor();
            JTableUtils.writeArrayToJTable(tableEnemy, Fill.getEnemyTable(data, bot, row, col));

            repaint();
        });
    }
    private void fillCells(List<Point> points, int value){
        if(points == null)
            return;
        for (Point p:points) {
            tableInput.setValueAt(value, p.x, p.y);
        }
    }
    private void setButtonsColor(){

        defultModeButton.setBackground(Color.red);
        mineModeButton.setBackground(Color.red);
        submarineButton.setBackground(Color.red);
        if(defultMode)
            defultModeButton.setBackground(Color.GREEN);
        if(mineMode)
            mineModeButton.setBackground(Color.GREEN);
        if(submarine)
            submarineButton.setBackground(Color.GREEN);
    }
    private void setEnables(){
        tableInput.setEnabled(true);
        field1.setEnabled(false);
        field2.setEnabled(false);
        field3.setEnabled(false);
        field4.setEnabled(false);
        fieldBombs.setEnabled(false);
    }
    private void setState(){
        field1.setText(Integer.toString(fillData.getCountShips(1)));
        field2.setText(Integer.toString(fillData.getCountShips(2)));
        field3.setText(Integer.toString(fillData.getCountShips(3)));
        field4.setText(Integer.toString(fillData.getCountShips(4)));
        fieldBombs.setText(Integer.toString(fillData.getCountBomns()));
    }
















    private boolean canAddMine(Point p){
        return Integer.parseInt(tableInput.getValueAt(p.x, p.y).toString()) == 0 && fillData.getCountBomns() > 0;
    }
    private List<Point> getEmptyCells(){
        List<Point> cells = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(Integer.parseInt(tableInput.getValueAt(i, j).toString()) == 0)
                    cells.add(new Point(i, j));
            }
        }
        return cells;
    }
    private List<Point> getCells(Point p1, Point p2){

        if(fillData.getMaxShip() == -1)
            return new ArrayList<>();
        int rowSize = Math.abs(p1.x - p2.x);
        int colSize = Math.abs(p1.y - p2.y);

        if(rowSize != 0 && colSize != 0)
            return new ArrayList<>();


        int sizeShip = Math.min(fillData.getMaxShip(), Math.max(rowSize, colSize)+1);

        List<Point> points = new ArrayList<>();

        if(rowSize == 0){
            int k = (p1.y-p2.y < 0)?1:-1;
            for (int i = 0; i < sizeShip; i++) {
                points.add(new Point(p1.x, p1.y+k*i));
            }
            return points;
        }else{
            int k = (p1.x-p2.x < 0)?1:-1;
            for (int i = 0; i < sizeShip; i++) {
                points.add(new Point(p1.x+k*i, p1.y));
            }
            return points;
        }
    }
    private boolean canAddShip(List<Point> points){

        Point[] directions = {
                new Point(0, 1),
                new Point(1, 0),
                new Point(0, -1),
                new Point(-1, 0),
                new Point(1, 1),
                new Point(-1, -1),
                new Point(1, -1),
                new Point(-1, 1),
        };
        List<Point> visited = new ArrayList<>();

        for (Point p: points) {
            if(Integer.parseInt(tableInput.getValueAt(p.x, p.y).toString()) == 4)
                return false;
        }
        for (Point p:points) {
            for (Point d:directions) {
                Point r = new Point(p.x + d.x, p.y + d.y);
                if(Auxiliary.checkBorders(r, row, col)
                        && Integer.parseInt(tableInput.getValueAt(r.x, r.y).toString()) == 1
                            && !visited.contains(r))
                    visited.add(r);

            }
        }
        for (Point p:points) {
            int i = visited.indexOf(p);
            if(i != -1)
                visited.remove(i);
        }
        return visited.isEmpty();
    }
    private List<Point> getCells(Point p){
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
                if(Integer.parseInt(tableInput.getValueAt(tmp.x, tmp.y).toString()) == 1)
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
    private Point getPoint(JTable table, MouseEvent e){
        return new Point(table.rowAtPoint(e.getPoint()), table.columnAtPoint(e.getPoint()));
    }


    private void setModes(){
        mineMode = false;
        defultMode = false;
    }

}
