package pebble;

import javafx.application.Application;
import javafx.stage.Stage;
import pebble.javafxloaderutil.LoaderUtil;

/**
 * A helper class so that the mvn shade plugin functions correctly.
 */
public class PebbleApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JavaFX Pebble Game");
        LoaderUtil.load(stage, "fxml/menu.fxml");
    }
}
