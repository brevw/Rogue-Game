package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

public abstract class Level0ItemRoom extends Level0Room{
    //List containing all the items in the room
    private List<Item> items;

    /**
     * Default Level0ItemRoom constructor
     * @param roomCoordinates (DiscreteCoordinates), not null
     */
    public Level0ItemRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        items = new ArrayList<>();
    }

    /**
     * Register the room's background and items
     */
    protected void createArea() {
        registerActor(new Background(this,getBehaviorName()));
        for(Item item:items){
            registerActor(item);
        }
        registerConnectors(this);
    }

    /**
     * add an item to the room's items list
     * @param item (Item): item to be added to the room, not null
     */
    protected void addItem(Item item){
        items.add(item);
    }

    /**
     * @return true if all items in the room have been collected
     * and false otherwise
     */
    protected boolean areAllCollected(){
        for(Item item : items){
            if(!item.isCollected()){
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean isOn(){
        return super.isOn() && areAllCollected();
    }
}
