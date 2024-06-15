package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0StaffRoom extends Level0ItemRoom{

    /**
     * Default Level0StaffRoom constructor
     * @param roomCoordinates (DiscreteCoordinates), not null
     */
    public Level0StaffRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        addItem(new Staff(this, Orientation.UP, DEFAULT_ITEM_POSITION));
    }
}
