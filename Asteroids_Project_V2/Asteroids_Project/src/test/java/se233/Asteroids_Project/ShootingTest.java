package se233.Asteroids_Project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.Asteroids_Project.model.Entities.Player;
import se233.Asteroids_Project.model.Asset.PlayerProjectile;
import static org.junit.jupiter.api.Assertions.*;

public class ShootingTest {
    private Player player;
    private static final double STAGE_WIDTH = 800;
    private static final double STAGE_HEIGHT = 600;
    private static final double DELTA = 0.001;

    @BeforeEach
    void setUp() {
        // Create a player at the center of the stage
        player = new Player(STAGE_WIDTH/2, STAGE_HEIGHT/2, STAGE_WIDTH, STAGE_HEIGHT);
    }
}