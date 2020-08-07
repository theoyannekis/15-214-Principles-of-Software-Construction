package edu.cmu.cs.cs214.hw4.core;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;


import org.yaml.snakeyaml.Yaml;



// In a file named TileInputBean.java
public class TileInputBean {

    private TileStackBean[] tileStack;

    public TileStackBean[] getTileStack() {
        return tileStack;
    }

    public void setTileStack(TileStackBean[] tileStack) {
        this.tileStack = tileStack;
    }

    // In a class named InventoryItemBean.java (or as a nested static class in GroceryStoreBean)
    public enum TileSegmentBean {
        road, cloister, field, city, cityPennant;
    }

    // In a class named InventoryBean.java (or as a nested static class in GroceryStoreBean)
    public static class TileStackBean {

        private int instances;
        public int getInstances() { return instances; }
        public void setInstances(int instances) { this.instances = instances; }

        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        private String northTop;
        public String getNorthTop() { return northTop; }
        public void setNorthTop(String northTop) { this.northTop = northTop; }

        private String northMid;
        public String getNorthMid() { return northMid; }
        public void setNorthMid(String northMid) { this.northMid = northMid; }
        
        private String northBot;
        public String getNorthBot() { return northBot; }
        public void setNorthBot(String northBot) { this.northBot = northBot; }

        private String westTop;
        public String getWestTop() { return westTop; }
        public void setWestTop(String westTop) { this.westTop = westTop; }

        private String westMid;
        public String getWestMid() { return westMid; }
        public void setWestMid(String westMid) { this.westMid = westMid; }

        private String westBot;
        public String getWestBot() { return westBot; }
        public void setWestBot(String westBot) { this.westBot = westBot; }

        private String southTop;
        public String getSouthTop() { return southTop; }
        public void setSouthTop(String southTop) { this.southTop = southTop; }

        private String southMid;
        public String getSouthMid() { return southMid; }
        public void setSouthMid(String southMid) { this.southMid = southMid; }

        private String southBot;
        public String getSouthBot() { return southBot; }
        public void setSouthBot(String southBot) { this.southBot = southBot; }

        private String eastTop;
        public String getEastTop() { return eastTop; }
        public void setEastTop(String eastTop) { this.eastTop = eastTop; }

        private String eastMid;
        public String getEastMid() { return eastMid; }
        public void setEastMid(String eastMid) { this.eastMid = eastMid; }

        private String eastBot;
        public String getEastBot() { return eastBot; }
        public void setEastBot(String eastBot) { this.eastBot = eastBot; }

        private String center;
        public String getCenter() { return center; }
        public void setCenter(String center) { this.center = center; }

        private String centerNE;
        public String getCenterNE() { return centerNE; }
        public void setCenterNE(String centerNE) { this.centerNE = centerNE; }

        private String centerN;
        public String getCenterN() { return centerN; }
        public void setCenterN(String centerN) { this.centerN = centerN; }

        private String centerNW;
        public String getCenterNW() { return centerNW; }
        public void setCenterNW(String centerNW) { this.centerNW = centerNW; }

        private String centerW;
        public String getCenterW() { return centerW; }
        public void setCenterW(String centerW) { this.centerW = centerW; }

        private String centerSW;
        public String getCenterSW() { return centerSW; }
        public void setCenterSW(String centerSW) { this.centerSW = centerSW; }

        private String centerS;
        public String getCenterS() { return centerS; }
        public void setCenterS(String centerS) { this.centerS = centerS; }

        private String centerSE;
        public String getCenterSE() { return centerSE; }
        public void setCenterSE(String centerSE) { this.centerSE = centerSE; }

        private String centerE;
        public String getCenterE() { return centerE; }
        public void setCenterE(String centerE) { this.centerE = centerE; }

        private String cornerNE;
        public String getCornerNE() { return cornerNE; }
        public void setCornerNE(String cornerNE) { this.cornerNE = cornerNE; }

        private String cornerNW;
        public String getCornerNW() { return cornerNW; }
        public void setCornerNW(String cornerNW) { this.cornerNW = cornerNW; }

        private String cornerSW;
        public String getCornerSW() { return cornerSW; }
        public void setCornerSW(String cornerSW) { this.cornerSW = cornerSW; }

        private String cornerSE;
        public String getCornerSE() { return cornerSE; }
        public void setCornerSE(String cornerSE) { this.cornerSE = cornerSE; }

    }

    static TileInputBean parse(String fileName) {
        Yaml yaml = new Yaml();
        try (InputStream is = new FileInputStream(fileName)) {
            return yaml.loadAs(is, TileInputBean.class);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File " + fileName + " not found!");
        } catch (IOException e) {
            throw new IllegalArgumentException("Error when reading " + fileName + "!");
        }
    }

    /**
     * Creates a list of all of maps corresponding to one tile. There will be 72 maps representing all of the
     * tiles in the game
     * @param tileStack the list of information of all tiles in the game read from YML file
     * @return An ArrayList of maps corresponding to all of the tiles in the game
     */
     ArrayList<Map<String, String>> makeTileMapStack(ArrayList<TileStackBean> tileStack) {
        ArrayList<Map<String, String>> maps = new ArrayList<>();
        for (TileStackBean elem : tileStack) {
            int instances = elem.getInstances();
            for (int i = 0; i < instances; i++ ) {
                maps.add(makeTileMap(elem));
            }
        }
        return maps;
    }

    /**
     * make the map to map segments to their position on the tile
     * @param tileStack the information regarding the segments
     * @return map of segment to position
     */
    private static Map<String, String> makeTileMap(TileStackBean tileStack) {
        Map<String, String> map = new HashMap<>();
        map.put("name", tileStack.getName());
        map.put("northTop", tileStack.getNorthTop());
        map.put("northMid", tileStack.getNorthMid());
        map.put("northBot", tileStack.getNorthBot());
        map.put("westTop", tileStack.getWestTop());
        map.put("westMid", tileStack.getWestMid());
        map.put("westBot", tileStack.getWestBot());
        map.put("southTop", tileStack.getSouthTop());
        map.put("southMid", tileStack.getSouthMid());
        map.put("southBot", tileStack.getSouthBot());
        map.put("eastTop", tileStack.getEastTop());
        map.put("eastMid", tileStack.getEastMid());
        map.put("eastBot", tileStack.getEastBot());
        map.put("center", tileStack.getCenter());
        map.put("centerNE", tileStack.getCenterNE());
        map.put("centerN", tileStack.getCenterN());
        map.put("centerNW", tileStack.getCenterNW());
        map.put("centerW", tileStack.getCenterW());
        map.put("centerSW", tileStack.getCenterSW());
        map.put("centerS", tileStack.getCenterS());
        map.put("centerSE", tileStack.getCenterSE());
        map.put("centerE", tileStack.getCenterE());
        map.put("cornerNE", tileStack.getCornerNE());
        map.put("cornerNW", tileStack.getCornerNW());
        map.put("cornerSW", tileStack.getCornerSW());
        map.put("cornerSE", tileStack.getCornerSE());

        return map;
    }

    public static void main(String[] args) {
        TileInputBean tileInput = parse("src/main/resources/config.yml");
        ArrayList<TileStackBean> tileStack = new ArrayList<>(Arrays.asList(tileInput.getTileStack()));
        ArrayList<Map<String, String>> map = tileInput.makeTileMapStack(tileStack);
        System.out.println(map.get(0).get("centerE"));
    }
}





