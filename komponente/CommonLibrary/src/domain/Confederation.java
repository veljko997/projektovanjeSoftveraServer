package domain;

import java.io.Serializable;

public enum Confederation implements Serializable{

    EUROPE(1),
    SOUTHAMERICA(1),
    NORTHAMERICA(0.88),
    ASIA(0.86),
    AFRICA(0.86),
    OCEANIA(0.85);

    public double STRENGTH_POINTS;

    private Confederation(double STRENGTH_POINTS) {
        this.STRENGTH_POINTS = STRENGTH_POINTS;
    }
}
