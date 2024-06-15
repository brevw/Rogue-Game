package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;

public class Turret extends Enemy{
    private static final String SPRITE_NAME = "icrogue/static_npc";
    private static final float COOLDOWN = 2.f;
    private float counter;
    private final TurretInteractionHandler handler;
    private Orientation[] orientations;
    /**
     * Default Turret Constructor
     * @param owner (Area), not null
     * @param orientation (Orientation): orientation, not null
     * @param coordinates (Discrete coordinates), coordinates, not null
     * @param orientations (Orientation[]), not null
     */
    public Turret(Area owner, Orientation orientation, DiscreteCoordinates coordinates, Orientation[] orientations){
        super(owner, orientation, coordinates);
        this.orientations = orientations;
        this.counter =  0.f;
        handler = new TurretInteractionHandler();
        setSprite(new Sprite(SPRITE_NAME, 1f, 1f, this ,
                new RegionOfInterest(0 , 0, 16 , 24) , new
                Vector(0 , 0)));
    }

    private class TurretInteractionHandler implements ICRogueInteractionHandler {
        public void interactWith(Fire fire, boolean isCellInteraction) {
            if(isCellInteraction){
                fire.consume();
                dies();
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        if(!isAlive()){
            leaveArea();
        }
        counter += deltaTime;
        if(counter>=COOLDOWN){
            counter =  0.f;
            for(Orientation orientation:orientations){
                getOwnerArea().registerActor(new Arrow(getOwnerArea(), orientation, new DiscreteCoordinates((int) getPosition().x, (int) getPosition().y)));
            }
        }
        super.update(deltaTime);
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
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }


}
