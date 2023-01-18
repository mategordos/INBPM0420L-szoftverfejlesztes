package pebble.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.tinylog.Logger;
import pebble.javafxloaderutil.LoaderUtil;
import pebble.model.game.GameState;
import pebble.model.game.Position;
import pebble.model.gameresults.GameResult;
import pebble.model.player.Player;
import pebble.services.GameResultService;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class that is the controller the corresponding FXML game component.
 */
public class GameController implements Initializable {
    private final GameState gameState;
    @FXML
    Label playerName;
    @FXML
    GridPane board;
    @FXML
    Button endTurn;

    /** Instantiates and initializes a gameState.
     * @param gameState the initialized gameState.
     */
    public GameController(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerName.setText(gameState.getCurrentPlayer().getName() + "'s turn");
        endTurn.setOnAction(this::onEndTurn);
        setBoardFill();
        Logger.info("Board initialized");
    }

    private void setBoardFill() {
        int boardSize = gameState.getBoardSize();
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                Rectangle rectangle = new Rectangle(150, 150);
                rectangle.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("images/pebble.png").toString())));
                rectangle.setOnMouseClicked(this::onFieldClickHandler);

                GridPane.setMargin(rectangle, new Insets(1));
                board.add(rectangle, i, j);
            }
        }
    }

    private Rectangle getRectangleByPosition(Position position) {
        for (var node : board.getChildren()) {
            if (node instanceof Rectangle rectangle) {
                if (GridPane.getColumnIndex(rectangle) == position.getColumn()
                        && GridPane.getRowIndex(rectangle) == position.getRow()) {
                    return rectangle;
                }
            }
        }
        Logger.error("Couldn't get rectangle by position!", new IllegalArgumentException());
        throw new IllegalArgumentException();
    }

    private void onFieldClickHandler(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY) {
            return;
        }
        var eventSource = (Rectangle) event.getSource();
        int sourceColumn = GridPane.getColumnIndex(eventSource);
        int sourceRow = GridPane.getRowIndex(eventSource);

        Position position = new Position(sourceRow, sourceColumn);

        if (gameState.isPositionEmpty(position)) {
            return;
        }
        if (!gameState.getPlannedMove().contains(position)) {
            gameState.markPositionOfNextMove(position);
            eventSource.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("images/pebble_selected.png").toString())));
        } else {
            gameState.unmarkPositionOfNextMove(position);
            eventSource.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("images/pebble.png").toString())));
        }
        Logger.debug("Planned move: " + gameState.getPlannedMove());
    }

    private void endGame(Player winner, Stage stage) {
        GameResult gameResult = gameState.getGameResult();
        GameResultService.getInstance().save(gameResult);
        LeaderboardController leaderboardcontroller = new LeaderboardController(winner);
        LoaderUtil.load(stage, "fxml/leaderboard.fxml", leaderboardcontroller);
        Logger.info("Game has finished successfully");
    }


    private void setFieldsTransparent() {
        List<Position> plannedMoves = gameState.getPlannedMove();
        plannedMoves.forEach(position -> getRectangleByPosition(position).setFill(Color.TRANSPARENT));
    }

    private void onGameEndCheck(ActionEvent actionEvent) {
        if (gameState.isBoardEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(gameState.getCurrentPlayer().getName() + " has won the game!");
            var result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                endGame(gameState.getCurrentPlayer(), LoaderUtil.getStageOfEvent(actionEvent));
            }
        }
    }

    /** When the End turn button is clicked, we check if the currently selected move is valid, if it is, the visuals of the fields are
     * changed, their values are changed to EMPTY, and we also check for a possible case that the game has ended. If the game hasn't ended,
     * it's the other player's turn.
     * @param actionEvent the button clicked
     */
    public void onEndTurn(ActionEvent actionEvent) {
        if (!gameState.isValidMove()) {
            Logger.debug("Move is not valid!");
            return;
        }
        setFieldsTransparent();
        gameState.setSelectedFieldValuesToEmpty();
        gameState.endTurn();
        Logger.info("All fields: " + gameState.getAllFieldValues().toString());
        Logger.info("The board is empty: " + gameState.isBoardEmpty());
        onGameEndCheck(actionEvent);
        playerName.setText(gameState.getCurrentPlayer().getName() + "'s turn");
    }
}
