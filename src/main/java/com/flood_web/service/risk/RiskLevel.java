package com.flood_web.service.risk;

public enum RiskLevel {
    ZERO(1, "No risk"),
    VERY_LOW(2, "Very low risk"),
    LOW(3, "Low risk"),
    MODERATE(4, "Moderate risk"),
    CONSIDERABLE(5, "Considerable risk"),
    HIGH(6, "High risk"),
    VERY_HIGH(7, "Very high risk"),
    IMMINENT_DANGER(8, "Imminent danger"),
    EXTREME(9, "Extreme risk"),
    DISASTER(10, "Disaster imminent");

    private final int level;
    private final String description;

    RiskLevel(int level, String description) {
        this.level = level;
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    // Get risk level from number
    public static RiskLevel fromNumber(int number) {
        for (RiskLevel rl : values()) {
            if (rl.getLevel() == number) {
                return rl;
            }
        }
        return null;
    }
}
