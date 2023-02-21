package ui;

import model.*;

import java.util.*;

// represents the wishing history on the three types of banners
public class WishHistory {

    private StandardBanner standardBannerHistory;
    private CharacterBanner characterBannerHistory;
    private WeaponBanner weaponBannerHistory;
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
            case "a": doAnalysis();
                break;
            case "v": viewWishHistory();
                break;
            default:
                System.out.println("Invalid Input!");
        }

    }

    // EFFECTS: deletes the most recent wish on a banner
    private void deleteWish() {
        System.out.println("\nWhich banner would you like to delete a wish from?");
        printBannerOptions();
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
        printBannerOptions();
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
            System.out.println(count + ". " + w.getResult() + ", rarity: " + banner.findRarity(w.getResult()));
            count++;
        }
        System.out.println("--------");
    }

    private void doAnalysis() {
        System.out.println("\nWhich banner would you like to analyze?");
        printBannerOptions();
        String banner = input.next();

        if (banner.equals("c") || banner.equals("w") || banner.equals("s")) {
            switch (banner) {
                case "c":
                    doCharacterBannerAnalysis();
                    break;
                case "w":
                    doWeaponBannerAnalysis();
                    break;
                case "s":
                    doStandardBannerAnalysis();
            }
            return;
        }
        System.out.println("Invalid Input!");
    }

    private void doStandardBannerAnalysis() {
        System.out.println("You currently have " + standardBannerHistory.getFiveStarPity() + " pity.");
        System.out.println("The probability that you will pull a five-star item on your next wish is "
                + standardBannerHistory.calculateFiveStarProbability(90, 74, 0.6)
                + "%...");
        System.out.println("and the probability that you will pull a four-star or better item on your next wish is "
                + standardBannerHistory.calculateFourStarProbability(5.1) + "%.");
    }

    private void doWeaponBannerAnalysis() {
        System.out.println("You currently have " + weaponBannerHistory.getFiveStarPity() + " pity.");
        System.out.println("The probability that you will pull a five-star item on your next wish is "
                + standardBannerHistory.calculateFiveStarProbability(80, 63, 0.7)
                + "%...");
        System.out.println("and the probability that you will pull a four-star or better item on your next wish is "
                + weaponBannerHistory.calculateFourStarProbability(6) + "%.");
        System.out.println("You have " + weaponBannerHistory.getFatePoints() + " Fate points.");
    }

    private void doCharacterBannerAnalysis() {
        System.out.println("You currently have " + characterBannerHistory.getFiveStarPity() + " pity.");
        System.out.println("The probability that you will pull a five-star item on your next wish is "
                + standardBannerHistory.calculateFiveStarProbability(90, 74, 0.6)
                + "%...");
        System.out.println("and the probability that you will pull a four-star or better item on your next wish is "
                + characterBannerHistory.calculateFourStarProbability(5.1) + "%.");
    }


    // MODIFIES: this
    // EFFECTS: adds a wish to the appropriate banner
    private void recordWish() {
        System.out.println("\nPlease choose the banner the wish was done on");
        printBannerOptions();
        String banner = input.next();

        if (banner.equals("c") || banner.equals("w") || banner.equals("s")) {
            System.out.println("\nPlease enter the character or weapon you obtained");
            String result = input.next();
            switch (banner) {
                case "c":
                    characterBannerHistory.addWish(new Wish(result, characterBannerHistory.findRarity(result)));
                    break;
                case "w":
                    weaponBannerHistory.addWish(new Wish(result, weaponBannerHistory.findRarity(result)));
                    if ((weaponBannerHistory.findRarity(result)) == 5) {
                        checkFatePoint();
                    }
                    break;
                case "s":
                    standardBannerHistory.addWish(new Wish(result, standardBannerHistory.findRarity(result)));
            }
            System.out.println("Recorded Wish!");
            return;
        }
        System.out.println("Invalid Input!");
    }

    private void printBannerOptions() {
        System.out.println("c -> Character Banner");
        System.out.println("w -> Weapon Banner");
        System.out.println("s -> Standard Banner");
    }

    private void checkFatePoint() {
        System.out.println("Was this five-star weapon the one on your epitomized path? (y/n)");
        boolean running = true;
        while (running) {
            String epitomized = input.next();
            if (epitomized.equals("y")) {
                weaponBannerHistory.resetFatePoints();
                running = false;
            } else if (epitomized.equals("n")) {
                weaponBannerHistory.addFatePoint();
                running = false;
            } else {
                System.out.println("Please enter y or n.");
            }
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
        System.out.println("a -> conduct analysis");
        System.out.println("v -> view wish history");
        System.out.println("q -> quit");
    }

}
