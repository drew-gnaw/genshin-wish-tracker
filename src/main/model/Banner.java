package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// represents the wishing history on a banner
public abstract class Banner {
    private static final List<String> FIVE_STARS = new ArrayList<>(Arrays.asList(
            "Albedo", "Alhaitham", "Aloy", "Ayaka", "Ayato", "Childe", "Cyno", "Dehya", "Diluc",
            "Eula", "Ganyu", "Hu Tao", "Itto", "Jean", "Kazuha", "Keqing", "Klee", "Kokomi", "Mona",
            "Nahida", "Nilou", "Qiqi", "Raiden Shogun", "Wanderer", "Shenhe", "Tighnari", "Venti",
            "Xiao", "Yae Miko", "Yelan", "Yoimiya", "Zhongli", "A Thousand Floating Dreams",
            "Amos' Bow", "Aqua Simulacra", "Aquila Favonia", "Beacon of the Reed Sea", "Calamity Queller",
            "Elegy for the End", "Engulfing Lightning", "Everlasting Moonglow", "Freedom-Sworn",
            "Haran Geppaku Futsu", "Hunter's Path", "Kagura's Verity", "Key of Khaj-Nisut",
            "Light of Foliar Incision", "Lost Prayer to the Sacred Winds", "Memory of Dust",
            "Mistsplitter Reforged", "Polar Star", "Primordial Jade Cutter", "Primordial Jade Winged-Spear",
            "Redhorn Stonethresher", "Skyward Atlas", "Skyward Blade", "Skyward Harp", "Skyward Pride",
            "Skyward Spine", "Song of Broken Pines", "Staff of Homa", "Staff of the Scarlet Sands",
            "Summit Shaper", "The Unforged", "Thundering Pulse", "Tulaytullah's Remembrance",
            "Vortex Vanquisher", "Wolf's Gravestone"));

    private static final List<String> FOUR_STARS = new ArrayList<>(Arrays.asList(
            "Faruzan", "Sayu", "Heizou", "Sucrose", "Chongyun", "Diona", "Kaeya", "Layla",
            "Rosaria", "Collei", "Beidou", "Dori", "Fischl", "Sara", "Shinobu", "Lisa",
            "Razor", "Gorou", "Ningguang", "Noelle", "Yun Jin", "Barbara", "Candace", "Xingqiu",
            "Amber", "Bennett", "Thoma", "Xiangling", "Xinyan", "Yanfei",
            "Favonius Warbow", "Rust", "Sacrificial Bow", "The Stringless", "Eye of Perception",
            "Favonius Codex", "Sacrificial Fragments", "The Widsith", "Favonius Greatsword",
            "Rainslasher", "Sacrificial Greatsword", "The Bell", "Dragon's Bane", "Favonius Lance",
            "Favonius Sword", "Lion's Roar", "Sacrificial Sword", "The Flute"));

    private static final List<String> THREE_STARS = new ArrayList<>(Arrays.asList(
            "Black Tassel", "Bloodtainted Greatsword", "Cool Steel", "Debate Club", "Emerald Orb",
            "Ferrous Shadow", "Harbinger of Dawn", "Magic Guide", "Raven Bow", "Sharpshooter's Oath",
            "Skyrider Sword", "Slingshot", "Thrilling Tales of Dragon Slayers"));

    protected List<Wish> wishes;

    public Banner() {
        wishes = new ArrayList<>();
    }

    public void addWish(Wish wish) {
        wishes.add(wish);
    }

    public int getFiveStarPity() {
        int count = 0;
        for (int i = wishes.size() - 1; i >= 0; i--) {
            if ((findRarity(wishes.get(i).getResult())) == 5) {
                return count;
            } else {
                count++;
            }
        }
        return wishes.size();
    }

    public int getFourStarPity() {
        for (int i = wishes.size() - 1; i >= 0; i--) {
            int count = 0;
            if ((findRarity(wishes.get(i).getResult())) >= 4) {
                return count;
            } else {
                count++;
            }
        }
        return wishes.size();
    }

    public int findRarity(String item) {
        if (FIVE_STARS.contains(item)) {
            return 5;
        } else if (FOUR_STARS.contains(item)) {
            return 4;
        } else if (THREE_STARS.contains(item)) {
            return 3;
        } else {
            return -1;
        }
    }

    public int getSize() {
        return wishes.size();
    }

    public boolean checkEmpty() {
        return (wishes.size() == 0);
    }

    public List<Wish> getWishes() {
        return wishes;
    }
}
