package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Coin extends Item {
    private static final String SPRITE_NAME = "zelda/coin";
    public static final int MOVE_DURATION = 4;
    private Animation animation;

    /**
     * Default Coin Constructor
     * @param area (Area), not null
     * @param orientation (Orientation): orientation, not null
     * @param position (Discrete coordinates), coordinates, not null
     */
    public Coin(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
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
}
