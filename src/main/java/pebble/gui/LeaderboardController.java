package pebble.gui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.tinylog.Logger;
import pebble.javafxloaderutil.LoaderUtil;
import pebble.model.gameresults.GameResult;
import pebble.model.player.Player;
import pebble.model.player.PlayerIdentity;
import pebble.services.GameResultService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * A controller class that is used for the leaderboard screen of the game.
 */
public class LeaderboardController implements Initializable {
    @FXML
    private Label winnerName;
    @FXML
    private Button back;

    /**
     * A TableColumn FXML container with the value of firstPlayerName.
     */
    @FXML
    public TableColumn<GameResult, String> firstPlayerName;
    /**
     * A TableColumn FXML container with the value of secondPlayerName.
     */
    @FXML
    public TableColumn<GameResult, String> secondPlayerName;
    /**
     * A TableColumn FXML container with the value of identityOfWinner.
     */
    @FXML
    public TableColumn<GameResult, PlayerIdentity> identityOfWinner;
    @FXML
    TableView<GameResult> leaderBoard;

    private final Player winner;
    private final List<GameResult> gameResults;


    /**
     * Sets the winner of the game, and returns all gameResults that have been played before.
     * @param winner the player that won the game.
     */
    public LeaderboardController(Player winner) {
        this.winner = winner;
        this.gameResults = GameResultService.getInstance().findAll();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        winnerName.setText(String.format("Congratulaions, ", winner.getName()) + " you won!");
        winnerName.setText("Current leaderboard");

        back.setOnAction(this::onBack);
        firstPlayerName.setCellValueFactory(new PropertyValueFactory<>("firstPlayerName"));
        secondPlayerName.setCellValueFactory(new PropertyValueFactory<>("secondPlayerName"));
        identityOfWinner.setCellValueFactory(new PropertyValueFactory<>("identityOfWinner"));
        leaderBoard.setItems(FXCollections.observableList(gameResults));

    }

    private void onBack(ActionEvent actionEvent) {
        LoaderUtil.load(LoaderUtil.getStageOfEvent(actionEvent), "fxml/menu.fxml");
        Logger.info("Returned to menu screen");
    }
}

