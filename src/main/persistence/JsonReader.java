package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Wish;
import org.json.*;
import model.WishHistory;


import static java.lang.Integer.valueOf;

// Represents a reader that reads wishing history from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads wish history from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WishHistory read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWishHistory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses wish history from JSON object and returns it
    private WishHistory parseWishHistory(JSONObject jsonObject) {
        WishHistory wh = new WishHistory();
        addWishes(wh, jsonObject, "standard");
        addWishes(wh, jsonObject, "character");
        addWishes(wh, jsonObject, "weapon");
        return wh;
    }

    // MODIFIES: wh
    // EFFECTS: parses standard wishes from JSON object and adds them to wish history's specified banner
    private void addWishes(WishHistory wh, JSONObject jsonObject, String banner) {
        JSONArray jsonArray = jsonObject.getJSONArray(banner);
        for (Object json : jsonArray) {
            JSONObject nextWish = (JSONObject) json;
            addWish(wh, nextWish, banner);
        }
    }

    // MODIFIES: wh
    // EFFECTS: parses wish from JSON object and adds it to wish history's specified banner
    private void addWish(WishHistory wh, JSONObject jsonObject, String banner) {
        String result = jsonObject.getString("result");
        int rarity = jsonObject.getInt("rarity");
        Wish wish = new Wish(result, rarity);
        switch (banner) {
            case "standard":
                wh.getStandardBannerHistory().addWish(wish);
                break;
            case "character":
                wh.getCharacterBannerHistory().addWish(wish);
                break;
            case "weapon":
                wh.getWeaponBannerHistory().addWish(wish);
                break;
        }

    }

}
