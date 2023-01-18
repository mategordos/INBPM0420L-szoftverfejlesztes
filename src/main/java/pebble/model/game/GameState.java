package pebble.model.game;

import pebble.model.gameresults.GameResult;
import pebble.model.player.Player;
import pebble.model.player.PlayerIdentity;

import java.util.List;

/**
 * An interface that contains the most important methods that are invoked somewhere in the GameController as well.
 * The methods are implemented in the {@code GameStateImplementation} class.
 */

public interface GameState {

    /**
     * Marks a position as part of the next move, without changing the state of the field selected.
     *
     * @param position the Position that will be marked as part of the next move
     */
    void markPositionOfNextMove(Position position);

    /**
     * Removes a marked position from the next move.
     *
     * @param position the Position that will be unselected from the next move.
     */

    void unmarkPositionOfNextMove(Position position);

    /**
     * Checks if the value corresponding to the position is EMPTY or not.
     *
     * @param position the Position value of the checked field
     * @return whether the checked field is empty or not
     */

    boolean isPositionEmpty(Position position);

    /**
     * Sets all selected fields' values to EMPTY.
     */

    void setSelectedFieldValuesToEmpty();

    /**
     * Returns a list of positions of moves planned.
     *
     * @return the list of planned positions.
     */

    List<Position> getPlannedMove();

    /**
     * Checks if selected pebbles are removable considering the rules (no empty fields between pebbles, all are in the same row/column).
     *
     * @return whether the move is valid or invalid.
     */

    boolean isValidMove();

    /**
     * Checks if the board is empty.
     *
     * @return whether the board is empty or not
     */

    boolean isBoardEmpty();

    /**
     * Returns the identity of the current player.
     *
     * @return the identity of the current player, either PLAYER1, or PLAYER2
     */

    PlayerIdentity getCurrentPlayerIdentity();

    /**
     * Returns the current Player.
     *
     * @return the current Player
     */

    Player getCurrentPlayer();

    /**
     * Ends a turn. After ending a turn, the PlayerIdentity is set to the opposite (PLAYER1 -> PLAYER2 / PLAYER2 -> PLAYER1).
     *
     * @return the opposite PlayerIdentity
     */

    PlayerIdentity endTurn();

    /**
     * Returns the board size.
     *
     * @return the size of the board
     */

    int getBoardSize();

    /**
     * Returns the list of all field values.
     *
     * @return a {@code List<FieldValue>}, that contains every existing field value.
     */

    List<FieldValue> getAllFieldValues();

    /**
     * Returns a POJO containing the two players, and the winner player when the game is finished.
     *
     * @return a GameResult instance containing two players and the winning player
     */

    GameResult getGameResult();


}
