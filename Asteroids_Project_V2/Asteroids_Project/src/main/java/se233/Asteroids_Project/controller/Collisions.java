package se233.Asteroids_Project.controller;

import javafx.geometry.Bounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.Asteroids_Project.model.*;
import se233.Asteroids_Project.model.Asset.PlayerProjectile;
import se233.Asteroids_Project.model.Entities.Asteroids;
import se233.Asteroids_Project.model.Entities.Boss;
import se233.Asteroids_Project.model.Entities.Minion;
import se233.Asteroids_Project.model.Entities.Player;

import java.util.List;

public class Collisions {
    private static final Logger logger = LogManager.getLogger(Collisions.class);
    private static final double COLLISION_MARGIN = 1;

    public static class CollisionHandlingException extends RuntimeException {
        public CollisionHandlingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static boolean checkCollision(AllObject obj1, AllObject obj2) throws CollisionHandlingException {
        try {
            // Get the bounds of both objects
            Bounds bounds1 = obj1.getBounds();
            Bounds bounds2 = obj2.getBounds();

            // Apply collision margin
            double width1 = bounds1.getWidth() * COLLISION_MARGIN;
            double height1 = bounds1.getHeight() * COLLISION_MARGIN;
            double width2 = bounds2.getWidth() * COLLISION_MARGIN;
            double height2 = bounds2.getHeight() * COLLISION_MARGIN;

            // Calculate centers
            double centerX1 = bounds1.getMinX() + bounds1.getWidth();
            double centerY1 = bounds1.getMinY() + bounds1.getHeight();
            double centerX2 = bounds2.getMinX() + bounds2.getWidth();
            double centerY2 = bounds2.getMinY() + bounds2.getHeight();

            // Check for collision using center points and adjusted dimensions
            return Math.abs(centerX1 - centerX2) < (width1 + width2) / 2 &&
                    Math.abs(centerY1 - centerY2) < (height1 + height2) / 2;
        } catch (Exception e) {
            throw new CollisionHandlingException(
                    String.format("Error checking collision between %s and %s",
                            obj1.getClass().getSimpleName(),
                            obj2.getClass().getSimpleName()),
                    e
            );
        }
    }

    public static void handleCollisions(Player player, List<Asteroids> asteroids, List<Minion> enemies, List<Boss> boss, List<PlayerProjectile> playerProjectiles) {
        try {
            if (!player.isInvulnerable()) {
                // Check player collision with asteroids
                for (Asteroids asteroid : asteroids) {
                    if (checkCollision(player, asteroid)) {
                        player.hit();
                        logger.info("Player hit by asteroid. Lives remaining: {}", player.getLives());
                        break;
                    }
                }

                // Check player collision with enemies
                for (Minion minion : enemies) {
                    if (checkCollision(player, minion)) {
                        player.hit();
                        logger.info("Player hit by enemy. Lives remaining: {}", player.getLives());
                        break;
                    }
                }

                // Check player collision with bosses
                for (Boss boss1 : boss) {
                    if (checkCollision(player, boss1)) {
                        player.hit();
                        logger.info("Player hit by boss. Lives remaining: {}", player.getLives());
                        break;
                    }
                }
            }
        } catch (CollisionHandlingException e) {
            logger.error("Collision handling error: {}", e.getMessage(), e);
            // Here you can add additional error handling logic if needed
        }
    }
}