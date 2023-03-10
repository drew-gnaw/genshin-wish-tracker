package persistence;

import model.Wish;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkWish(String result, int rarity, Wish wish) {
        assertEquals(result, wish.getResult());
        assertEquals(rarity, wish.getRarity());
    }
}
