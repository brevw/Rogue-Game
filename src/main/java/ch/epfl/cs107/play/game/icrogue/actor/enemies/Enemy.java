package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public abstract class Enemy extends ICRogueActor {
    //boolean
    private boolean alive;

    /**
     * Default constructor for Enemy
     * @param owner (owner), not Null
     * @param orientation (Orientation), not Null
     * @param coordinates (DiscreteCoordinates)
     */
    public Enemy(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates);
        this.alive = true;
    }

    /**
     * Getter for alive
     * @return alive (boolean).
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Kills the enemy by setting his alive attribute to false
     */
    public void dies() {
        this.alive = false;
    }
}
