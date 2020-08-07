package edu.cmu.cs.cs214.hw4.core;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TileTests {
    private Map<String, String> map;
    private Map<String, String> dummyMap;
    private Tile startingTile;

    @Before
    public void setup() {
        map = new HashMap<>();
        map.put("name", "D");
        map.put("northTop", "field");
        map.put("northMid", "road");
        map.put("northBot", "field");
        map.put("westTop", "field");
        map.put("westMid", "field");
        map.put("westBot", "field");
        map.put("southTop", "field");
        map.put("southMid", "road");
        map.put("southBot", "field");
        map.put("eastTop", "city");
        map.put("eastMid", "city");
        map.put("eastBot", "city");
        map.put("center", "road");
        map.put("centerNE", "field");
        map.put("centerN", "road");
        map.put("centerNW", "field");
        map.put("centerW", "field");
        map.put("centerSW", "field");
        map.put("centerS", "road");
        map.put("centerSE", "field");
        map.put("centerE", "field");
        map.put("cornerNE", "dummy");
        map.put("cornerNW", "dummy");
        map.put("cornerSW", "dummy");
        map.put("cornerSE", "dummy");

        dummyMap = new HashMap<>();
        dummyMap.put("name", "dummy");
        dummyMap.put("northTop", "dummy");
        dummyMap.put("northMid", "dummy");
        dummyMap.put("northBot", "dummy");
        dummyMap.put("westTop", "dummy");
        dummyMap.put("westMid", "dummy");
        dummyMap.put("westBot", "dummy");
        dummyMap.put("southTop", "dummy");
        dummyMap.put("southMid", "dummy");
        dummyMap.put("southBot", "dummy");
        dummyMap.put("eastTop", "dummy");
        dummyMap.put("eastMid", "dummy");
        dummyMap.put("eastBot", "dummy");
        dummyMap.put("center", "dummy");
        dummyMap.put("centerNE", "dummy");
        dummyMap.put("centerN", "dummy");
        dummyMap.put("centerNW", "dummy");
        dummyMap.put("centerW", "dummy");
        dummyMap.put("centerSW", "dummy");
        dummyMap.put("centerS", "dummy");
        dummyMap.put("centerSE", "dummy");
        dummyMap.put("centerE", "dummy");
        dummyMap.put("cornerNE", "dummy");
        dummyMap.put("cornerNW", "dummy");
        dummyMap.put("cornerSW", "dummy");
        dummyMap.put("cornerSE", "dummy");

        startingTile = Tile.readInStartingTile();

    }

    @Test
    public void testReadInStartingTile() {
        Tile shouldBeStartingTile = new Tile(map);
        assertTrue(shouldBeStartingTile.identicalTile(startingTile));
        Tile dummyTile = new Tile(dummyMap);
        assertFalse(dummyTile.identicalTile(shouldBeStartingTile));
    }

    @Test
    public void testRotateTile() {
        Map<String, String> map90 = new HashMap<>();
        map90.put("name", "D");
        map90.put("northTop", "field");
        map90.put("northMid", "field");
        map90.put("northBot", "field");
        map90.put("westTop", "field");
        map90.put("westMid", "road");
        map90.put("westBot", "field");
        map90.put("southTop", "city");
        map90.put("southMid", "city");
        map90.put("southBot", "city");
        map90.put("eastTop", "field");
        map90.put("eastMid", "road");
        map90.put("eastBot", "field");
        map90.put("center", "road");
        map90.put("centerNE", "field");
        map90.put("centerN", "field");
        map90.put("centerNW", "field");
        map90.put("centerW", "road");
        map90.put("centerSW", "field");
        map90.put("centerS", "field");
        map90.put("centerSE", "field");
        map90.put("centerE", "road");
        map90.put("cornerNE", "dummy");
        map90.put("cornerNW", "dummy");
        map90.put("cornerSW", "dummy");
        map90.put("cornerSE", "dummy");
        Tile rotated90 = new Tile(map90);
        Tile startingTile = new Tile(map);
        Tile anotherStart = new Tile(map);
        assertFalse(rotated90.identicalTile(startingTile));
        assertTrue(startingTile.identicalTile(anotherStart));

        startingTile.rotateTile();

        assertTrue(rotated90.identicalTile(startingTile));
        assertFalse(startingTile.identicalTile(anotherStart));

        startingTile.rotateTile();
        assertFalse(rotated90.identicalTile(startingTile));
        assertFalse(startingTile.identicalTile(anotherStart));

        startingTile.rotateTile();
        assertFalse(rotated90.identicalTile(startingTile));
        assertFalse(startingTile.identicalTile(anotherStart));

        startingTile.rotateTile();
        assertFalse(rotated90.identicalTile(startingTile));
        assertTrue(startingTile.identicalTile(anotherStart));
    }

    @Test
    public void testCreateDummyTile() {
        Tile dummy = new Tile(dummyMap);
        Tile shouldBeDummy = Tile.createDummyTile();
        assertTrue(dummy.identicalTile(shouldBeDummy));

        Tile notDummy = new Tile(map);
        assertFalse(shouldBeDummy.identicalTile(notDummy));
    }

    @Test
    public void testIsDummyTile() {
        Tile dummy = new Tile(dummyMap);
        assertTrue(dummy.isDummyTile());
        assertTrue(Tile.createDummyTile().isDummyTile());

        Tile notDummy = new Tile(map);
        assertFalse(notDummy.isDummyTile());


    }

    @Test
    public void testSidesMatch() {
        Tile startTile = new Tile(map);
        Tile otherStart = new Tile(map);
        Tile dummyTile = new Tile(dummyMap);
        assertTrue(startTile.sidesMatch("north", dummyTile));
        assertTrue(startTile.sidesMatch("west", dummyTile));
        assertTrue(startTile.sidesMatch("east", dummyTile));
        assertTrue(startTile.sidesMatch("south", dummyTile));
        assertTrue(dummyTile.sidesMatch("north", startTile));

        assertTrue(startTile.sidesMatch("north", otherStart));
        assertTrue(otherStart.sidesMatch("south", startTile));
        assertTrue(startTile.sidesMatch("south", otherStart));
        assertTrue(otherStart.sidesMatch("north", startTile));

        assertFalse(startTile.sidesMatch("west", otherStart));
        assertFalse(otherStart.sidesMatch("west", startTile));
        assertFalse(startTile.sidesMatch("east", otherStart));
        assertFalse(otherStart.sidesMatch("east", startTile));

        otherStart.rotateTile();
        otherStart.rotateTile();

        assertTrue(startTile.sidesMatch("east", otherStart));
        assertTrue(otherStart.sidesMatch("west", startTile));

    }

    @Test
    public void testGetSegmentsOnSide() {
        Tile dummyTile = new Tile(dummyMap);
        ArrayList<Segment> northSegs = new ArrayList<>();
        ArrayList<Segment> westSegs = new ArrayList<>();
        ArrayList<Segment> southSegs = new ArrayList<>();
        ArrayList<Segment> eastSegs = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            northSegs.add(startingTile.getTile().get(0).get(i));
            westSegs.add(startingTile.getTile().get(i).get(0));
            southSegs.add(startingTile.getTile().get(4).get(i));
            eastSegs.add(startingTile.getTile().get(i).get(4));
        }
            assertEquals(startingTile.getSegmentsOnSide("north"), northSegs);
            assertEquals(startingTile.getSegmentsOnSide("west"), westSegs);
            assertEquals(startingTile.getSegmentsOnSide("south"), southSegs);
            assertEquals(startingTile.getSegmentsOnSide("east"), eastSegs);

            assertNotEquals(startingTile.getSegmentsOnSide("north"), dummyTile.getSegmentsOnSide("north"));


    }

    @Test
    public void testGetAndSetTile() {
        Tile tempTile = startingTile;
        ArrayList<ArrayList<Segment>> tileRep = startingTile.getTile();
        assertEquals(tileRep.size(), 5);
        assertEquals(tileRep.get(0).size(), 5);
        startingTile.setTile(tileRep);
        assertTrue(startingTile.identicalTile(tempTile));
    }

    @Test
    public void testGetAdjacentSegments() {
        Tile temp = startingTile;
        ArrayList<ArrayList<Segment>> tileRep = startingTile.getTile();
        ArrayList<Segment> adjSegs = startingTile.getAdjacentSegments(tileRep.get(0).get(0));
        assertEquals(adjSegs.size(), 2);
        assertTrue(adjSegs.contains(tileRep.get(1).get(0)));
        assertTrue(adjSegs.contains(tileRep.get(0).get(1)));

        adjSegs = startingTile.getAdjacentSegments(tileRep.get(3).get(2));
        assertEquals(adjSegs.size(), 4);
        assertTrue(adjSegs.contains(tileRep.get(4).get(2)));
        assertTrue(adjSegs.contains(tileRep.get(2).get(2)));
        assertTrue(adjSegs.contains(tileRep.get(3).get(1)));
        assertTrue(adjSegs.contains(tileRep.get(3).get(3)));
        assertTrue(temp.identicalTile(startingTile));
    }

    @Test
    public void testGetSegPositionOnTile() {
        Tile temp = startingTile;
        Segment seg = startingTile.getTile().get(2).get(3);
        Pair<Integer, Integer> foundSeg = startingTile.getSegPositionInTile(seg);
        assertEquals(new Integer(2), foundSeg.getKey());
        assertEquals(new Integer(3), foundSeg.getValue());
        assertNotEquals(foundSeg.getKey(), foundSeg.getValue());
        assertTrue(temp.identicalTile(startingTile));

    }

    @Test
    public void testContainsSegment() {
        Tile temp = startingTile;
        Tile newStartingTile = Tile.readInStartingTile();
        Tile dummyTile = Tile.createDummyTile();
        Segment seg = startingTile.getTile().get(3).get(3);
        assertTrue(startingTile.containsSegment(seg));
        assertFalse(dummyTile.containsSegment(seg));
        assertFalse(newStartingTile.containsSegment(seg));
        assertTrue(startingTile.identicalTile(temp));
    }

    @Test
    public void getAdjacentFieldSegmentsToFeature() {
        Tile newStartingTile = Tile.readInStartingTile();
        ArrayList<Segment> citySegs = startingTile.getSegmentsOnSide("east");
        Feature feat = new CityFeature(citySegs);
        Set<Segment> adjFields = new HashSet<>();
        adjFields.add(startingTile.getTile().get(1).get(3));
        adjFields.add(startingTile.getTile().get(2).get(3));
        adjFields.add(startingTile.getTile().get(3).get(3));
        Set<Segment> adjSet = new HashSet<>(startingTile.getAdjacentFieldSegmentsToFeature(feat));
        assertEquals(adjSet, adjFields);
        Tile dummyTile = Tile.createDummyTile();
        assertNotEquals(adjSet, new HashSet<>(dummyTile.getSegmentsOnSide("east")));
        assertTrue(newStartingTile.identicalTile(startingTile));

        startingTile.getTile().get(1).set(3, new Segment("road"));
        startingTile.getTile().get(2).set(3, new Segment("road"));
        startingTile.getTile().get(3).set(3, new Segment("road"));
        adjFields = new HashSet<>();
        adjFields.add(startingTile.getTile().get(1).get(3));
        adjFields.add(startingTile.getTile().get(2).get(3));
        adjFields.add(startingTile.getTile().get(3).get(3));
        adjSet = new HashSet<>(startingTile.getAdjacentFieldSegmentsToFeature(feat));
        assertEquals(adjSet.size(), 0);
        assertNotEquals(adjSet, adjFields);

    }

    @Test
    public void testAllCitySegs() {
        ArrayList<Segment> cities = new ArrayList<>();
        cities.add(startingTile.getTile().get(1).get(4));
        cities.add(startingTile.getTile().get(2).get(4));
        cities.add(startingTile.getTile().get(3).get(4));
        assertTrue(Tile.allCitySegs(cities));

        ArrayList<Segment> notCities = new ArrayList<>();
        notCities.add(startingTile.getTile().get(1).get(3));
        notCities.add(startingTile.getTile().get(2).get(3));
        notCities.add(startingTile.getTile().get(3).get(3));
        assertFalse(Tile.allCitySegs(notCities));

    }

    @Test
    public void testGetSegmentsInTile() {
        ArrayList<Segment> allSegs = startingTile.getSegmentsInTile();
        assertEquals(allSegs.size(), 25);
    }

    @Test
    public void testCitySides() {
        ArrayList<String> sides = new ArrayList<>();
        sides.add("east");
        ArrayList<String> cSides = startingTile.citySides();
        assertEquals(cSides, sides);
        Tile dummy = Tile.createDummyTile();
        sides = new ArrayList<>();
        cSides = dummy.citySides();
        assertEquals(cSides.size(), 0);
        assertEquals(cSides, sides);

    }

    @Test
    public void testIdenticalTile() {
        Tile anotherStart = Tile.readInStartingTile();
        assertTrue(anotherStart.identicalTile(startingTile));
        anotherStart.rotateTile();
        assertFalse(anotherStart.identicalTile(startingTile));
        anotherStart.rotateTile();
        assertFalse(anotherStart.identicalTile(startingTile));
        anotherStart.rotateTile();
        assertFalse(anotherStart.identicalTile(startingTile));
        anotherStart.rotateTile();
        assertTrue(anotherStart.identicalTile(startingTile));
        assertFalse((Tile.createDummyTile()).identicalTile(startingTile));

    }

    @Test
    public void testConstructTileFeatures() {
        Tile stillStart = Tile.readInStartingTile();
        Tile dummyTile = Tile.createDummyTile();
        assertEquals(dummyTile.constructTileFeatures().size(), 0);
        ArrayList<Feature> features = startingTile.constructTileFeatures();
        ArrayList<ArrayList<Segment>> tileBoard = startingTile.getTile();
        ArrayList<Segment> field1 = new ArrayList<>();
        ArrayList<Segment> field2 = new ArrayList<>();
        ArrayList<Segment> city = new ArrayList<>();
        ArrayList<Segment> road = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Segment seg = tileBoard.get(i).get(j);
                if (seg.sameSegmentType(new Segment("city"))) {
                    city.add(seg);
                }
                else if (seg.sameSegmentType(new Segment("road"))) {
                    road.add(seg);
                }
                else if (seg.sameSegmentType(new Segment("field"))) {
                    if (j < 2) {
                        field1.add(seg);
                    }
                    else {
                        field2.add(seg);
                    }
                }
            }
        }
        Feature field1Feat = new FieldFeature(field1);
        Feature field2Feat = new FieldFeature(field2);
        Feature cityFeat = new CityFeature(city);
        Feature roadFeat = new RoadFeature(road);
        assertEquals(features.size(), 4);
        assertEquals(features.get(0).segmentsInFeature(), field1Feat.segmentsInFeature());
        assertEquals(features.get(1).segmentsInFeature(), roadFeat.segmentsInFeature());
        assertEquals(features.get(2).segmentsInFeature(), field2Feat.segmentsInFeature());
        assertEquals(features.get(3).segmentsInFeature(), cityFeat.segmentsInFeature());

        assertTrue(stillStart.identicalTile(startingTile));

        Segment cloisterSeg = new Segment("cloister");
        Segment roadEnd1 = new Segment("roadEnd");
        Segment roadEnd2 = new Segment("roadEnd");

        tileBoard.get(2).set(2, cloisterSeg);
        tileBoard.get(1).set(2, roadEnd1);
        tileBoard.get(3).set(2, roadEnd2);

        startingTile.setTile(tileBoard);

        features = startingTile.constructTileFeatures();
        field1 = new ArrayList<>();
        field2 = new ArrayList<>();
        city = new ArrayList<>();
        ArrayList<Segment> road1 = new ArrayList<>();
        ArrayList<Segment> road2 = new ArrayList<>();
        ArrayList<Segment> cloister = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Segment seg = tileBoard.get(i).get(j);
                if (seg.sameSegmentType(new Segment("city"))) {
                    city.add(seg);
                }
                else if (seg.sameSegmentType(new Segment("road"))) {
                    if (i < 2) {
                        road1.add(seg);
                    }
                    else {
                        road2.add(seg);
                    }
                }
                else if (seg.sameSegmentType(new Segment("field"))) {
                    if (j < 2) {
                        field1.add(seg);
                    }
                    else {
                        field2.add(seg);
                    }
                }
                else if (seg.sameSegmentType(new Segment("cloister"))) {
                    cloister.add(seg);
                }
            }
        }

        field1Feat = new FieldFeature(field1);
        field2Feat = new FieldFeature(field2);
        cityFeat = new CityFeature(city);
        Feature road1Feat = new RoadFeature(road1);
        Feature road2Feat = new RoadFeature(road2);
        Feature cloisterFeat = new CloisterFeature(cloister);

        assertEquals(features.size(), 6);
        assertEquals(features.get(0).segmentsInFeature(), field1Feat.segmentsInFeature());
        assertEquals(features.get(1).segmentsInFeature(), road1Feat.segmentsInFeature());
        assertEquals(features.get(2).segmentsInFeature(), field2Feat.segmentsInFeature());
        assertEquals(features.get(3).segmentsInFeature(), cityFeat.segmentsInFeature());
        assertEquals(features.get(4).segmentsInFeature(), cloisterFeat.segmentsInFeature());
        assertEquals(features.get(5).segmentsInFeature(), road2Feat.segmentsInFeature());
        for (Feature feat : features) {
            assertFalse(feat.isComplete());
        }
    }

}
