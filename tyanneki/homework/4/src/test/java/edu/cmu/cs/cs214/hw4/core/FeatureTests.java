package edu.cmu.cs.cs214.hw4.core;

import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FeatureTests {
    CityFeature city;
    CityFeature city2;
    @Before
    public void setup() {
        ArrayList<Segment> c = new ArrayList<>();
        c.add(new Segment("city"));
        city = new CityFeature(c);
        ArrayList<Segment> ci = new ArrayList<>();
        ci.add(new Segment("city"));
        city2 = new CityFeature(ci);
    }

    @Test
    public void testIsComplete() {
        assertFalse(city.isComplete());
        city.setCompleted();
        assertTrue(city.isComplete());
    }

    @Test
    public void testCombineFeatures() {
        assertNotEquals(city, city2);
        city.combineFeatures(city2);
        ArrayList<Segment> segs = city.segmentsInFeature();
        ArrayList<Segment> segs2 = city2.segmentsInFeature();
        segs.addAll(segs2);
        assertEquals(city.segmentsInFeature(), segs);

    }
    @Test
    public void testFeatureType() {
        assertNotEquals(city.featureType(), "field");
        assertEquals(city.featureType(), "city");

        ArrayList<Segment> c = new ArrayList<>();
        c.add(new Segment("cityPennant"));
        CityFeature z = new CityFeature(c);
        assertEquals(z.featureType(), "city");

        c = new ArrayList<>();
        c.add(new Segment("roadEnd"));
        RoadFeature r = new RoadFeature(c);
        assertEquals(r.featureType(), "road");
    }

    @Test(expected = IllegalStateException.class)
    public void testFollowers() {
        Player player1 = new Player(1);
        Player player2 = new Player(2);

        ArrayList<Segment> c = new ArrayList<>();
        c.add(new Segment("cityPennant"));
        c.add(new Segment("city"));
        c.add(new Segment("city"));
        city = new CityFeature(c);
        city.playFollowerOnFeature(player1);
        assertEquals(city.mostFollowersOnFeature().size(), 1);
        assertEquals(city.mostFollowersOnFeature().get(0), player1);

        city2.playFollowerOnFeature(player2);
        city2.playFollowerOnFeature(player1);
        assertEquals(city2.mostFollowersOnFeature().size(), 1);
        assertEquals(city2.mostFollowersOnFeature().get(0), player2);
        city.combineFeatures(city2);
        assertEquals(city.mostFollowersOnFeature().size(), 2);
        assertTrue(city.mostFollowersOnFeature().contains(player1));
        assertTrue(city.mostFollowersOnFeature().contains(player2));
        city.removePlayersFollowersInFeature(player1);
        assertEquals(city.mostFollowersOnFeature().size(), 1);
        assertTrue(city.mostFollowersOnFeature().contains(player2));

        city.playFollowerOnFeature(player1);


    }
}
