/*
 *	Author:      Ahmed Tlili
 *	Date:
 */
package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Enemy;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;

public class Level0EnemyRoom extends Level0Room {
    // List of the enemies in the room(Level0EnemyRoom)
    // Dead enemies are removed from this list, so it only contains alive(active) ones
    private ArrayList<Enemy> activeEnemies;

    /**
     * Default Level0EnemyRoom constructor
     * @param roomCoordinates (DiscreteCoordinates), not null
     */
    public Level0EnemyRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
    }
    protected void setActiveEnemies(ArrayList<Enemy> activeEnemies){
        this.activeEnemies = activeEnemies;
    }

    /**
     * Register the room's background, enemies and connectors
     */
    protected void createArea(){
        registerActor(new Background(this,getBehaviorName()));
        for(Enemy enemy:activeEnemies){
            registerActor(enemy);
        }
        registerConnectors(this);
    }

    /**
     * Remove enemy from the activeEnemies list if he's in it.
     * Does nothing if enemy is not in the list or if he's null.
     * Usually called when enemy is dead
     * @param enemy (Enemy): enemy, can be null
     */
    public void removeEnemy(Enemy enemy){
        for(int i=0;i<activeEnemies.size();++i){
            if(activeEnemies.get(i) == enemy){
                activeEnemies.remove(i);
                break;
            }
        }
    }

    @Override
    public boolean isOn() {
        return super.isOn() && activeEnemies.isEmpty();
    }
}
