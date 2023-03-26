package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

// represents the wishing history on the three types of banners
public class WishHistoryApp extends JFrame {

    private static final String JSON_STORE = "./data/wishHistory.json";
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private WishHistory wishHistory;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private String activeBanner;
    private JPanel buttonPanel;
    private JPanel bannerPanel;
    private JButton recordButton;
    private JButton deleteButton;
    private JButton viewButton;
    private JButton analyzeButton;
    private JButton standardButton;
    private JButton weaponButton;
    private JButton characterButton;
    private JLabel currentBanner;

    public StandardBanner getStandardBannerHistory() {
        return wishHistory.getStandardBannerHistory();
    }

    public CharacterBanner getCharacterBannerHistory() {
        return wishHistory.getCharacterBannerHistory();
    }

    public WeaponBanner getWeaponBannerHistory() {
        return wishHistory.getWeaponBannerHistory();
    }

    // EFFECTS: runs the wish tracker
    public WishHistoryApp() {
        super("Wishing History");
        initializeGraphics();
        initializeActionButtons();
        initializeBannerButtons();
        setVisible(true);
        runWishTracker();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);


    }

    private void initializeActionButtons() {
        buttonPanel = new JPanel();
        recordButton = new JButton("Record");
        deleteButton = new JButton("Delete");
        viewButton = new JButton("View History");
        analyzeButton = new JButton("Analyze");

        buttonPanel.setLayout(new GridLayout(0, 1));
        buttonPanel.add(recordButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(analyzeButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initializeBannerButtons() {
        bannerPanel = new JPanel();
        activeBanner = "Standard";
        initializeStandardButton();
        initializeWeaponButton();
        initializeCharacterButton();
        currentBanner = new JLabel(activeBanner);

        bannerPanel.setLayout(new BoxLayout(bannerPanel, BoxLayout.X_AXIS));
        bannerPanel.add(standardButton);
        bannerPanel.add(weaponButton);
        bannerPanel.add(characterButton);

        add(bannerPanel, BorderLayout.NORTH);
        add(currentBanner, BorderLayout.EAST);
    }

    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void updateCurrentBanner() {
        currentBanner.setText(activeBanner);
    }

    private void initializeStandardButton() {
        standardButton = new JButton("Standard");
        standardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                activeBanner = "Standard";
                updateCurrentBanner();
            }
        });
    }

    private void initializeWeaponButton() {
        weaponButton = new JButton("Weapon");
        weaponButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                activeBanner = "Weapon";
                updateCurrentBanner();
            }
        });
    }

    private void initializeCharacterButton() {
        characterButton = new JButton("Character");
        characterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                activeBanner = "Character";
                updateCurrentBanner();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: allows user to give input
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

    // MODIFIES: this
    // EFFECTS: processes a command
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
            case "s": saveWishHistory();
                break;
            case "l": loadWishHistory();
                break;
            default:
                System.out.println("Invalid Input!");
        }
    }

    // EFFECTS: saves the wish history to file
    private void saveWishHistory() {
        try {
            jsonWriter.open();
            jsonWriter.write(wishHistory);
            jsonWriter.close();
            System.out.println("Saved current wishing history to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads wish history from file
    private void loadWishHistory() {
        try {
            wishHistory = jsonReader.read();
            System.out.println("Loaded wishing history from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the most recent wish on a banner
    private void deleteWish() {
        System.out.println("\nWhich banner would you like to delete a wish from?");
        printBannerOptions();
        String banner = input.next();
        if (banner.equals("c")) {
            if (checkIfEmpty(wishHistory.getCharacterBannerHistory().getWishes())) {
                return;
            }
            removeWish("character");
        } else if (banner.equals("w")) {
            if (wishHistory.getWeaponBannerHistory().checkEmpty()) {
                return;
            }
            removeWish("weapon");
        } else if (banner.equals("s")) {
            if (wishHistory.getStandardBannerHistory().checkEmpty()) {
                return;
            }
            removeWish("standard");
        } else {
            System.out.println("Invalid Input!");
            return;
        }
        System.out.println("Removed the latest wish!");
    }

    private void removeWish(String banner) {
        if (banner.equals("character")) {
            wishHistory.getCharacterBannerHistory().getWishes()
                    .remove(wishHistory.getCharacterBannerHistory().getSize() - 1);
        } else if (banner.equals("weapon")) {
            wishHistory.getWeaponBannerHistory().getWishes()
                    .remove(wishHistory.getWeaponBannerHistory().getSize() - 1);
        } else if (banner.equals("standard")) {
            wishHistory.getStandardBannerHistory().getWishes()
                    .remove(wishHistory.getStandardBannerHistory().getSize() - 1);
        }
    }

    // EFFECTS: checks whether the given list is empty and produces appropriate response
    private boolean checkIfEmpty(List<Wish> wishes) {
        if (wishes.size() == 0) {
            System.out.println("No Wishes to remove!\n");
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
                    printWishes(wishHistory.getCharacterBannerHistory());
                    break;
                case "w":
                    printWishes(wishHistory.getWeaponBannerHistory());
                    break;
                case "s":
                    printWishes(wishHistory.getStandardBannerHistory());
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

    // EFFECTS: analyzes the probabilities on a banner
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

    // EFFECTS: analyzes the standard banner wishing history
    private void doStandardBannerAnalysis() {
        System.out.println("You currently have " + wishHistory.getStandardBannerHistory().getFiveStarPity() + " pity.");
        System.out.println("The probability that you will pull a five-star item on your next wish is "
                + wishHistory.getStandardBannerHistory().calculateFiveStarProbability(90, 74, 0.6)
                + "%...");
        System.out.println("and the probability that you will pull a four-star or better item on your next wish is "
                + wishHistory.getStandardBannerHistory().calculateFourStarProbability(5.1) + "%.");
    }

    // EFFECTS: analyzes the weapon banner wishing history
    private void doWeaponBannerAnalysis() {
        System.out.println("You currently have " + wishHistory.getWeaponBannerHistory().getFiveStarPity() + " pity.");
        System.out.println("The probability that you will pull a five-star item on your next wish is "
                + wishHistory.getWeaponBannerHistory().calculateFiveStarProbability(80, 63, 0.7)
                + "%...");
        System.out.println("and the probability that you will pull a four-star or better item on your next wish is "
                + wishHistory.getWeaponBannerHistory().calculateFourStarProbability(6) + "%.");
        System.out.println("You have " + wishHistory.getWeaponBannerHistory().getFatePoints() + " Fate points.");
    }

    // EFFECTS: analyzes the character banner wishing history
    private void doCharacterBannerAnalysis() {
        System.out.println("You currently have " + wishHistory
                .getCharacterBannerHistory().getFiveStarPity() + " pity.");
        System.out.println("The probability that you will pull a five-star item on your next wish is "
                + wishHistory.getCharacterBannerHistory().calculateFiveStarProbability(90, 74, 0.6)
                + "%...");
        System.out.println("and the probability that you will pull a four-star or better item on your next wish is "
                + wishHistory.getCharacterBannerHistory().calculateFourStarProbability(5.1) + "%.");
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
            recordWish(banner, result);
            System.out.println("Recorded Wish!");
            return;
        }
        System.out.println("Invalid Input!");
    }

    // MODIFIES: this
    // EFFECTS: adds wish to appropriate banner and checks fate points
    private void recordWish(String banner, String result) {
        switch (banner) {
            case "c":
                wishHistory.getCharacterBannerHistory().addWish(new Wish(result, wishHistory
                        .getCharacterBannerHistory().findRarity(result)));
                break;
            case "w":
                wishHistory.getWeaponBannerHistory().addWish(new Wish(result, wishHistory
                        .getWeaponBannerHistory().findRarity(result)));
                if ((wishHistory.getWeaponBannerHistory().findRarity(result)) == 5) {
                    checkFatePoint();
                }
                break;
            case "s":
                wishHistory.getStandardBannerHistory().addWish(new Wish(result, wishHistory
                        .getStandardBannerHistory().findRarity(result)));
        }
    }

    // EFFECTS: shows banner options to user
    private void printBannerOptions() {
        System.out.println("c -> Character Banner");
        System.out.println("w -> Weapon Banner");
        System.out.println("s -> Standard Banner");
    }

    // MODIFIES: this
    // EFFECTS: adjusts fate points on the weapon banner according to user input
    private void checkFatePoint() {
        System.out.println("Was this five-star weapon the one on your epitomized path? (y/n)");
        boolean running = true;
        while (running) {
            String epitomized = input.next();
            if (epitomized.equals("y")) {
                wishHistory.getWeaponBannerHistory().resetFatePoints();
                running = false;
            } else if (epitomized.equals("n")) {
                wishHistory.getWeaponBannerHistory().addFatePoint();
                running = false;
            } else {
                System.out.println("Please enter y or n.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes banner histories
    private void init() {
        wishHistory = new WishHistory();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: shows main menu options to user
    private void displayMenu() {
        System.out.println("Please choose a command:");
        System.out.println("r -> record wish");
        System.out.println("d -> delete wish");
        System.out.println("a -> conduct analysis");
        System.out.println("v -> view wish history");
        System.out.println("s -> save wish history to file");
        System.out.println("l -> load wish history from file");
        System.out.println("q -> quit");
    }

}
