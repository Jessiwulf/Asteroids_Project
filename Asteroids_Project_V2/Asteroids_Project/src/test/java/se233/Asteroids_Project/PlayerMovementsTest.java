package se233.Asteroids_Project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.Asteroids_Project.model.Entities.Player;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerMovementsTest {
    private Player player;
    private final double SCREEN_WIDTH = 800;
    private final double SCREEN_HEIGHT = 600;
    private final double DELTA = 0.01;
    private final double INITIAL_ROTATION = -90;

    @BeforeEach
    void setUp() {
        // Initialize player at center of screen
        player = new Player(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

}