package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;

public class Arrow extends Projectile {
    //Arrow sprite name used to create the associated sprite
    private static final String SPRITE_NAME = "zelda/arrow";
    //interaction handler of Arrow
    private final ArrowInteractionHandler handler;

    /**
     * Default Arrow Constructor
     * @param area (Area)
     * @param orientation (Orientation)
     * @param coordinates (DiscreteCoordinates)
     */
    public Arrow(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates);
        setSprite(new Sprite (SPRITE_NAME, 1f, 1f, this ,
                new RegionOfInterest (32* orientation.ordinal () , 0, 32 , 32) ,
                new Vector (0 , 0)));
        handler = new ArrowInteractionHandler();
    }

    /**
     * interaction handler class for Arrow
     */
    private class ArrowInteractionHandler implements ICRogueInteractionHandler{

        /**
         *
         * @param player (ICRoguePlayer). Not null
         * @param isCellInteraction (isCellInteraction). Not null
         */
        public void interactWith(ICRoguePlayer player, boolean isCellInteraction) {
            if(isCellInteraction && !isConsumed()){
                player.takeDamage(getDamage());
                consume();
                player.updateHeartsSprites();
            }
        }

        /**
         * checks if the arrows has hit a wall or a hole. If so, the arrow gets consumed
         * @param cell (ICRogueBehavior.ICRogueCell)
         * @param isCellInteraction (boolean)
         */
        public void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction) {
            if(!isCellInteraction){
                if(cell.getType() == ICRogueBehavior.ICRogueCellType.HOLE
                        || cell.getType() == ICRogueBehavior.ICRogueCellType.WALL){
                    consume();
                }
            }
        }
    }


    @Override
    public void update(float deltaTime) {
        move(getMoveDuration());
        super.update(deltaTime);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }
}
