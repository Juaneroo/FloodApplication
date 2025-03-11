package com.flood_web.service.risk;

public enum RiskLevel {
    ZERO(1, "Sin riesgo"),

    VERY_LOW(2, "Riesgo muy bajo"),

    LOW(3, "Riesgo bajo"),

    MODERATE(4, "Riesgo moderado"),
    CONSIDERABLE(5, "Riesgo considerable"),

    HIGH(6, "Riesgo alto"),

    VERY_HIGH(7, "Riesgo muy alto"),

    IMMINENT_DANGER(8, "Peligro inminente"),

    EXTREME(9, "Riesgo extremo"),

    DISASTER(10, "Desastre inminente");

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
