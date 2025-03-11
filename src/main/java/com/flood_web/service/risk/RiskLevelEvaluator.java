package com.flood_web.service.risk;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.*;

@Component
public class RiskLevelEvaluator {

    /**
     * Evaluates if the given expression is valid.
     * @param expression The risk-level expression (multiline string).
     * @return True if valid, false if there are inconsistencies.
     */
    public boolean evaluateExpression(String expression) {
        return getInconsistencies(expression).equals("No inconsistencies found.");
    }

    /**
     * Checks for inconsistencies in the given expression.
     * @param expression The risk-level expression (multiline string).
     * @return A string listing the inconsistencies found.
     */
    public String getInconsistencies(String expression) {
        if(StringUtils.isBlank(expression)){
            return "No inconsistencies found.";
        }

        List<String> errors = new ArrayList<>();
        Pattern pattern = Pattern.compile("^(\\d+)=(\\d+)m$", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(expression);

        Map<RiskLevel, Integer> riskMap = new TreeMap<>(Comparator.comparingInt(RiskLevel::getLevel));
        Map<Integer, String> lineMap = new HashMap<>(); // Store original line reference

        String[] lines = expression.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;

            matcher = pattern.matcher(line);
            if (!matcher.matches()) {
                errors.add("Error at line " + (i + 1) + " ('" + line + "'): Invalid format. Expected 'risk=heightM'.");
                continue;
            }

            int riskNumber = Integer.parseInt(matcher.group(1));
            int height = Integer.parseInt(matcher.group(2));

            RiskLevel riskLevel = RiskLevel.fromNumber(riskNumber);
            if (riskLevel == null) {
                errors.add("Error at line " + (i + 1) + " ('" + line + "'): Invalid risk level (" + riskNumber + "). Must be between 1 and 10.");
                continue;
            }

            if (height < 0) {
                errors.add("Error at line " + (i + 1) + " ('" + line + "'): Height cannot be negative (" + height + "m).");
            }

            if (riskMap.containsKey(riskLevel)) {
                errors.add("Error at line " + (i + 1) + " ('" + line + "'): Duplicate risk level '" + riskLevel.getDescription() + "', first defined at " + lineMap.get(riskNumber) + ".");
            }

            riskMap.put(riskLevel, height);
            lineMap.put(riskNumber, "line " + (i + 1) + " ('" + line + "')");
        }

        // Validate height progression
        int previousHeight = -1;
        String previousLine = "";
        for (Map.Entry<RiskLevel, Integer> entry : riskMap.entrySet()) {
            int currentHeight = entry.getValue();
            String currentLine = lineMap.get(entry.getKey().getLevel());

            if (previousHeight != -1 && currentHeight < previousHeight) {
                errors.add("Error at " + currentLine + ": Height (" + currentHeight + "m) is lower than a previous risk level at " + previousLine + ".");
            }

            previousHeight = currentHeight;
            previousLine = currentLine;
        }

        return errors.isEmpty() ? "No inconsistencies found." : String.join("\n", errors);
    }

    /**
     * Determines if a notification should be sent based on the given height in meters.
     * @param meters The height in meters.
     * @param expression The risk-level expression (multiline string).
     * @return True if the corresponding risk level is >= CONSIDERABLE (5), false otherwise.
     */
    public boolean shouldNotify(int meters, String expression) {
        RiskLevel riskLevel = getRiskLevel(meters, expression);
        return riskLevel.getLevel() >= RiskLevel.CONSIDERABLE.getLevel();
    }

    /**
     * Finds the risk level associated with a given height in meters.
     * @param meters The height in meters.
     * @param expression The risk-level expression (multiline string).
     * @return The corresponding RiskLevel enum, or ZERO if no matching height is found.
     */
    public RiskLevel getRiskLevel(int meters, String expression) {
        Pattern pattern = Pattern.compile("^(\\d+)=(\\d+)m$", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(expression);

        TreeMap<Integer, Integer> metersToRisk = new TreeMap<>();

        while (matcher.find()) {
            int riskLevel = Integer.parseInt(matcher.group(1));
            int height = Integer.parseInt(matcher.group(2));
            metersToRisk.put(height, riskLevel);
        }

        // Find the highest risk level that is still <= meters
        Map.Entry<Integer, Integer> entry = metersToRisk.floorEntry(meters);

        return (entry != null) ? RiskLevel.fromNumber(entry.getValue()) : RiskLevel.ZERO;
    }
}
