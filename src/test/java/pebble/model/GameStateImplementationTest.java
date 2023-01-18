package pebble.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pebble.model.game.FieldValue;
import pebble.model.game.GameStateImplementation;
import pebble.model.game.Position;
import pebble.model.gameresults.GameResult;
import pebble.model.player.Player;
import pebble.model.player.PlayerIdentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateImplementationTest {

    private static GameStateImplementation gameStateImplementationTested;
    private static Map<PlayerIdentity, Player> identityToPlayerMappingTested;

    @BeforeEach
    public void init() {
        int BOARD_SIZE = 4;
        final Map<Position, FieldValue> positionToFieldValueMapping = new HashMap<>();
        PlayerIdentity currentPlayerIdentity = PlayerIdentity.PLAYER1;
        Player p1 = new Player("Teszt József 1");
        Player p2 = new Player("Teszt Béla 2");
        final Map<PlayerIdentity, Player> identityToPlayerMapping = new HashMap<>();
        identityToPlayerMapping.put(PlayerIdentity.PLAYER1, p1);
        identityToPlayerMapping.put(PlayerIdentity.PLAYER2, p2);
        List<Position> plannedMove = new ArrayList<>();
        for (int row = 0; row < BOARD_SIZE; ++row) {
            for (int column = 0; column < BOARD_SIZE; ++column) {
                var newPosition = new Position(row, column);
                positionToFieldValueMapping.put(newPosition, FieldValue.PEBBLE);
            }
        }
        identityToPlayerMappingTested = new HashMap<>(identityToPlayerMapping);
        gameStateImplementationTested = new GameStateImplementation(identityToPlayerMapping);
    }

    @Test
    void getPlannedMoveTest() {
        Position pos1 = new Position(0, 1);
        Position pos2 = new Position(0, 2);
        Position pos3 = new Position(0, 3);
        List<Position> posList = new ArrayList<>();
        posList.add(pos1);
        posList.add(pos2);
        gameStateImplementationTested.markPositionOfNextMove(pos1);
        gameStateImplementationTested.markPositionOfNextMove(pos2);
        assertEquals(posList, gameStateImplementationTested.getPlannedMove());
        gameStateImplementationTested.markPositionOfNextMove(pos3);
        assertFalse(posList == gameStateImplementationTested.getPlannedMove());
    }

    @Test
    void isValidMoveTest() {
        Position pos1 = new Position(0, 1);
        Position pos2 = new Position(0, 2);
        Position pos3 = new Position(0, 3);
        gameStateImplementationTested.markPositionOfNextMove(pos1);
        gameStateImplementationTested.markPositionOfNextMove(pos2);
        gameStateImplementationTested.markPositionOfNextMove(pos3);
        assertEquals(true, gameStateImplementationTested.isValidMove());
        gameStateImplementationTested.unmarkPositionOfNextMove(pos2);
        assertNotEquals(true, gameStateImplementationTested.isValidMove());
        gameStateImplementationTested.unmarkPositionOfNextMove(pos1);
        gameStateImplementationTested.unmarkPositionOfNextMove(pos3);
        pos1 = new Position(1, 1);
        pos2 = new Position(2, 2);
        pos3 = new Position(3, 3);
        gameStateImplementationTested.markPositionOfNextMove(pos1);
        gameStateImplementationTested.markPositionOfNextMove(pos2);
        gameStateImplementationTested.markPositionOfNextMove(pos3);
        assertEquals(false, gameStateImplementationTested.isValidMove());
        gameStateImplementationTested.unmarkPositionOfNextMove(pos1);
        gameStateImplementationTested.unmarkPositionOfNextMove(pos2);
        gameStateImplementationTested.unmarkPositionOfNextMove(pos3);
        pos1 = new Position(1, 1);
        pos2 = new Position(2, 1);
        pos3 = new Position(3, 1);
        gameStateImplementationTested.markPositionOfNextMove(pos1);
        gameStateImplementationTested.markPositionOfNextMove(pos2);
        gameStateImplementationTested.markPositionOfNextMove(pos3);
        assertEquals(true, gameStateImplementationTested.isValidMove());

    }

    @Test
    void unmarkPositionOfNextMoveTest() {
        Position pos1 = new Position(0, 1);
        gameStateImplementationTested.markPositionOfNextMove(pos1);
        gameStateImplementationTested.markPositionOfNextMove(new Position(0, 2));
        gameStateImplementationTested.markPositionOfNextMove(new Position(0, 3));
        List<Position> posList = gameStateImplementationTested.getPlannedMove();
        gameStateImplementationTested.unmarkPositionOfNextMove(pos1);
        assertArrayEquals(posList.toArray(), gameStateImplementationTested.getPlannedMove().toArray());
    }

    @Test
    void isBoardEmptyTest() {
        assertEquals(false, gameStateImplementationTested.isBoardEmpty());
    }

    @Test
    void getGameResultTest() {
        assertNotNull(gameStateImplementationTested.getGameResult());
        assertNotNull(new GameResult());
        assertNotNull(new GameResult().toString());
    }

    @Test
    void getAllFieldValuesTest() {
        List<FieldValue> fieldValues = new ArrayList<>();
        for (int i = 0; i < 16; ++i) {
            fieldValues.add(FieldValue.PEBBLE);
        }
        assertEquals(fieldValues, gameStateImplementationTested.getAllFieldValues());
    }

    @Test
    void endTurnTest() {
        Position pos1 = new Position(0, 1);
        gameStateImplementationTested.markPositionOfNextMove(pos1);
        assertEquals(PlayerIdentity.PLAYER2, gameStateImplementationTested.endTurn());
    }

    @Test
    void isPositionEmptyTest() {
        Position pos1 = new Position(0, 1);
        gameStateImplementationTested.markPositionOfNextMove(pos1);
        assertEquals(false, gameStateImplementationTested.isPositionEmpty(pos1));
        gameStateImplementationTested.setSelectedFieldValuesToEmpty();
        assertEquals(true, gameStateImplementationTested.isPositionEmpty(pos1));
    }

    @Test
    void getCurrentPlayerIdentityTest() {
        assertEquals(PlayerIdentity.PLAYER1, gameStateImplementationTested.getCurrentPlayerIdentity());
    }

    @Test
    void getCurrentPlayerTest() {
        assertEquals(identityToPlayerMappingTested.get(PlayerIdentity.PLAYER1), gameStateImplementationTested.getCurrentPlayer());
    }

    @Test
    void getBoardSizeTest() {
        assertEquals(4, gameStateImplementationTested.getBoardSize());
    }
}
