module se233.Asteroids_Project {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;

    opens se233.Asteroids_Project to javafx.fxml;
    exports se233.Asteroids_Project;
}