package domain;

import java.io.Serializable;

public enum MatchType implements Serializable{
    FriendlyGame(1),
    WorldCupQualifier(2.5),
    ConfederationCup(3),
    WorldCup(4);

    public double STRENGTH_POINTS;

    private MatchType(double STRENGTH_POINTS) {
        this.STRENGTH_POINTS = STRENGTH_POINTS;
    }
}
