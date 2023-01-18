package pebble.javafxloaderutil;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

/**
 * A class containing JavaFX loader utility methods.
 */
public class LoaderUtil {
    /**
     * A method that takes a stage and a path as parameters, then sets the scene of the stage based on the read FXML file.
     * @param stage that will be modified based on the loaded FXML file
     * @param path the path to the FXML file.
     */
    public static void load(Stage stage, String path) {
        try {
            Parent root = FXMLLoader.load(LoaderUtil.class.getClassLoader().getResource(path));
            var scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            Logger.error("Couldn't load FXML. " + e);
        }
    }

    /**
     * A method that takes a stage and a path as parameters, then sets the scene of the stage based on the read FXML file,
     * and sets a controller to the FXMLLoader object created.
     * @param stage that will be modified based on the loaded FXML file
     * @param path the path to the FXML file.
     * @param controller the controller of the FXMLLoader object.
     */
    public static void load(Stage stage, String path, Initializable controller) {
        try {
            FXMLLoader loader = new FXMLLoader(LoaderUtil.class.getClassLoader().getResource(path));
            Logger.debug("FXML successfully loaded");
            loader.setController(controller);
            Logger.debug("Controller successfully set");
            Parent root = loader.load();
            Logger.debug("Parent successfully loaded");
            Scene scene = new Scene(root);
            Logger.debug("Scene successfully set");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            Logger.error("Something went from with loading FXML. " + e);
        }
    }

    /**
     * Returns the window from which the event originates.
     * @param event input event from the user
     * @return Stage the source window of the event
     */
    public static Stage getStageOfEvent(Event event) {
        Logger.info((Stage) ((Node) event.getSource()).getScene().getWindow());
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
}
