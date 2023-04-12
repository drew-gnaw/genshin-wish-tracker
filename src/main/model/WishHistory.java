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
    private EventLog eventLog;

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
        eventLog = EventLog.getInstance();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("standard", standardWishesToJson());
        json.put("character", characterWishesToJson());
        json.put("weapon", weaponWishesToJson());
        return json;
    }

    // EFFECTS: returns wishes on the standard banner as a JSON array
    private JSONArray standardWishesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Wish w : standardBannerHistory.getWishes()) {
            jsonArray.put(w.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns wishes on the character banner as a JSON array
    private JSONArray characterWishesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Wish w : characterBannerHistory.getWishes()) {
            jsonArray.put(w.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns wishes on the weapon banner as a JSON array
    private JSONArray weaponWishesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Wish w : weaponBannerHistory.getWishes()) {
            jsonArray.put(w.toJson());
        }

        return jsonArray;
    }

    public void recordWish(String banner, String result) {
        switch (banner) {
            case "c":
                characterBannerHistory.addWish(new Wish(result, characterBannerHistory.findRarity(result)));
                eventLog.logEvent(new Event("Added " + result + " to character banner"));
                break;
            case "w":
                weaponBannerHistory.addWish(new Wish(result, weaponBannerHistory.findRarity(result)));
                eventLog.logEvent(new Event("Added " + result + " to weapon banner"));
                break;
            case "s":
                standardBannerHistory.addWish(new Wish(result, standardBannerHistory.findRarity(result)));
                eventLog.logEvent(new Event("Added " + result + " to standard banner"));
        }
    }

    public void removeWish(String banner) {
        if (banner.equals("character")) {
            characterBannerHistory.getWishes().remove(characterBannerHistory.getSize() - 1);
            eventLog.logEvent(new Event("Removed latest wish from character banner"));
        } else if (banner.equals("weapon")) {
            weaponBannerHistory.getWishes().remove(weaponBannerHistory.getSize() - 1);
            eventLog.logEvent(new Event("Removed latest wish from weapon banner"));
        } else if (banner.equals("standard")) {
            standardBannerHistory.getWishes().remove(standardBannerHistory.getSize() - 1);
            eventLog.logEvent(new Event("Removed latest wish from standard banner"));
        }
    }

    // EFFECTS: analyzes the standard banner wishing history
    public String doStandardBannerAnalysis() {
        eventLog.logEvent(new Event("Analyzed standard banner"));
        return "You currently have " + standardBannerHistory.getFiveStarPity() + " pity. \n"
                + "The probability that you will pull a five-star item on your next wish is "
                + standardBannerHistory.calculateFiveStarProbability(90, 74, 0.6)
                + "%... \nand the probability that you will pull a four-star or better item on your next wish is "
                + standardBannerHistory.calculateFourStarProbability(5.1) + "%.";
    }

    // EFFECTS: analyzes the weapon banner wishing history
    public String doWeaponBannerAnalysis() {
        eventLog.logEvent(new Event("Analyzed weapon banner"));
        return "You currently have " + weaponBannerHistory.getFiveStarPity() + " pity. \n"
                + "The probability that you will pull a five-star item on your next wish is "
                + weaponBannerHistory.calculateFiveStarProbability(80, 63, 0.7)
                + "%... \nand the probability that you will pull a four-star or better item on your next wish is "
                + weaponBannerHistory.calculateFourStarProbability(6) + "%."
                + "\nYou have " + weaponBannerHistory.getFatePoints() + " Fate points.";
    }

    // EFFECTS: analyzes the character banner wishing history
    public String doCharacterBannerAnalysis() {
        eventLog.logEvent(new Event("Analyzed character banner"));
        return "You currently have " + characterBannerHistory.getFiveStarPity() + " pity. \n"
                + "The probability that you will pull a five-star item on your next wish is "
                + characterBannerHistory.calculateFiveStarProbability(90, 74, 0.6)
                + "%... \nand the probability that you will pull a four-star or better item on your next wish is "
                + characterBannerHistory.calculateFourStarProbability(5.1) + "%.";
    }
}
