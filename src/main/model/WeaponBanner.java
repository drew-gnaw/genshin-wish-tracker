package model;

import java.util.List;

public class WeaponBanner extends Banner {
    private int fatePoints;

    public WeaponBanner() {
        super();
        fatePoints = 0;
    }

    public void addFatePoint() {
        fatePoints++;
    }

    public void resetFatePoints() {
        fatePoints = 0;
    }

    public int getFatePoints() {
        return fatePoints;
    }
}
