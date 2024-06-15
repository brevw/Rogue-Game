package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Coin;
import ch.epfl.cs107.play.game.icrogue.actor.items.Heart;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0HealingRoom extends Level0ItemRoom {

    /**
     * Default Level0HealingRoom constructor
     * @param roomCoordinates (DiscreteCoordinates), not null
     * @param healingPoints (int), positive integer
     */
    public Level0HealingRoom(DiscreteCoordinates roomCoordinates, int healingPoints) {
        super(roomCoordinates);
        addItem(new Heart(this, Orientation.DOWN, DEFAULT_ITEM_POSITION, healingPoints));
        addItem(new Heart(this, Orientation.DOWN, SECONDARY_ITEM_POSITION, healingPoints));
        addItem(new Coin(this, Orientation.DOWN, new DiscreteCoordinates(3,5)));
        addItem(new Coin(this, Orientation.DOWN, new DiscreteCoordinates(3,6)));
        addItem(new Coin(this, Orientation.DOWN, new DiscreteCoordinates(3,2)));
        addItem(new Coin(this, Orientation.DOWN, new DiscreteCoordinates(3,4)));
        addItem(new Coin(this, Orientation.DOWN, new DiscreteCoordinates(2,5)));
        addItem(new Coin(this, Orientation.DOWN, new DiscreteCoordinates(2,6)));
        addItem(new Coin(this, Orientation.DOWN, new DiscreteCoordinates(2,2)));
        addItem(new Coin(this, Orientation.DOWN, new DiscreteCoordinates(2,3)));
        addItem(new Coin(this, Orientation.DOWN, new DiscreteCoordinates(4,4)));
        addItem(new Coin(this, Orientation.DOWN, new DiscreteCoordinates(4,5)));
        addItem(new Coin(this, Orientation.DOWN, new DiscreteCoordinates(4,6)));
    }
}
