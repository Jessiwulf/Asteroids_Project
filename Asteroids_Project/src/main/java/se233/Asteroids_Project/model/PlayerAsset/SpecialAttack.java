package se233.Asteroids_Project.model.PlayerAsset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpecialAttack {
    private static final Logger logger = LogManager.getLogger(Projectile.class);

    // Bomb ability properties
    private static final double BOMB_COOLDOWN = 15.0; // 15 seconds cooldown
    private double bombCooldownTimer = 0;
    private boolean canUseBomb = true;

    // Bomb ability methods
    public boolean canUseBomb() {
        return canUseBomb;
    }

    public void useBomb() {
        if (canUseBomb) {
            canUseBomb = false;
            bombCooldownTimer = BOMB_COOLDOWN;
            logger.info("Bomb ability used");
        }
    }

    public double getBombCooldown() {
        return Math.max(0, bombCooldownTimer);
    }
}
