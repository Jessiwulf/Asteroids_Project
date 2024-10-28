package se233.Asteroids_Project.model.PlayerAsset;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.Asteroids_Project.model.GameObject;

public class Projectile extends GameObject {
    private static final Logger logger = LogManager.getLogger(Projectile.class);

    private static final double PROJECTILE_SPEED = 8.0;
    private static final double MAX_LIFETIME = 1.5; // seconds

    private double velocityX;
    private double velocityY;
    private double lifetime;
    private boolean isExpired;
    private final double screenWidth;
    private final double screenHeight;

    private static final String Idle = "/se233/Asteroids_Project/asset/player_ship.png";

    public Projectile(double x, double y, double rotation, double screenWidth, double screenHeight) {
        super(Idle, x, y, 4, 4); // Small projectile size
        this.rotation = rotation;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.lifetime = 0;
        this.isExpired = false;

        // Calculate velocity based on rotation
        double angleRad = Math.toRadians(rotation);
        this.velocityX = Math.cos(angleRad) * PROJECTILE_SPEED;
        this.velocityY = Math.sin(angleRad) * PROJECTILE_SPEED;

        logger.debug("Projectile created at ({}, {}) with rotation {}", x, y, rotation);
    }

    @Override
    public void update() {
        // Update position
        x += velocityX;
        y += velocityY;

        // Update lifetime
        lifetime += 0.016; // Assuming 60 FPS
        if (lifetime >= MAX_LIFETIME) {
            isExpired = true;
            return;
        }

        // Wrap around screen
        if (x < 0) x = screenWidth;
        if (x > screenWidth) x = 0;
        if (y < 0) y = screenHeight;
        if (y > screenHeight) y = 0;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.save();

        // Define the projectile as a small glowing blue oval
        gc.setFill(Color.GREENYELLOW);
        gc.fillOval(x - width / 2, y - height / 2, width, height);

        // Optional: Add a glowing effect by layering slightly larger, semi-transparent ovals
//        gc.setGlobalAlpha(0.5); // Set transparency
//        gc.setFill(Color.LIGHTBLUE);
//        gc.fillOval(x - width / 2 - 1, y - height / 2 - 1, width + 2, height + 2); // Larger halo

        // Optional: Add a thin trailing line to indicate direction
        gc.setGlobalAlpha(1.0); // Reset transparency for trail
        gc.setStroke(Color.CYAN);
        gc.setLineWidth(1);
        double trailLength = 10; // Length of the trail
        double angleRad = Math.toRadians(rotation + 180); // Trail direction opposite projectile

        gc.strokeLine(
                x, y,
                x + Math.cos(angleRad) * trailLength,
                y + Math.sin(angleRad) * trailLength
        );

        gc.restore();
    }


    public boolean isExpired() {
        return isExpired;
    }
}