package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Heart extends Item {
    private static final String SPRITE_NAME = "zelda/heart";
    public static final int MOVE_DURATION = 4;
    private int healingPoints;
    private Animation animation;

    /**
     * Default Heart Constructor
     * @param area (Area), not null
     * @param orientation (Orientation): orientation, not null
     * @param position (Discrete coordinates), coordinates, not null
     * @param healthPoints (int): number of health points the heart contains
     */
    public Heart(Area area, Orientation orientation, DiscreteCoordinates position, int healthPoints) {
        super(area, orientation, position);
        this.healingPoints = healthPoints;
        Sprite[] heart = Sprite.extractSprites(SPRITE_NAME, 4, 1f, 1f, this, 16, 16);
        animation = new Animation(MOVE_DURATION, heart, true);
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
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    /**
     * getter for the heart's healing points value
     * @return healingPoints
     */
    public int getHealingPoints() {
        return healingPoints;
    }
}
