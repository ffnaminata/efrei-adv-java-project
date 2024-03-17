package fr.diopfofana.efrei.monopoly.simulation;

import fr.arsenelapostolet.efrei.monopoly.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final List<Location> board;
    private final Map<String, List<Integer>> colorsIndex;
    public Board(){
        Parser parser = new Parser();
        this.board = parser.loadMonopolyDataFromCSV();
        this.colorsIndex = initialiseColorsIndex(this.board);
    }
    public Map<String, List<Integer>> initialiseColorsIndex(List<Location> board) {
        Map<String, List<Integer>> colorsIndex = new HashMap<>();
        for (int index = 0; index < board.size(); index++) {
            String color = board.get(index).getColor();
            if (!colorsIndex.containsKey(color)) {
                List<Integer> colorIndex = new ArrayList<>();
                colorsIndex.put(color, colorIndex);
            }
            colorsIndex.get(color).add(index);
        }
        return colorsIndex;
    }

    public boolean isOwnerOfAllColorLocation(String playerName, String color){
        for (int index : colorsIndex.get(color)){
            if (!playerName.equals(board.get(index).getOwner())){
                return false;
            }
        }
        return true;
    }

    public List<Location> getBoard() {
        return board;
    }
}
