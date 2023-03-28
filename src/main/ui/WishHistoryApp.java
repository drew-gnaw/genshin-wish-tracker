package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

// represents the wishing history on the three types of banners
public class WishHistoryApp extends JFrame {

    private static final String JSON_STORE = "./data/wishHistory.json";
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private WishHistory wishHistory;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private String activeBanner;
    private JButton recordButton;
    private JButton deleteButton;
    private JButton viewButton;
    private JButton analyzeButton;
    private JButton standardButton;
    private JButton weaponButton;
    private JButton characterButton;
    private JButton saveButton;
    private JButton loadButton;
    private JTextField textField;
    private JTextArea historyArea;
    private JTextArea analysisArea;
    private JLabel currentBanner;
    private JLabel picLabel;

    // MODIFIES: this
    // EFFECTS: runs the wish tracker
    public WishHistoryApp() {
        super("Wishing History");
        initializeGraphics();
        initializeActionButtons();
        try {
            initializeBannerButtons();
        } catch (IOException e) {
            System.out.println("IOEXCEPTION");
        }
        initializeTextArea();
        setVisible(true);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runWishTracker();
    }

    // MODIFIES: this
    // EFFECTS: initializes all action buttons
    private void initializeActionButtons() {
        JPanel buttonPanel = new JPanel();
        JPanel actionPanel = new JPanel();
        makeButtons();
        textField = new JTextField();
        buttonPanel.setLayout(new GridLayout(0, 1));
        buttonPanel.add(recordButton);
        buttonPanel.add(textField);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(analyzeButton);
        actionPanel.setLayout(new GridLayout(1, 0));
        actionPanel.add(buttonPanel);
        actionPanel.add(saveButton);
        actionPanel.add(loadButton);
        add(actionPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: initializes action panel buttons
    private void makeButtons() {
        initializeRecordButton();
        deleteButton = new JButton("Delete");
        handleDeleteButton();
        viewButton = new JButton("View History");
        handleViewButton();
        analyzeButton = new JButton("Analyze");
        handleAnalyzeButton();
        saveButton = new JButton("Save");
        handleSaveButton();
        loadButton = new JButton("Load");
        handleLoadButton();
    }

    // MODIFIES: this
    // EFFECTS: initializes banner option buttons and image
    private void initializeBannerButtons() throws IOException {
        JPanel bannerPanel = new JPanel();
        activeBanner = "Standard";
        initializeStandardButton();
        initializeWeaponButton();
        initializeCharacterButton();
        currentBanner = new JLabel(activeBanner);

        bannerPanel.setLayout(new BoxLayout(bannerPanel, BoxLayout.X_AXIS));
        bannerPanel.add(standardButton);
        bannerPanel.add(weaponButton);
        bannerPanel.add(characterButton);
        BufferedImage banner = ImageIO.read(new File("./images/standardBanner.jpeg"));
        picLabel = new JLabel(new ImageIcon(banner.getScaledInstance(400, 200, Image.SCALE_SMOOTH)));
        bannerPanel.add(picLabel);
        bannerPanel.add(Box.createVerticalStrut(200));
        add(bannerPanel, BorderLayout.NORTH);
        add(currentBanner, BorderLayout.EAST);
    }

    // MODIFIES: this
    // EFFECTS: initializes JFrame
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // EFFECTS: adds an ActionListener to save button
    private void handleSaveButton() {
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(wishHistory);
                    jsonWriter.close();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    // EFFECTS: adds an ActionListener to load button
    private void handleLoadButton() {
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    wishHistory = jsonReader.read();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: sets the text on the right side of GUI to activeBanner
    private void updateCurrentBanner() {
        currentBanner.setText(activeBanner);
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds an ActionListener to record button
    private void initializeRecordButton() {
        recordButton = new JButton("Record");
        recordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    handleRecordWish();
                } catch (IOException r) {
                    System.out.println("IOEXCEPTION");
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adds an ActionListener to analyze button
    private void handleAnalyzeButton() {
        analyzeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch (activeBanner) {
                    case "Standard":
                        analysisArea.setText(doStandardBannerAnalysis());
                        break;
                    case "Weapon":
                        analysisArea.setText(doWeaponBannerAnalysis());
                        break;
                    case "Character":
                        analysisArea.setText(doCharacterBannerAnalysis());
                        break;
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: records the wish from textField
    private void handleRecordWish() throws IOException {
        switch (activeBanner) {
            case "Standard":
                recordWish("s", textField.getText());
                break;
            case "Weapon":
                wishHistory.getWeaponBannerHistory().addWish(new Wish(textField.getText(), wishHistory
                        .getWeaponBannerHistory().findRarity(textField.getText())));
                break;
            case "Character":
                recordWish("c", textField.getText());
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds an ActionListener to delete button
    private void handleDeleteButton() {
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch (activeBanner) {
                    case "Standard":
                        if (!(wishHistory.getStandardBannerHistory().getWishes().size() == 0)) {
                            removeWish(activeBanner.toLowerCase());
                        }
                    case "Weapon":
                        if (!(wishHistory.getWeaponBannerHistory().getWishes().size() == 0)) {
                            removeWish(activeBanner.toLowerCase());
                        }
                    case "Character":
                        if (!(wishHistory.getCharacterBannerHistory().getWishes().size() == 0)) {
                            removeWish(activeBanner.toLowerCase());
                        }
                }

            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adds an ActionListener to view button
    private void handleViewButton() {
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                historyArea.setText(null);
                switch (activeBanner) {
                    case "Standard":
                        addAllWishes(wishHistory.getStandardBannerHistory().getWishes());
                        break;
                    case "Weapon":
                        addAllWishes(wishHistory.getWeaponBannerHistory().getWishes());
                        break;
                    case "Character":
                        addAllWishes(wishHistory.getCharacterBannerHistory().getWishes());
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adds wishes into the viewing window
    private void addAllWishes(List<Wish> wishes) {
        int count = 1;
        for (Wish w : wishes) {
            addToHistoryArea(count, w);
            count++;
        }
    }

    // REQUIRES: count >= 1
    // EFFECTS: prints a wish into the viewing window with appropriate formatting
    private void addToHistoryArea(int count, Wish w) {
        historyArea.append(count + ". " + w.getResult() + ", Rarity: " + w.getRarity() + "\n");
    }

    // MODIFIES: this
    // EFFECTS: initializes the viewing window
    private void initializeTextArea() {
        historyArea = new JTextArea(30, 20);
        historyArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(historyArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setLayout(new ScrollPaneLayout());
        add(scroll, BorderLayout.WEST);

        analysisArea = new JTextArea(20, 20);
        analysisArea.setEditable(false);
        add(analysisArea, BorderLayout.CENTER);

    }

    // MODIFIES: this
    // EFFECTS: initializes the standard button and adds appropriate ActionListener
    private void initializeStandardButton() {
        standardButton = new JButton("Standard");
        standardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                activeBanner = "Standard";
                updateCurrentBanner();
                picLabel.setIcon(new ImageIcon("./images/standardBanner.jpeg"));
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes the weapon button and adds appropriate ActionListener
    private void initializeWeaponButton() {
        weaponButton = new JButton("Weapon");
        weaponButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                activeBanner = "Weapon";
                updateCurrentBanner();
                picLabel.setIcon(new ImageIcon("./images/weaponBanner.jpeg"));
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes the character button and adds appropriate ActionListener
    private void initializeCharacterButton() {
        characterButton = new JButton("Character");
        characterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                activeBanner = "Character";
                updateCurrentBanner();
                picLabel.setIcon(new ImageIcon("./images/characterBanner.jpeg"));
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
                    System.out.println(doCharacterBannerAnalysis());
                    break;
                case "w":
                    System.out.println(doWeaponBannerAnalysis());
                    break;
                case "s":
                    System.out.println(doStandardBannerAnalysis());
            }
            return;
        }
        System.out.println("Invalid Input!");
    }

    // EFFECTS: analyzes the standard banner wishing history
    private String doStandardBannerAnalysis() {
        return "You currently have " + wishHistory.getStandardBannerHistory().getFiveStarPity() + " pity. \n"
                + "The probability that you will pull a five-star item on your next wish is "
                + wishHistory.getStandardBannerHistory().calculateFiveStarProbability(90, 74, 0.6)
                + "%... \nand the probability that you will pull a four-star or better item on your next wish is "
                + wishHistory.getStandardBannerHistory().calculateFourStarProbability(5.1) + "%.";
    }

    // EFFECTS: analyzes the weapon banner wishing history
    private String doWeaponBannerAnalysis() {
        return "You currently have " + wishHistory.getWeaponBannerHistory().getFiveStarPity() + " pity. \n"
                + "The probability that you will pull a five-star item on your next wish is "
                + wishHistory.getWeaponBannerHistory().calculateFiveStarProbability(80, 63, 0.7)
                + "%... \nand the probability that you will pull a four-star or better item on your next wish is "
                + wishHistory.getWeaponBannerHistory().calculateFourStarProbability(6) + "%."
                + "\nYou have " + wishHistory.getWeaponBannerHistory().getFatePoints() + " Fate points.";
    }

    // EFFECTS: analyzes the character banner wishing history
    private String doCharacterBannerAnalysis() {
        return "You currently have " + wishHistory.getCharacterBannerHistory().getFiveStarPity() + " pity. \n"
                + "The probability that you will pull a five-star item on your next wish is "
                + wishHistory.getCharacterBannerHistory().calculateFiveStarProbability(90, 74, 0.6)
                + "%... \nand the probability that you will pull a four-star or better item on your next wish is "
                + wishHistory.getCharacterBannerHistory().calculateFourStarProbability(5.1) + "%.";
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
