package pebble.model.game;

import lombok.Value;

/**
 * Represents a position by having a row and a column int value.
 */
@Value
public class Position {
    int row;
    int column;
}
