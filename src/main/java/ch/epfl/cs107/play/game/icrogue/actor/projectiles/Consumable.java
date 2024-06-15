package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

public interface Consumable {

    /**
     * consumes the consumable entity by setting its
     * boolean attribute "isConsumed" to true
     */
    void consume();

    /**
     * @return (boolean): true if the consumable entity
     * is already consumed and false otherwise
     */
    boolean isConsumed();
}
