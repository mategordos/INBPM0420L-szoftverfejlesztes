package pebble.model.game;

import pebble.model.gameresults.GameResult;
import pebble.model.player.Player;
import pebble.model.player.PlayerIdentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation class of GameState.
 */
public class GameStateImplementation implements GameState {
    private static final int BOARD_SIZE = 4;
    private final Map<Position, FieldValue> positionToFieldValueMapping;
    private final Map<PlayerIdentity, Player> identityToPlayerMapping;
    private PlayerIdentity currentPlayerIdentity;
    /**
     * A list of positions that are currently selected for the next move.
     */
    public List<Position> plannedMove;

    /**
     * Initializing a GameStateImplementation class.
     * @param identityToPlayerMapping a map containing PlayerIdentity enum keys linked to Player objects
     */
    public GameStateImplementation(Map<PlayerIdentity, Player> identityToPlayerMapping) {
        this.identityToPlayerMapping = identityToPlayerMapping;
        currentPlayerIdentity = PlayerIdentity.PLAYER1;
        positionToFieldValueMapping = new HashMap<>();
        plannedMove = new ArrayList<>();
        initBoard();
    }

    private void initBoard() {
        for (int row = 0; row < BOARD_SIZE; ++row) {
            for (int column = 0; column < BOARD_SIZE; ++column) {
                var newPosition = new Position(row, column);
                positionToFieldValueMapping.put(newPosition, FieldValue.PEBBLE);
            }
        }
    }

    @Override
    public void markPositionOfNextMove(Position position) {
        if (positionToFieldValueMapping.get(position) == FieldValue.PEBBLE) {
            plannedMove.add(position);
        }
    }

    @Override
    public void unmarkPositionOfNextMove(Position position) {
        plannedMove.remove(position);
    }

    @Override
    public List<Position> getPlannedMove() {
        return plannedMove;
    }

    private List<Integer> getMoveColumnCoordinates() {
        return plannedMove.stream().map(Position::getColumn).toList();
    }

    private List<Integer> getMoveRowCoordinates() {
        return plannedMove.stream().map(Position::getRow).toList();
    }

    private boolean isInSameRow() {
        return getMoveRowCoordinates().stream().distinct().count() == 1;
    }

    private boolean isInSameColumn() {
        return getMoveColumnCoordinates().stream().distinct().count() == 1;
    }

    private boolean doesntHaveEmptyFieldBetweenRow() {
        int listMax = getMoveColumnCoordinates().stream().max(Integer::compareTo).orElseThrow(() -> new RuntimeException());
        int listMin = getMoveColumnCoordinates().stream().min(Integer::compareTo).orElseThrow(() -> new RuntimeException());
        int correctedDifference = listMax - listMin + 1;
        return isInSameRow() && correctedDifference == plannedMove.size();
    }

    private boolean doesntHaveEmptyFieldBetweenColumn() {
        int listMax = getMoveRowCoordinates().stream().max(Integer::compareTo).orElseThrow(() -> new RuntimeException());
        int listMin = getMoveRowCoordinates().stream().min(Integer::compareTo).orElseThrow(() -> new RuntimeException());
        int correctedDifference = listMax - listMin + 1;
        return isInSameColumn() && correctedDifference == plannedMove.size();
    }

    @Override
    public boolean isValidMove() {
        if (!plannedMove.isEmpty()){
            return doesntHaveEmptyFieldBetweenColumn() || doesntHaveEmptyFieldBetweenRow();
        } else {
            return false;
        }
    }

    @Override
    public boolean isBoardEmpty() {
        return !positionToFieldValueMapping.containsValue(FieldValue.PEBBLE);
    }

    @Override
    public boolean isPositionEmpty(Position position) {
        return positionToFieldValueMapping.get(position).equals(FieldValue.EMPTY);
    }

    @Override
    public void setSelectedFieldValuesToEmpty() {
        plannedMove.forEach(position -> positionToFieldValueMapping.put(position, FieldValue.EMPTY));
    }

    private void resetPlannedMove() {
        plannedMove.clear();
    }

    @Override
    public GameResult getGameResult() {
        return GameResult.builder()
                .firstPlayerName(identityToPlayerMapping.get(PlayerIdentity.PLAYER1).getName())
                .secondPlayerName(identityToPlayerMapping.get(PlayerIdentity.PLAYER2).getName())
                .identityOfWinner(currentPlayerIdentity)
                .build();
    }

    @Override
    public PlayerIdentity getCurrentPlayerIdentity() {
        return currentPlayerIdentity;
    }

    @Override
    public Player getCurrentPlayer() {
        return identityToPlayerMapping.get(currentPlayerIdentity);
    }

    @Override
    public PlayerIdentity endTurn() {
        if (plannedMove.size() > 0) {
            currentPlayerIdentity = currentPlayerIdentity.getOther();
            resetPlannedMove();
        }
        return currentPlayerIdentity;
    }

    @Override
    public List<FieldValue> getAllFieldValues() {
        return positionToFieldValueMapping.values().stream().toList();
    }

    @Override
    public int getBoardSize() {
        return BOARD_SIZE;
    }
}
