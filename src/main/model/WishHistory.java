package ui;

import model.*;

import java.util.*;

// represents the wishing history on the three types of banners
public class WishHistory {

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

}
