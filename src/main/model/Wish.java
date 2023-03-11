package model;

import org.json.JSONObject;
import persistence.Writable;

// represents a single wish with its result and the rarity of the result
public class Wish implements Writable {
    //private Banner banner;
    private String result;
    private int rarity;

    //EFFECTS: constructs a new wish with specified result and rarity
    public Wish(String result, int rarity) {
        this.result = result;
        this.rarity = rarity;
    }

    //EFFECTS: returns the wish result
    public String getResult() {
        return result;
    }

    //EFFECTS: returns the rarity of the item
    public int getRarity() {
        return rarity;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("result", result);
        json.put("rarity", rarity);
        return json;
    }
}
