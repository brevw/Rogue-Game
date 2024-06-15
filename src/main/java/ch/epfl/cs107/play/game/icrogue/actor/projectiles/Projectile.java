package ch.epfl.cs107.play.game.icrogue.actor.projectiles;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.Collections;
import java.util.List;

public abstract class Projectile extends ICRogueActor implements Consumable, Interactor {

    //Default value of damage
    public static final int DEFAULT_DAMAGE = 1;
    //Default value of moveDuration
    public static final int DEFAULT_MOVE_DURATION = 10;
    private boolean isConsumed;
    //Damage points dealt by the projectile per hit
    private int damage;
    //Number of frames used for the calculation of the displacement
    private int moveDuration;

    /**
     * Default Projectile Constructor
     * @param area (Area), not null
     * @param orientation (Orientation): projectile's orientation, not null
     * @param coordinates (DiscreteCoordinates): projectile's coordinates, not null
     * @param damage (int): damage points the projectile inflicts by hitting
     * a player (or any actor with health points in general), not null
     * @param moveDuration (int): , not null
     */
    public Projectile(Area area, Orientation orientation, DiscreteCoordinates coordinates, int damage,int moveDuration){
        super(area,orientation,coordinates);
        this.damage = damage;
        this.moveDuration = moveDuration;
        this.isConsumed = false;
    }

    /**
     * Projectile Constructor
     * @param area (Area), not null
     * @param orientation (Orientation): projectile's orientation, not null
     * @param coordinates (DiscreteCoordinates): projectile's coordinates, not null
     * damage and moveDuration will be set to the default predefined values in
     * the Projectile class: DEFAULT_DAMAGE and DEFAULT_MOVE_DURATION
     */
    public Projectile(Area area, Orientation orientation, DiscreteCoordinates coordinates){
        this(area, orientation, coordinates, DEFAULT_DAMAGE, DEFAULT_MOVE_DURATION);
    }

    @Override
    public void update(float deltaTime) {
        if(isConsumed){
            leaveArea();

        }
        super.update(deltaTime);
    }

    /**
     * getter for the number of frames used to
     * calculate the displacement "moveDuration"
     * @return moveDuration
     */
    protected int getMoveDuration(){
        return moveDuration;
    }

    /**
     * getter for the damage points a projectile can inflict
     * @return damage
     */
    protected int getDamage(){
        return damage;
    }

    @Override
    public void consume() {
        isConsumed=true;
    }

    @Override
    public boolean isConsumed() {
        return isConsumed;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }
    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells(){
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return true;
    }


}
