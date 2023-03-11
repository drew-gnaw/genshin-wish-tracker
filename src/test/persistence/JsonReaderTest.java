package persistence;

import org.junit.jupiter.api.Test;
import model.WishHistory;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// This test class was adapted from the JsonSerializationDemo project
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WishHistory wh = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWishHistory.json");
        try {
            WishHistory wh = reader.read();
            assertTrue(wh.getStandardBannerHistory().checkEmpty());
            assertTrue(wh.getCharacterBannerHistory().checkEmpty());
            assertTrue(wh.getWeaponBannerHistory().checkEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWishHistory.json");
        try {
            WishHistory wh = reader.read();
            assertEquals(1, wh.getStandardBannerHistory().getWishes().size());
            checkWish("Debate Club", 3, wh.getStandardBannerHistory().getWishes().get(0));
            checkWish("Sacrificial Sword", 4, wh.getCharacterBannerHistory().getWishes().get(0));
            checkWish("Diluc", 5, wh.getCharacterBannerHistory().getWishes().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}