package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;


public class Staff extends Item{
    private static final String SPRITE_NAME = "zelda/staff";
    private static final int MOVE_DURATION = 2;
    private Animation animation;

    /**
     * Default Staff Constructor
     * @param area (Area), not null
     * @param orientation (Orientation): orientation, not null
     * @param position (Discrete coordinates), coordinates, not null
     */
    public Staff(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        Sprite[] staff = Sprite.extractSprites(SPRITE_NAME, 8, 1f, 1f, this, 32, 32);
        animation = new Animation(MOVE_DURATION, staff, true);
    }
    @Override
    public void draw(Canvas canvas){
        animation.draw(canvas);
    }

    @Override
    public void update(float deltaTime){
        animation.update(deltaTime);
    }


    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction){
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }
    //CHECK BELOW
    @Override
    public boolean takeCellSpace() {
        return true;
    }
}
