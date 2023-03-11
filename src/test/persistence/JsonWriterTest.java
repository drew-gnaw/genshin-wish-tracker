package persistence;

import model.Wish;
import model.WishHistory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// This test class was adapted from the JsonSerializationDemo project
class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            WishHistory wh = new WishHistory();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWishHistory() {
        try {
            WishHistory wh = new WishHistory();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWishHistory.json");
            writer.open();
            writer.write(wh);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWishHistory.json");
            wh = reader.read();
            assertEquals(0, wh.getStandardBannerHistory().getWishes().size());
            assertEquals(0, wh.getWeaponBannerHistory().getWishes().size());
            assertEquals(0, wh.getCharacterBannerHistory().getWishes().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWishHistory() {
        try {
            WishHistory wh = new WishHistory();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWishHistory.json");
            wh.getCharacterBannerHistory().addWish(new Wish("Debate Club", 3));
            wh.getWeaponBannerHistory().addWish(new Wish("Rainslasher", 4));
            wh.getStandardBannerHistory().addWish(new Wish("Qiqi", 5));
            writer.open();
            writer.write(wh);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWishHistory.json");
            wh = reader.read();
            checkWish("Debate Club", 3, wh.getCharacterBannerHistory().getWishes().get(0));
            checkWish("Rainslasher", 4, wh.getWeaponBannerHistory().getWishes().get(0));
            checkWish("Qiqi", 5, wh.getStandardBannerHistory().getWishes().get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}