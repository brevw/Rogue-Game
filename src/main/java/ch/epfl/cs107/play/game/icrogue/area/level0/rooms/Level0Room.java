package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.area.ConnectorInRoom;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

import java.util.ArrayList;
import java.util.List;

public class Level0Room extends ICRogueRoom implements Logic {
    public static final DiscreteCoordinates DEFAULT_ITEM_POSITION = new DiscreteCoordinates(5,5);
    public static final DiscreteCoordinates SECONDARY_ITEM_POSITION = new DiscreteCoordinates(7, 7);
    // level0Room behavior name
    private static final String BEHAVIOUR_NAME = "icrogue/Level0Room";
    public DiscreteCoordinates getPlayerSpawnPosition(){
        return new DiscreteCoordinates(2,2);
    }
    public Level0Room(DiscreteCoordinates roomCoordinates){
        super(Level0Connector.getAllConnectorsPosition(), Level0Connector.getAllConnectorsOrientation(),
                BEHAVIOUR_NAME, roomCoordinates);
    }

    @Override
    public boolean isOn() {
        return isVisited();
    }

    @Override
    public boolean isOff() {
        return !isOn();
    }

    @Override
    public float getIntensity() {
        return 0;
    }

    public enum Level0Connector implements ConnectorInRoom {
        // ordre des attributs : position , destination , orientation
        W(new DiscreteCoordinates(0, 4),
                new DiscreteCoordinates(8, 5), Orientation.LEFT),
        S(new DiscreteCoordinates(4, 0),
                new DiscreteCoordinates(5, 8), Orientation.DOWN),
        E(new DiscreteCoordinates(9, 4),
                new DiscreteCoordinates(1, 5), Orientation.RIGHT),
        N(new DiscreteCoordinates(4, 9),
                new DiscreteCoordinates(5, 1), Orientation.UP);
        private final DiscreteCoordinates position;
        private final DiscreteCoordinates destination;
        private final Orientation orientation;

        /**
         * Level0Connector default constructor
         * @param position (DiscreteCoordinates): connector's position in the level0 room, not null
         * @param destination (DiscreteCoordinates) connector's destination room coordinates, not null
         * @param orientation (Orientation) connector's orientation, not null
         */
        Level0Connector(DiscreteCoordinates position, DiscreteCoordinates destination, Orientation orientation){
            this.destination = destination;
            this.orientation = orientation;
            this.position = position;
        }

        /**
         * Getter for orientation
         * @return orientation (Orientation)
         */
        public Orientation getOrientation() {
            return orientation;
        }

        /**
         *
         * @return orientations (List<Orientation>) : List of the room's
         * connectors' orientations, not null
         */
        public static List<Orientation> getAllConnectorsOrientation(){
            List<Orientation> orientations = new ArrayList<>();
            for(Level0Connector connector : Level0Connector.values()){
                orientations.add(connector.orientation);
            }
            return orientations;
        }

        /**
         * @return positions (List<DiscreteCoordinates>): list containing all
         * the room's connectors' positions, not null
         */
        public static List<DiscreteCoordinates> getAllConnectorsPosition(){
            List<DiscreteCoordinates> positions = new ArrayList<>();
            for(Level0Connector connector : Level0Connector.values()){
                positions.add(connector.position);
            }
            return positions;
        }
        @Override
        public int getIndex() {
            return ordinal();
        }

        @Override
        public DiscreteCoordinates getDestination() {
            return destination;
        }
    }

    /**
     * @return the room's title (String), not null
     */
    public String getTitle() {
        return "icrogue/level0"+getRoomCoordinates().x+getRoomCoordinates().y;
    }

    /**
     *  Register the room's background and connectors
     */
    protected void createArea() {
        registerActor(new Background(this,getBehaviorName())) ;
        registerConnectors(this);
    }
}
