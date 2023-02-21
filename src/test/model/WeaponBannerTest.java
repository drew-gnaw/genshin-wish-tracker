package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeaponBannerTest {
    WeaponBanner testWeaponBanner;

    @BeforeEach
    public void runBefore() {
        testWeaponBanner = new WeaponBanner();
    }

    @Test
    public void testAddFatePoint() {
        testWeaponBanner.addFatePoint();
        assertEquals(1, testWeaponBanner.getFatePoints());
    }

    @Test
    public void testResetFatePoints() {
        testWeaponBanner.addFatePoint();
        testWeaponBanner.addFatePoint();
        assertEquals(2, testWeaponBanner.getFatePoints());
        testWeaponBanner.resetFatePoints();
        assertEquals(0, testWeaponBanner.getFatePoints());
    }

}
