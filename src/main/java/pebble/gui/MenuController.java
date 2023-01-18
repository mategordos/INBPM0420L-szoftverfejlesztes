package pebble.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;
import pebble.javafxloaderutil.LoaderUtil;
import pebble.model.game.GameState;
import pebble.model.game.GameStateImplementation;
import pebble.model.player.Player;
import pebble.model.player.PlayerIdentity;

import java.util.EnumMap;
import java.util.Map;

/**
 * Class that controls the menu part of the game.
 */
public class MenuController {
    @FXML
    TextField firstPlayer;
    @FXML
    TextField secondPlayer;

    private void loadGame(Map<PlayerIdentity, Player> playerNameMapping, Stage stage) {
        GameState gameState = new GameStateImplementation(playerNameMapping);
        GameController gameController = new GameController(gameState);
        LoaderUtil.load(stage, "fxml/game.fxml", gameController);
    }

    /**
     * An action that sets the two players' names, and binds them to their corresponding player identity if all names are correctly set.
     * Also loads the game on the button clicked.
     * @param actionEvent the button clicked in this case.
     */
    public void onStart(ActionEvent actionEvent) {
        String firstPlayerName = firstPlayer.getText();
        String secondPlayerName = secondPlayer.getText();

        if (firstPlayerName.isEmpty() || secondPlayerName.isEmpty()) {
            Logger.info("invalid player name(s)");
            return;
        }

        var playerNameMapping = new EnumMap<PlayerIdentity, Player>(PlayerIdentity.class);
        playerNameMapping.put(PlayerIdentity.PLAYER1, new Player(firstPlayerName));
        playerNameMapping.put(PlayerIdentity.PLAYER2, new Player(secondPlayerName));

        loadGame(playerNameMapping, LoaderUtil.getStageOfEvent(actionEvent));
    }
}

