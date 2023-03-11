package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// represents the wishing history on the three types of banners
public class WishHistory implements Writable {

    private StandardBanner standardBannerHistory;
    private CharacterBanner characterBannerHistory;
    private WeaponBanner weaponBannerHistory;
    private Scanner input;

    public StandardBanner getStandardBannerHistory() {
        return standardBannerHistory;
    }

    public CharacterBanner getCharacterBannerHistory() {
        return characterBannerHistory;
    }

    public WeaponBanner getWeaponBannerHistory() {
        return weaponBannerHistory;
    }

    // EFFECTS: runs the wish tracker
    public WishHistory() {
        standardBannerHistory = new StandardBanner();
        characterBannerHistory = new CharacterBanner();
        weaponBannerHistory = new WeaponBanner();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("standard", standardWishesToJson());
        json.put("character", characterWishesToJson());
        json.put("weapon", weaponWishesToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray standardWishesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Wish w : standardBannerHistory.getWishes()) {
            jsonArray.put(w.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray characterWishesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Wish w : characterBannerHistory.getWishes()) {
            jsonArray.put(w.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray weaponWishesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Wish w : weaponBannerHistory.getWishes()) {
            jsonArray.put(w.toJson());
        }

        return jsonArray;
    }

}
