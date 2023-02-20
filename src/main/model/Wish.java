package model;

// represents a single wish, with the banner it was done on, its result
public class Wish {
    //private Banner banner;
    private String result;
    private int rarity;

    public Wish(String result, int rarity) {
        //this.banner = banner;
        this.result = result;
        this.rarity = rarity;
    }

    public String getResult() {
        return result;
    }

    public int getRarity() {
        return rarity;
    }
}
