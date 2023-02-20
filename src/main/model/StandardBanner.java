package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StandardBanner extends Banner {
    private final List<String> fiveStarChars =
            new ArrayList<>(Arrays.asList("Jean", "Qiqi", "Tighnari", "Keqing", "Mona", "Diluc"));
    private final List<String> fiveStarWeapons =
            new ArrayList<>(Arrays.asList("Amos' Bow", "Skyward Harp", "Lost Prayer to the Sacred Winds",
                    "Skyward Atlas", "Skyward Pride", "Wolf's Gravestone", "Primordial Jade Winged-Spear",
                    "Skyward Spine", "Skyward Blade", "Aquila Favonia"));

    public StandardBanner() {
        super(new ArrayList<>(Arrays.asList("Jean", "Qiqi", "Tighnari", "Keqing", "Mona", "Diluc")),
                new ArrayList<>(Arrays.asList("Amos' Bow", "Skyward Harp", "Lost Prayer to the Sacred Winds",
                        "Skyward Atlas", "Skyward Pride", "Wolf's Gravestone", "Primordial Jade Winged-Spear",
                        "Skyward Spine", "Skyward Blade", "Aquila Favonia")));
    }
}
