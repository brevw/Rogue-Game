package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Key extends Item{
    //Key sprite name used to created the associated sprite
    private static final String SPRITE_NAME = "icrogue/key";
    private int identifier;

    /**
     * Default Key Constructor
     * @param area (Area), not null
     * @param orientation (Orientation): orientation, not null
     * @param position (Discrete coordinates), coordinates, not null
     * @param identifier (int): key identifier, default value = NO_KEY_ID  ??
     */
    public Key(Area area, Orientation orientation, DiscreteCoordinates position, int identifier) {
        super(area, orientation, position);
        Sprite sprite  =new Sprite(SPRITE_NAME, 0.6f, 0.6f, this );
        setSprite(sprite);
        this.identifier = identifier;
    }

    /**
     * getter for key identifier
     * @return identifier (int), Not Null
     */
    public int getIdentifier() {
        return identifier;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }
}
