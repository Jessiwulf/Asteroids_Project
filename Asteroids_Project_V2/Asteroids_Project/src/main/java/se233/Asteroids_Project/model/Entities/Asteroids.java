package se233.Asteroids_Project.model.Entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.Asteroids_Project.model.AllObject;

public class Asteroids extends AllObject {
    // Logger for tracking asteroid-related events
    private static final Logger logger = LogManager.getLogger(Asteroids.class);

    // Asteroid properties
    private double rotationSpeed;
    private double speedX;
    private double speedY;
    private int size;  // Size of the asteroids
    private int points; // Points awarded when asteroid is destroyed
    private boolean markedForDestruction;
    private Image asteroidImage;

    // Health properties
    private int maxHp;
    private int currentHp;

    // Paths to asteroid images
    private static final String asteroid1 = "/se233/Asteroids_Project/asset/asteroid1.png";
    private static final String asteroid2 = "/se233/Asteroids_Project/asset/asteroid2.png";
    private static final String asteroid3 = "/se233/Asteroids_Project/asset/asteroid3.png";

    // HP bar properties
    private static final double HP_BAR_WIDTH = 40;
    private static final double HP_BAR_HEIGHT = 4;
    private static final Color HP_BAR_BORDER = Color.WHITE;
    private static final Color HP_BAR_BACKGROUND = Color.TRANSPARENT;
    private static final Color HP_BAR_FILL = Color.RED;

    // Constructor for creating an asteroid at specific coordinates
    public Asteroids(double x, double y, int size) {
        super(getImagePathForSize(size), x, y, getAsteroidSize(size), getAsteroidSize(size));
        this.size = size;
        this.markedForDestruction = false; // Default state
        initializeAsteroid();
        loadAsteroidImage();
        initializeHp();
    }

    // Load the image for the asteroid based on its size
    private void loadAsteroidImage() {
        try {
            String imagePath = getImagePathForSize(size);
            this.asteroidImage = new Image(getClass().getResourceAsStream(imagePath));
            if (this.asteroidImage == null) {
                logger.error("Failed to load asteroid image for size: " + size);
            }
        } catch (Exception e) {
            logger.error("Error loading asteroid image: " + e.getMessage());
        }
    }

    // Return the image path for a given asteroid size
    private static String getImagePathForSize(int size) {
        return switch(size) {
            case 1 -> asteroid1; // Small
            case 2 -> asteroid2; // Medium
            case 3 -> asteroid3; // Large
            default -> throw new IllegalArgumentException("Invalid asteroid image: " + size);
        };
    }

    // Return the asteroid's dimensions based on its size
    private static double getAsteroidSize(int size) {
        return switch(size) {
            case 1 -> 25.0; // Small
            case 2 -> 50.0; // Medium
            case 3 -> 100.0; // Large
            default -> throw new IllegalArgumentException("Invalid asteroid size: " + size);
        };
    }

    // Initialize asteroid movement direction, speed, and rotation
    private void initializeAsteroid() {
        double angle = Math.random() * Math.PI * 2; // Random angle for direction
        double speed = 3 + Math.random() * 2;

        switch(this.size) {
            case 1: // Small
                speed *= 1.0;
                points = 100;
                break;
            case 2: // Medium
                speed *= 0.75;
                points = 250;
                break;
            case 3: // Large
                speed *= 0.5;
                points = 500;
                break;
            default:
                throw new IllegalArgumentException("Invalid asteroid size: " + size);
        }

        speedX = Math.cos(angle) * speed;
        speedY = Math.sin(angle) * speed;
        rotationSpeed = (Math.random() - 0.5) * 7; // Random rotation speed
        rotation = Math.random() * 360; // Initial random rotation
    }

    // Initialize HP based on asteroid size
    private void initializeHp() {
        switch(this.size) {
            case 1: // Small asteroid
                this.maxHp = 1;
                break;
            case 2: // Medium asteroid
                this.maxHp = 2;
                break;
            case 3: // Large asteroid
                this.maxHp = 3;
                break;
            default:
                this.maxHp = 1;
        }
        this.currentHp = this.maxHp;
    }

    // Apply damage to the asteroid and mark it for destruction if HP is zero or below
    public void takeDamage(int damage) {
        currentHp -= damage;
        if (currentHp <= 0) {
            markForDestruction();
        }
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;
        rotation += rotationSpeed;
        wrapAroundScreen(); // Handle screen wrapping
    }

    // Wrap around screen edges
    private void wrapAroundScreen() {
        if (x < -width) x = 1024;
        if (x > 1024) x = -width;
        if (y < -height) y = 768;
        if (y > 768) y = -height;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (asteroidImage != null) {
            gc.save();

            // Calculate center point for rotation
            double centerX = x + width / 2;
            double centerY = y + height / 2;

            // Apply rotation and translate to center point
            gc.translate(centerX, centerY);
            gc.rotate(rotation);
            gc.translate(-centerX, -centerY);

            // Draw asteroid image
            gc.drawImage(asteroidImage, x, y, width, height);

            gc.restore();
            renderHPBar(gc); // Render HP bar above the asteroid

        } else {
            logger.warn("Asteroid sprite is null, cannot render");
        }
    }

    // Render health bar above asteroid
    private void renderHPBar(GraphicsContext gc) {
        double hpBarX = x + (width - HP_BAR_WIDTH) / 2;
        double hpBarY = y - 10; // Position above the asteroid

        // Draw HP bar background
        gc.setFill(HP_BAR_BACKGROUND);
        gc.fillRect(hpBarX, hpBarY, HP_BAR_WIDTH, HP_BAR_HEIGHT);

        // Draw filled portion representing current HP
        double fillWidth = (HP_BAR_WIDTH * currentHp) / maxHp;
        gc.setFill(HP_BAR_FILL);
        gc.fillRect(hpBarX, hpBarY, fillWidth, HP_BAR_HEIGHT);

        // Draw HP bar border
//        gc.setStroke(HP_BAR_BORDER);
//        gc.setLineWidth(1);
//        gc.strokeRect(hpBarX, hpBarY, HP_BAR_WIDTH, HP_BAR_HEIGHT);
    }

    // Mark asteroid for destruction
    public void markForDestruction() {
        this.markedForDestruction = true;
    }

    public boolean isMarkedForDestruction() {
        return markedForDestruction;
    }

    public int getPoints() {
        return points;
    }

    public int getSize() {
        return size;
    }
}
