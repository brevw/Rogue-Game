package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Cherry extends Item{
    private static final String SPRITE_NAME = "icrogue/cherry";

    /**
     * Default Cherry Constructor
     * @param area (Area), not null
     * @param orientation (Orientation): cherry's orientation, not null
     * @param position (Discrete coordinates), coordinates, not null
     */
    public Cherry(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        Sprite sprite  =new Sprite(SPRITE_NAME, 0.6f, 0.6f, this );
        setSprite(sprite);
    }
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction){
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }
}
