package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.WishHistory;

import static org.junit.jupiter.api.Assertions.*;

class BannerTest {
    Banner b1;
    Banner b2;
    Banner b3;
    Wish testWish3;
    Wish testWish4;
    Wish testWish5;

    @BeforeEach
    public void runBefore() {
        b1 = new CharacterBanner();
        b2 = new WeaponBanner();
        b3 = new StandardBanner();
        testWish3 = new Wish("Skyrider Sword", 3);
        testWish4 = new Wish("Sacrificial Fragments", 4);
        testWish5 = new Wish("Diluc", 5);
    }

    @Test
    public void testAddOneWish() {
        b1.addWish(testWish3);
        assertEquals(testWish3, b1.getWishes().get(0));
        b2.addWish(testWish3);
        assertEquals(testWish3, b2.getWishes().get(0));
        b3.addWish(testWish3);
        assertEquals(testWish3, b3.getWishes().get(0));
    }

    @Test
    public void testAddManyWishes() {
        b1.addWish(testWish3);
        b1.addWish(testWish4);
        b1.addWish(testWish3);
        assertEquals(testWish3, b1.getWishes().get(0));
        assertEquals(testWish4, b1.getWishes().get(1));
        assertEquals(3, b1.getSize());
        assertEquals(false, b1.checkEmpty());
    }

    @Test
    public void testFiveStarPity() {
        for (int i = 0; i <= 9; i++) {
            b2.addWish(testWish3);
            b2.addWish(testWish4);
        }
        assertEquals(20, b2.getSize());
        assertEquals(20, b2.getFiveStarPity());
        b2.addWish(testWish5);
        assertEquals(0, b2.getFiveStarPity());
        b2.addWish(testWish4);
        assertEquals(1, b2.getFiveStarPity());
    }

    @Test
    public void testFourStarPityWithFourStar() {
        assertEquals(0, b3.getFourStarPity());
        for (int i = 0; i <= 7; i++) {
            b3.addWish(testWish3);
        }
        assertEquals(8, b3.getFourStarPity());
        b3.addWish(testWish4);
        assertEquals(0, b3.getFourStarPity());
    }

    @Test
    public void testFourStarPityWithFiveStar() {
        assertEquals(0, b3.getFourStarPity());
        for (int i = 0; i <= 4; i++) {
            b3.addWish(testWish3);
        }
        assertEquals(5, b3.getFourStarPity());
        b3.addWish(testWish5);
        assertEquals(0, b3.getFourStarPity());
    }

    @Test
    public void testCheckEmpty() {
        assertTrue(b1.checkEmpty());
        b1.addWish(testWish5);
        assertFalse(b1.checkEmpty());
    }

    @Test
    public void testFindRarity() {
        assertEquals(-1, b1.findRarity("Not An Item"));
        assertEquals(3, b1.findRarity("Debate Club"));
        assertEquals(4, b1.findRarity("Rainslasher"));
        assertEquals(5, b1.findRarity("Tighnari"));
    }

}