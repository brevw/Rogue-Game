package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0EnemyRoom;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Fire extends Projectile{

    ////Fire sprite name used to create the associated sprite
    private static final String SPRITE_NAME = "zelda/fire";
    //Default damage value for Fire instance
    private static final int FIRE_DAMAGE = 1;
    //default moveDuration value for Fire instance
    private static final int MOVE_DURATION = 5;
    private Animation animation;
    //interaction handler for Fire
    private final FireInteractionHandler handler;

    /**
     * Default Fire Constructor
     * @param area (Area). Not null
     * @param orientation (Orientation). Not Null
     * @param coordinates (DiscreteCoordinates). Not null
     */
    public Fire(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates, FIRE_DAMAGE, MOVE_DURATION);
        Sprite[] fire = Sprite.extractSprites(SPRITE_NAME, 7, 1f, 1f, this, 16, 16);
        animation = new Animation(MOVE_DURATION, fire, true);
        //creating the Fire Interaction Handler
        handler = new FireInteractionHandler();
    }

    /**
     * interaction handler class for Fire. Implements specific interactions of
     * a Fire object with another (cell or turret in this case).
     */
    private class FireInteractionHandler implements ICRogueInteractionHandler{

        @Override
        public void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction) {
            if(!isCellInteraction){
                if(cell.getType() == ICRogueBehavior.ICRogueCellType.HOLE
                        || cell.getType() == ICRogueBehavior.ICRogueCellType.WALL){
                    consume();
                }
            }
        }

        @Override
        public void interactWith(Turret turret, boolean isCellInteraction) {
            if(isCellInteraction){
                turret.dies();
                ((Level0EnemyRoom)getOwnerArea()).removeEnemy(turret);
            }
        }
    }
    @Override
    public void draw(Canvas canvas){
        animation.draw(canvas);
    }

    @Override
    public void update(float deltaTime) {
        move(MOVE_DURATION);
        animation.update(deltaTime);
        super.update(deltaTime);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction){
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }
    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }
}
