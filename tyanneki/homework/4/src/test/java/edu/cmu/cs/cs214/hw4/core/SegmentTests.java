package edu.cmu.cs.cs214.hw4.core;

import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SegmentTests {

    private Segment dummySegment;
    private Segment roadSegment;
    private Segment cloisterSegment;
    private Segment fieldSegment;
    private Segment citySegment;
    private Segment cityPennantSegment;
    private Segment roadEndSegment;

    @Before
    public void setup() {
        dummySegment = new Segment("dummy");
        roadSegment = new Segment("road");
        cloisterSegment = new Segment("cloister");
        fieldSegment = new Segment("field");
        citySegment = new Segment("city");
        cityPennantSegment = new Segment("cityPennant");
        roadEndSegment = new Segment("roadEnd");
    }

    @Test
    public void testIsDummySegment() {
        assertTrue(dummySegment.isDummySegment());
        assertFalse(roadSegment.isDummySegment());
        assertFalse(citySegment.isDummySegment());
    }

    @Test
    public void testSegmentsMatch() {
        assertTrue(Segment.segmentsMatch(dummySegment, roadSegment));
        assertTrue(Segment.segmentsMatch(roadSegment, dummySegment));
        assertTrue(Segment.segmentsMatch(fieldSegment, dummySegment));
        assertTrue(Segment.segmentsMatch(fieldSegment, fieldSegment));
        assertTrue(Segment.segmentsMatch(dummySegment, dummySegment));
        assertTrue(Segment.segmentsMatch(cloisterSegment, cloisterSegment));
        assertTrue(Segment.segmentsMatch(roadSegment, roadSegment));
        assertTrue(Segment.segmentsMatch(citySegment, citySegment));
        assertTrue(Segment.segmentsMatch(cityPennantSegment, cityPennantSegment));
        assertTrue(Segment.segmentsMatch(citySegment, cityPennantSegment));
        assertTrue(Segment.segmentsMatch(cityPennantSegment, citySegment));
        assertTrue(Segment.segmentsMatch(roadEndSegment, roadSegment));
        assertTrue(Segment.segmentsMatch(roadSegment, roadEndSegment));
        assertFalse(Segment.segmentsMatch(roadEndSegment, cityPennantSegment));
        assertFalse(Segment.segmentsMatch(fieldSegment, cloisterSegment));
        assertFalse(Segment.segmentsMatch(cloisterSegment, fieldSegment));
        assertFalse(Segment.segmentsMatch(roadSegment, cloisterSegment));

    }

    @Test
    public void testSameSegmentType() {
        Segment dummy = new Segment("dummy");
        assertTrue(dummy.sameSegmentType(dummySegment));
        assertTrue(dummySegment.sameSegmentType(dummy));
        Segment road = new Segment("road");
        assertTrue(road.sameSegmentType(roadSegment));
        assertTrue(roadSegment.sameSegmentType(road));
        Segment field = new Segment("field");
        assertTrue(field.sameSegmentType(fieldSegment));
        assertTrue(fieldSegment.sameSegmentType(field));
        Segment cloister = new Segment("cloister");
        assertTrue(cloister.sameSegmentType(cloisterSegment));
        assertTrue(cloisterSegment.sameSegmentType(cloister));
        Segment city = new Segment("city");
        assertTrue(city.sameSegmentType(citySegment));
        assertTrue(citySegment.sameSegmentType(city));
        Segment cityPennant = new Segment("cityPennant");
        assertTrue(cityPennant.sameSegmentType(cityPennantSegment));
        assertTrue(cityPennantSegment.sameSegmentType(cityPennant));
        Segment roadEnd = new Segment("roadEnd");
        assertTrue(roadEnd.sameSegmentType(roadEndSegment));
        assertTrue(roadEndSegment.sameSegmentType(roadEnd));

        assertFalse(roadEndSegment.sameSegmentType(cityPennantSegment));
        assertFalse(cloisterSegment.sameSegmentType(roadSegment));

    }

    @Test
    public void testGetFollowerOnSegment() {
        Segment seg = new Segment("road");
        assertEquals(null, seg.getFollowerOnSegment());
        seg.playFollowerOnSegment(new Player(1));
        assertNotEquals(null, seg.getFollowerOnSegment());
    }

    @Test
    public void testHasFollower() {
        Segment seg = new Segment("road");
        assertFalse(seg.hasFollower());
        seg.playFollowerOnSegment(new Player(1));
        assertTrue(seg.hasFollower());
    }

    @Test
    public void testRemoveFollower() {
        Segment seg = new Segment("road");
        Player player = new Player(1);
        seg.playFollowerOnSegment(player);
        assertTrue(seg.hasFollower());
        assertEquals(player.followersInPlay(), 1);
        seg.removeFollower();
        assertFalse(seg.hasFollower());
    }

    @Test
    public void testPlayFollowerOnSegment() {
        ArrayList<Follower> followers = new ArrayList<>();
        ArrayList<Segment> segments = new ArrayList<>();
        Player player1 = new Player(1);
        Player player2 = new Player(2);
        for (int i = 0; i < 8; i++) {
            segments.add(new Segment("cloister"));
            assertFalse(segments.get(i).hasFollower());
        }
        for (int i = 0; i < 7; i++) {
            Segment seg = segments.get(i);
            seg.playFollowerOnSegment(player1);
            assertTrue(seg.hasFollower());
            assertEquals(player1.followersInPlay(), i + 1);
            followers.add(seg.getFollowerOnSegment());
        }
        segments.get(7).playFollowerOnSegment(player2);
        assertTrue(segments.get(7).hasFollower());
        assertEquals(player2.followersInPlay(), 1);
        segments.get(7).removeFollower();
        for (int i = 0; i < 7; i++) {
            Segment seg = segments.get(i);
            Follower f = seg.getFollowerOnSegment();
            assertEquals(f, followers.get(i));
        }

    }

    @Test(expected = IllegalStateException.class)
    public void testPlayTooManyFollowersForPlayer() {
        Player player1 = new Player(1);
        for (int i = 0; i < 8; i++) {
            Segment seg = new Segment("cloister");
            seg.playFollowerOnSegment(player1);
        }

    }

    @Test(expected = IllegalStateException.class)
    public void testPlayFollowerOnOccupiedSegment() {
        Player player1 = new Player(1);
        Player player2 = new Player(2);
        Segment seg = new Segment("cloister");
        seg.playFollowerOnSegment(player1);
        seg.playFollowerOnSegment(player2);

    }

    @Test
    public void testIsRoadEnd() {
        Segment seg = new Segment("roadEnd");
        Segment seg2 = new Segment("road");
        assertTrue(seg.isRoadEnd());
        assertFalse(seg2.isRoadEnd());

    }
    @Test
    public void testHasPennant() {
        Segment seg = new Segment("cityPennant");
        Segment seg2 = new Segment("city");
        assertTrue(seg.hasPennant());
        assertFalse(seg2.hasPennant());

    }
}
