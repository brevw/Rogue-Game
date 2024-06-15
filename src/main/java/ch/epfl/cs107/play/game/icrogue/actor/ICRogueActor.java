package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public abstract class ICRogueActor extends MovableAreaEntity {
    private Sprite sprite;

    /**
     * Setter for the actor's sprite
     * @param sprite (Sprite)
     */
    protected void setSprite(Sprite sprite){
        this.sprite = sprite;
    }

    /**
     * Default ICRogueActor Constructor
     * @param owner (Area), not null
     * @param orientation (Orientation): orientation, not null
     * @param coordinates (DiscreteCoordinates), coordinates, not null
     */
    public ICRogueActor(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates);
        resetMotion();
    }


    /**
     * Leave an area by unregister this player
     */
    public void leaveArea(){
        getOwnerArea().unregisterActor(this);
    }

    /**
     * @param area (Area): initial area, not null
     * @param position (DiscreteCoordinates): initial position, not null
     */
    public void enterArea(Area area, DiscreteCoordinates position){
        area.registerActor(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
        resetMotion();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }


    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }
}
