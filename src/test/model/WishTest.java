package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class WishTest {
    Wish testWish3;
    Wish testWish4;
    Wish testWish5;

    @BeforeEach
    public void runBefore() {
        testWish3 = new Wish("Thrilling Tales of Dragon Slayers", 3);
        testWish4 = new Wish("Favonius bow", 4);
        testWish5 = new Wish("Qiqi", 5);
    }

    @Test
    public void testGetResult() {
        assertEquals("Qiqi", testWish5.getResult());
        assertEquals("Favonius bow", testWish4.getResult());
        assertEquals("Thrilling Tales of Dragon Slayers", testWish3.getResult());
    }

    @Test
    public void testGetRarity() {
        assertEquals(5, testWish5.getRarity());
        assertEquals(4, testWish4.getRarity());
        assertEquals(3, testWish3.getRarity());
    }
}
