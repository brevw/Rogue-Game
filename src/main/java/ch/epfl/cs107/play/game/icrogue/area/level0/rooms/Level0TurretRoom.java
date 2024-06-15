package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Enemy;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level0TurretRoom extends Level0EnemyRoom{

    private final static DiscreteCoordinates FIRST_ENEMY_DEFAULT_POSITION = new DiscreteCoordinates(1, 8);
    private final static DiscreteCoordinates SECOND_ENEMY_DEFAULT_POSITION = new DiscreteCoordinates(8, 1);
    /**
     * Default Level0TurretRoom constructor
     * @param roomCoordinates (Discrete Coordinates), not null
     */
    public Level0TurretRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        ArrayList<Enemy> enemyList = new ArrayList<Enemy>( Arrays.asList ( new Turret(this,Orientation.UP,
                        FIRST_ENEMY_DEFAULT_POSITION, new Orientation[]{Orientation.DOWN, Orientation.RIGHT}),
                new Turret(this, Orientation.UP, SECOND_ENEMY_DEFAULT_POSITION, new Orientation[]{Orientation.UP,
                        Orientation.LEFT})));
        setActiveEnemies(enemyList);
    }

}
