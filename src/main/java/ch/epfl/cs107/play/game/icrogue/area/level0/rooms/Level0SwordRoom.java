package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Sword;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0SwordRoom extends Level0ItemRoom{

    /**
     * Default Level0SwordRoom constructor
     * @param roomCoordinates (DiscreteCoordinates), not null
     */
    public Level0SwordRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        addItem(new Sword(this, Orientation.UP, DEFAULT_ITEM_POSITION));
    }
}
