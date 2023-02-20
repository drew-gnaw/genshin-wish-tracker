package ui;

import model.*;

import java.util.*;

// represents the wishing history on the three types of banners
public class WishHistory {
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

    private Banner standardBannerHistory;
    private Banner characterBannerHistory;
    private Banner weaponBannerHistory;
    private Scanner input;

    public WishHistory() {
        runWishTracker();
    }

    public List<Wish> getStandardBannerHistory() {
        return standardBannerHistory.getWishes();
    }

    public List<Wish> getCharacterBannerHistory() {
        return characterBannerHistory.getWishes();
    }

    public List<Wish> getWeaponBannerHistory() {
        return weaponBannerHistory.getWishes();
    }

    private void runWishTracker() {
        boolean running = true;
        String cmd;
        init();

        while (running) {
            displayMenu();
            cmd = input.next();
            cmd = cmd.toLowerCase();

            if (cmd.equals("q")) {
                running = false;
            } else {
                processCommand(cmd);
            }
        }

        System.out.println("\nGoodbye!");


    }

    private void processCommand(String cmd) {
        switch (cmd) {
            case "r": recordWish();
                break;
            case "d": deleteWish();
                break;
            case "p": getprobabilities();
                break;
            case "v": viewWishHistory();
                break;
            default:
                System.out.println("Invalid Input!");
        }

    }

    // EFFECTS: deletes the most recent wish on a banner
    private void deleteWish() {
        System.out.println("\nWhich banner would you like to delete a wish from?\nc -> Character Banner");
        System.out.println("w -> Weapon Banner\ns -> Standard Banner");
        String banner = input.next();
        if (banner.equals("c")) {
            if (checkIfEmpty(characterBannerHistory.getWishes())) {
                return;
            }
            characterBannerHistory.getWishes().remove(characterBannerHistory.getSize() - 1);
        } else if (banner.equals("w")) {
            if (weaponBannerHistory.checkEmpty()) {
                return;
            }
            weaponBannerHistory.getWishes().remove(weaponBannerHistory.getSize() - 1);
        } else if (banner.equals("s")) {
            if (standardBannerHistory.checkEmpty()) {
                return;
            }
            standardBannerHistory.getWishes().remove(standardBannerHistory.getSize() - 1);
        } else {
            System.out.println("Invalid Input!");
            return;
        }
        System.out.println("Removed the latest wish!");
    }

    // EFFECTS: checks whether the given list is empty and produces appropriate response
    private boolean checkIfEmpty(List<Wish> wishes) {
        if (wishes.size() == 0) {
            System.out.printf("No Wishes to remove!\n");
        }
        return (wishes.size() == 0);
    }

    // EFFECTS: displays the wishing history for a chosen banner
    private void viewWishHistory() {
        System.out.println("\nWhich banner's history would you like to view?");
        System.out.println("c -> Character Banner");
        System.out.println("w -> Weapon Banner");
        System.out.println("s -> Standard Banner");
        String banner = input.next();
        if (banner.equals("c") || banner.equals("w") || banner.equals("s")) {
            switch (banner) {
                case "c":
                    printWishes(characterBannerHistory);
                    break;
                case "w":
                    printWishes(weaponBannerHistory);
                    break;
                case "s":
                    printWishes(standardBannerHistory);
            }
            return;
        }

        System.out.println("Invalid Input!");
    }

    // EFFECTS: prints out the contents of a banner history
    private void printWishes(Banner banner) {
        int count = 1;
        List<Wish> wishes = banner.getWishes();
        System.out.println("--------");
        for (Wish w : wishes) {
            System.out.println(count + ". " + w.getResult() + ", rarity: " + w.getRarity());
        }
        System.out.println("--------");
    }

    private void getprobabilities() {
    }

    // MODIFIES: this
    // EFFECTS: adds a wish to the appropriate banner
    private void recordWish() {
        System.out.println("\nPlease choose the banner the wish was done on");
        System.out.println("c -> Character Banner");
        System.out.println("w -> Weapon Banner");
        System.out.println("s -> Standard Banner");
        String banner = input.next();

        if (banner.equals("c") || banner.equals("w") || banner.equals("s")) {
            System.out.println("\nPlease enter the character or weapon you obtained");
            String result = input.next();
            switch (banner) {
                case "c":
                    characterBannerHistory.addWish(new Wish(result, getRarity(result)));
                    break;
                case "w":
                    weaponBannerHistory.addWish(new Wish(result, getRarity(result)));
                    break;
                case "s":
                    standardBannerHistory.addWish(new Wish(result, getRarity(result)));
            }
            System.out.println("Recorded Wish!");
            return;
        }

        System.out.println("Invalid Input!");
    }

    private int getRarity(String item) {
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

    // MODIFIES: this
    // EFFECTS: initializes banner histories
    private void init() {
        characterBannerHistory = new CharacterBanner();
        weaponBannerHistory = new WeaponBanner();
        standardBannerHistory = new StandardBanner();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    private void displayMenu() {
        System.out.println("Please choose a command:");
        System.out.println("r -> record wish");
        System.out.println("d -> delete wish");
        System.out.println("p -> get probabilities");
        System.out.println("v -> view wish history");
        System.out.println("q -> quit");
    }

}
