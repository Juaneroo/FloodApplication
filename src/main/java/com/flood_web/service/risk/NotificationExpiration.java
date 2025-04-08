package com.flood_web.service.risk;

/**
 * Enum representing notification expiration times in hours for different risk levels.
 * This makes the code more readable and maintainable by centralizing the expiration time logic.
 */
public enum NotificationExpiration {
    TWELVE_HOURS(12),
    EIGHT_HOURS(8),
    SIX_HOURS(6),
    FOUR_HOURS(4),
    TWO_HOURS(2),
    ONE_HOUR(1);

    private final long hours;

    NotificationExpiration(long hours) {
        this.hours = hours;
    }

    public long getHours() {
        return hours;
    }

    /**
     * Gets the expiration time for a given risk level.
     *
     * @param riskLevel The risk level to get expiration time for
     * @return The expiration time in hours
     * @throws IllegalArgumentException if the risk level is not supported for notifications
     */
    public static long getExpirationHours(RiskLevel riskLevel) {
        return switch (riskLevel) {
            case CONSIDERABLE -> TWELVE_HOURS.getHours();
            case HIGH -> EIGHT_HOURS.getHours();
            case VERY_HIGH -> SIX_HOURS.getHours();
            case IMMINENT_DANGER -> FOUR_HOURS.getHours();
            case EXTREME -> TWO_HOURS.getHours();
            case DISASTER -> ONE_HOUR.getHours();
            default -> throw new IllegalArgumentException("Invalid risk level for notification: " + riskLevel);
        };
    }
} 