package se233.Asteroids_Project.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Scoring {
    private static final Logger logger = LogManager.getLogger(Scoring.class);

    // Static variables for tracking the current and high scores
    private static int currentScore = 0;  // Current score in the game
    private static int highestScore = 0;     // Highest score achieved

    // Variables for handling combo-based scoring
    private static int consecutiveHits = 0;                // Count of consecutive hits within the combo window
    private static final double COMBO_MULTIPLIER = 0.5;    // Multiplier applied for consecutive hits
    private static final int COMBO_WINDOW = 2;             // Time limit for a combo sequence (in seconds)
    private static double lastHitTime = 0;                 // Timestamp of the last successful hit

    // Resets the score and combo variables to their initial states
    public static void resetScore() {
        currentScore = 0;
        consecutiveHits = 0;
        lastHitTime = 0;
        logger.info("Score reset to 0");  // Logs reset action
    }

    // Adds points to the current score with combo calculation if applicable
    public static void addPoints(int points) {
        double currentTime = System.currentTimeMillis() / 1000.0;

        // Check if this hit is within the combo time window
        if (currentTime - lastHitTime <= COMBO_WINDOW) {
            consecutiveHits++;  // Increment consecutive hits for combo
            // Apply combo multiplier to points based on consecutive hits
            double multiplier = 1 + (consecutiveHits * COMBO_MULTIPLIER - 1);
            points = (int)(points * multiplier);
            logger.debug("Combo x{} applied! Points multiplied to {}", consecutiveHits, points);  // Logs combo multiplier effect
        } else {
            consecutiveHits = 1;  // Reset combo count if window exceeded
        }

        lastHitTime = currentTime;  // Update last hit time
        currentScore += points;     // Add points to the current score

        // Update high score if the new current score exceeds it
        if (currentScore > highestScore) {
            highestScore = currentScore;
            logger.info("New high score achieved: {}", highestScore);  // Logs new high score
        }

        logger.debug("Score updated: {} (High Score: {})", currentScore, highestScore);  // Logs updated score and high score
    }

    // Getter for the current score
    public static int getCurrentScore() {
        return currentScore;
    }

    // Getter for the high score
    public static int getHighestScore() {
        return highestScore;
    }

    // Returns the current consecutive hit count (combo level)
    public static int getCombo() {
        return consecutiveHits;
    }

    // Checks if the current score is a high score and greater than 0
    public static boolean checkHighestScore() {
        return currentScore >= highestScore && currentScore > 0;
    }
}
