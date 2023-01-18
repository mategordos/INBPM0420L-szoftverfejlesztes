package pebble.model.player;

/**
 * Represents a PlayerIdentity enum which can have the value of PLAYER1 and PLAYER2.
 */
public enum PlayerIdentity {
    /**
     * PlayerIdentity with the value of PLAYER1.
     */
    PLAYER1,
    /**
     * PlayerIdentity with the value of PLAYER2.
     */
    PLAYER2;

    /**
     * Returns the other PlayerIdentity value based on the current one.
     *
     * @return the other PlayerIdentity value
     */
    public PlayerIdentity getOther() {
        return switch (this) {
            case PLAYER1 -> PLAYER2;
            case PLAYER2 -> PLAYER1;
        };
    }
}
