package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Sword extends Item{
    //Key sprite name used to create the associated sprite
    private static final String SPRITE_NAME = "zelda/sword.icon";

    /**
     * Default Sword Constructor
     * @param area (Area). Not null
     * @param orientation (Orientation): orientation, not null
     * @param position (Discrete coordinates), coordinates, not null
     */
    public Sword(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        Sprite sprite = new Sprite(SPRITE_NAME, .5f, .5f, this);
        setSprite(sprite);
    }
    @Override
    public boolean isViewInteractable() {
        return false;
    }
    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }
}
