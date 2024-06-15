package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0KeyRoom extends Level0ItemRoom{
    /**
     * Level0KeyRoom default constructor
     * @param roomCoordinates (DiscreteCoordinates), not null
     * @param keyId (int), the identifier of the key in the level0KeyRoom
     */
    public Level0KeyRoom(DiscreteCoordinates roomCoordinates, int keyId) {
        super(roomCoordinates);
        addItem(new Key(this, Orientation.DOWN, DEFAULT_ITEM_POSITION, keyId));

    }
}
