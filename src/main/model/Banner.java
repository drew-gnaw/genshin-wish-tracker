package model;

import java.util.List;

public class Banner {
    private final List<String> fiveStars;
    private final List<String> fourStars;

    public Banner(List<String> fiveStars, List<String> fourStars) {
        this.fiveStars = fiveStars;
        this.fourStars = fourStars;
    }

    public List<String> getFiveStars() {
        return fiveStars;
    }

    public List<String> getFourStars() {
        return fourStars;
    }
}
