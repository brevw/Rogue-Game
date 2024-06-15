package ch.epfl.cs107.play.game.icrogue.handler;

import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.items.*;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;

public interface ICRogueInteractionHandler extends AreaInteractionVisitor {
    /**
     * Note: Need to be Override
     * @param cell (ICRogueBehavior.ICRogueCell)
     * @param isCellInteraction (boolean)
     */
    default void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction){}

    /**
     *
     * Note: Need to be Override
     * @param player (ICRoguePlayer)
     * @param isCellInteraction (boolean)
     */
    default void interactWith(ICRoguePlayer player, boolean isCellInteraction){}

    /**
     *
     * Note: Need to be Override
     * @param cherry (Cherry)
     * @param isCellInteraction (booleam)
     */
    default void interactWith(Cherry cherry, boolean isCellInteraction){}

    /**
     *
     * Note: Need to be Override
     * @param staff (Staff)
     * @param isCellInteraction (boolean)
     */
    default void interactWith(Staff staff, boolean isCellInteraction){}

    /**
     *
     * Note: Need to be Override
     * @param fire (Staff)
     * @param isCellInteraction (booleam)
     */
    default void interactWith(Fire fire, boolean isCellInteraction){}

    /**
     *
     * Note: Need to be Override
     * @param key (Key)
     * @param isCellInteraction (boolean)
     */
    default void interactWith(Key key, boolean isCellInteraction){}

    /**
     *
     * Note: Need to be Override
     * @param connector (Connector)
     * @param isCellInteraction (boolean)
     */
    default void interactWith(Connector connector, boolean isCellInteraction){}

    /**
     *
     * Note: Need to be Override
     * @param heart (Heart)
     * @param isCellInteraction (boolean)
     */
    default void interactWith(Heart heart, boolean isCellInteraction){}

    /**
     *
     * @param arrow (Arrow)
     * @param isCellInteraction (boolean)
     */
    default void interactWith(Arrow arrow, boolean isCellInteraction){}

    /**
     *
     * Note: Need to be Override
     * @param coin (Coin)
     * @param isCellinteraction (boolean)
     */
    default void interactWith(Coin coin, boolean isCellinteraction){}

    /**
     * implements the specific interaction of the interactor with the turret,
     * if we have a cell interaction, the turret dies and is removed from its area
     * @param turret (Turret), not null
     * @param isCellInteraction (boolean): true if there must be a
     * cell interaction between the interactor and Arrow Not Null, this happens
     * when they both occupate shared(or the same) cells
     */
    default void interactWith(Turret turret, boolean isCellInteraction){}


    /**
     *
     * Note: Need to be Override
     * @param sword (Sword)
     * @param isCellInteraction (boolean)
     */
    default void interactWith(Sword sword, boolean isCellInteraction){}
}
