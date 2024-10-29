package se233.Asteroids_Project.model;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class AllObject {
    // Position and size of the object
    protected double x;            // X position
    protected double y;            // Y position
    protected double width;        // Object width
    protected double height;       // Object height
    protected double velocity;     // Movement speed
    protected double rotation;     // Rotation angle in degrees

    // Sprite sheet animation properties
    protected Image spriteSheet;
    protected int frameWidth;      // Width of each frame in the sprite sheet
    protected int frameHeight;     // Height of each frame in the sprite sheet
    protected int currentFrame;    // Current frame number in the animation sequence
    protected int totalFrames;     // Total number of frames in the animation
    protected double frameTimer;   // Timer to track the frame interval
    protected double frameInterval;// Time interval between frames in seconds

    // Constructor that initializes position, size, and optionally loads the sprite sheet
    public AllObject(String imagePath, double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = 0;         // Default velocity is set to 0
        this.rotation = 0;         // Default rotation angle is set to 0

        if (imagePath != null) {
            // Load sprite sheet image from file path
            this.spriteSheet = new Image(getClass().getResourceAsStream(imagePath));
        }
    }

    // Initializes the animation by setting frame dimensions and timing properties
    protected void initializeAnimation(int frameWidth, int frameHeight, int totalFrames, double frameInterval) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.totalFrames = totalFrames;
        this.frameInterval = frameInterval;
        this.currentFrame = 0;     // Start at the first frame
        this.frameTimer = 0;       // Reset the frame timer
    }

    // Updates the animation by progressing to the next frame based on elapsed time
    protected void updateAnimation(double deltaTime) {
        if (spriteSheet != null) {
            // Accumulate elapsed time in frameTimer
            frameTimer += deltaTime;
            if (frameTimer >= frameInterval) {
                // Move to the next frame and reset timer if interval is reached
                currentFrame = (currentFrame + 1) % totalFrames;
                frameTimer = 0;
            }
        }
    }

    // Abstract methods to be implemented by subclasses for updating object state and rendering
    public abstract void update();           // Update object properties per frame
    public abstract void render(GraphicsContext gc); // Draw the object to the canvas

    // Calculates and returns the bounding box for collision detection
    public Bounds getBounds() {
        // Adjusts x and y for the object's center position
        return new javafx.geometry.BoundingBox(x - width / 2, y - height / 2, width, height);
    }

    // Getters and setters for position, size, velocity, and rotation
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getVelocity() { return velocity; }
    public void setVelocity(double velocity) { this.velocity = velocity; }
    public double getRotation() { return rotation; }
    public void setRotation(double rotation) { this.rotation = rotation; }
}
