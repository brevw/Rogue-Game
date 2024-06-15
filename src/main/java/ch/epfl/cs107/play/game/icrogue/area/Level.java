package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.icrogue.ICRogue;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.*;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

import java.util.ArrayList;
import java.util.List;

public abstract class Level implements Logic {
    // Default coordinates for the level's boss room
    private static final DiscreteCoordinates BOSS_ROOM_COORDS = new DiscreteCoordinates(0,0);
    // Coordinates dor the level's boss room
    private DiscreteCoordinates bossRoomCoords;
    // The level map containing the level's rooms
    protected ICRogueRoom[][] map;
    // starting room's discrete coordinates
    protected DiscreteCoordinates startPosition;
    // key identifier of the connector locking the boss room
    protected static final int BOSS_ROOM_KEY_ID = 199;
    // Default healing points value for a heart
    private static final int HEALING_POINTS = 1;
    private String initialRoomTitle;
    // The map's width
    protected final int width;
    // The map's height
    protected final int height;
    protected final int[] roomsDistribution;

    /**
     * Default Level Constructor
     * @param randomMap (boolean) : True if you want to generate a random map
     * and false if you want to generate a fixed map, not null
     * @param startPosition (DiscreteCoordinates)
     * @param roomsDistribution (int[]), the room distribution according to which the map is built
     * @param width (int), not null
     * @param height (int), not null
     * @param bossRoomCoords (DiscreteCoordinates), null if you don't want your level to have a boss room
     */
    public Level(boolean randomMap, DiscreteCoordinates startPosition,
                 int[] roomsDistribution, int width, int height, DiscreteCoordinates bossRoomCoords){
        if(randomMap){
            int nbRooms=0;
            for(int i:roomsDistribution){
                nbRooms+=i;
            }
            this.width = nbRooms;
            this.height = nbRooms;
        }else{
            this.width = width;
            this.height = height;
        }
        map = new ICRogueRoom[this.width][this.height];
        this.startPosition = startPosition;
        this.roomsDistribution = roomsDistribution;
        this.bossRoomCoords = bossRoomCoords;

    }

    /**
     * Level Constructor
     * The boss room coordinates take their default value
     * @param randomMap (boolean) : True if you want to generate a random map
     * and false if you want to generate a fixed map, not null
     * @param startPosition (DiscreteCoordinates)
     * @param roomsDistribution (int[]), the room distribution according to which the map is built
     * @param width (int), not null
     * @param height (int), not null
     */
    public Level(boolean randomMap, DiscreteCoordinates startPosition,
                 int[] roomsDistribution, int width, int height) {
        this(randomMap, startPosition, roomsDistribution, width, height, BOSS_ROOM_COORDS);
    }

    /**
     * Add the room to the level's map in the according
     * @param coords (DiscreteCoordinates), the room's coordinates in the map,
     * must be valid coordinates in map, not null
     * @param room (ICRogueRoom), the room to set
     */
    protected void setRoom(DiscreteCoordinates coords, ICRogueRoom room){
        map[coords.x][coords.y] = room;
    }

    /**
     * @param coords (DiscreteCoordinates): coordinates of the room
     * containing the connector (for which we want to set a destination)
     * @param destination (String): destination room title, not null
     * @param connector (ConnectorInType): The connector for which we're setting the destination, not null
     */
    protected void setRoomConnectorDestination(DiscreteCoordinates coords, String destination,
                                     ConnectorInRoom connector){
        map[coords.x][coords.y].setConnetorDestination(connector.getIndex(), destination);
    }

    /**
     * @param coords (DiscreteCoordinates), coordinates of the room
     * containing the connector we want to set, not null
     * @param destination (String): destination room title, not null
     * @param connector (ConnectorInRoom): The connector to set, not null
     */
    protected void setRoomConnector(DiscreteCoordinates coords, String destination,
                          ConnectorInRoom connector){
        setRoomConnectorDestination(coords, destination, connector);
        map[coords.x][coords.y].setConnectorState(connector.getIndex(), Connector.connectorType.CLOSED);
    }

    /**
     * @param coords (DiscreteCoordinates): coordinates of the room
     * containing the connector we want to lock, not null
     * @param connector (ConnectorInRoom) The connector to lock, not null
     * @param keyId (int): key identifier (to lock the connector with), not null
     */
    protected void lockRoomConnector(DiscreteCoordinates coords, ConnectorInRoom connector,
                           int keyId){
        map[coords.x][coords.y].setConnectorKeyId(connector.getIndex(), keyId);
        map[coords.x][coords.y].setConnectorState(connector.getIndex(), Connector.connectorType.LOCKED);
    }

    /**
     * Setter of initialRoomTitle
     * @param coords (DiscreteCoordinates): coordinates for the initial room in the level's map, not null
     */
    protected void setInitialRoom(DiscreteCoordinates coords){
        initialRoomTitle = map[coords.x][coords.y].getTitle();
    }

    /**
     * Add all non null rooms of the level's map
     * @param icRogue (ICRogue)
     */
    public void addArea(ICRogue icRogue){
        for(int i=0;i<width;++i){
            for(int j=0;j<height;++j){
                if(map[i][j]!=null){
                    icRogue.addArea(map[i][j]);
                }
            }
        }
    }

    /**
     * Getter for the initial room's title
     * @return initialRoomTitle (String), not null
     */
    public String getInitialRoomTitle(){
        return initialRoomTitle;
    }

    /**
     * Abstract methode for generating a  fixed (non random) map by calling a specific map generator (generateFinalMap() for example)
     */
    abstract protected void generateFixedMap();

    @Override
    public boolean isOn() {
        if(bossRoomCoords!=null){
            return map[bossRoomCoords.x][bossRoomCoords.y].isOn();
        }else{
            for(ICRogueRoom[] icRogueRooms:map){
                for(ICRogueRoom icRogueRoom:icRogueRooms){
                    if(icRogueRoom!=null && icRogueRoom.isOff()){
                        return false;
                    }
                }
            }
            return true;
        }
    }


    protected enum MapState {
        NULL , // Empty space
        PLACED , // The room has been placed but not yet explored by the room placement algorithm
        EXPLORED , // The room has been placed and explored by the algorithm
        BOSS_ROOM , // The room is a boss room
        CREATED ; // The room has been instantiated in the room map
        @Override
        public String toString () {
            return Integer.toString(ordinal());
        }
    }

    /**
     * @param nbrRooms (int), number of rooms to generate
     * @return mapState (MapState[][]), not null
     */
    protected MapState[][] generateRandomPlacement(int nbrRooms){
        MapState[][] mapStates= new MapState[nbrRooms][nbrRooms];
        ArrayList<Integer> toExploreI = new ArrayList<>();
        ArrayList<Integer> toExploreJ = new ArrayList<>();
        //init all map elements as NULL
        for(int i=0;i<nbrRooms;++i){
            for(int j=0;j<nbrRooms;++j){
                mapStates[i][j] = MapState.NULL;
            }
        }
        mapStates[(nbrRooms-1)/2][(nbrRooms-1)/2] = MapState.PLACED;
        toExploreI.add((nbrRooms-1)/2);
        toExploreJ.add((nbrRooms-1)/2);

        int roomsToPlace = nbrRooms-1;
        while(roomsToPlace>0){
            int count = 0;
            int i = toExploreI.get(0);
            int j = toExploreJ.get(0);
            ArrayList<Integer> availableSlotsI = new ArrayList<>();
            ArrayList<Integer> availableSlotsJ = new ArrayList<>();
            ArrayList<Integer> indexIJ = new ArrayList<>();
            // add them to the availableSlots
            if(i+1<nbrRooms && mapStates[i+1][j]==MapState.NULL){
                availableSlotsI.add(i+1);
                availableSlotsJ.add(j);
                indexIJ.add(count);
                ++count;
            }
            if(i-1>=0 && mapStates[i-1][j]==MapState.NULL){
                availableSlotsI.add(i-1);
                availableSlotsJ.add(j);
                indexIJ.add(count);
                ++count;
            }
            if(j+1<nbrRooms && mapStates[i][j+1]==MapState.NULL){
                availableSlotsI.add(i);
                availableSlotsJ.add(j+1);
                indexIJ.add(count);
                ++count;
            }
            if(j-1>=0 && mapStates[i][j-1]==MapState.NULL){
                availableSlotsI.add(i);
                availableSlotsJ.add(j-1);
                indexIJ.add(count);
                ++count;
            }
            if(!indexIJ.isEmpty()){
                int k = RandomHelper.roomGenerator.nextInt(1, Math.min(roomsToPlace, indexIJ.size())+1);
                roomsToPlace-=k;

                List<Integer> randomIndex = RandomHelper.chooseKInList(k, indexIJ.stream().toList() );
                for(int index : randomIndex){
                    mapStates[availableSlotsI.get(index)][availableSlotsJ.get(index)] = MapState.PLACED;
                    toExploreI.add(availableSlotsI.get(index));
                    toExploreJ.add(availableSlotsJ.get(index));
                }
            }
            mapStates[toExploreI.remove(0)][toExploreJ.remove(0)] = MapState.EXPLORED;
        }
        //set boss room
        roomsToPlace= 1;
        while(roomsToPlace>0){

            int count = 0;
            int i = toExploreI.get(0);
            int j = toExploreJ.get(0);
            ArrayList<Integer> availableSlotsI = new ArrayList<>();
            ArrayList<Integer> availableSlotsJ = new ArrayList<>();
            ArrayList<Integer> indexIJ = new ArrayList<>();
            // add them to the availableSlots
            if(i+1<nbrRooms && mapStates[i+1][j]==MapState.NULL){
                availableSlotsI.add(i+1);
                availableSlotsJ.add(j);
                toExploreI.add(i+1);
                toExploreJ.add(j);
                indexIJ.add(count);
                ++count;
            }
            if(i-1>=0 && mapStates[i-1][j]==MapState.NULL){
                availableSlotsI.add(i-1);
                availableSlotsJ.add(j);
                toExploreI.add(i-1);
                toExploreJ.add(j);
                indexIJ.add(count);
                ++count;
            }
            if(j+1<nbrRooms && mapStates[i][j+1]==MapState.NULL){
                availableSlotsI.add(i);
                availableSlotsJ.add(j+1);
                toExploreI.add(i);
                toExploreJ.add(j+1);
                indexIJ.add(count);
                ++count;
            }
            if(j-1>=0 && mapStates[i][j-1]==MapState.NULL){
                availableSlotsI.add(i);
                availableSlotsJ.add(j-1);
                toExploreI.add(i);
                toExploreJ.add(j+1);
                indexIJ.add(count);
                ++count;
            }
            if(!indexIJ.isEmpty()){
                --roomsToPlace;
                List<Integer> randomIndex = RandomHelper.chooseKInList(1, indexIJ.stream().toList() );
                    mapStates[availableSlotsI.get(randomIndex.get(0))][availableSlotsJ.get(randomIndex.get(0))] = MapState.BOSS_ROOM;
                    bossRoomCoords = new DiscreteCoordinates(availableSlotsI.get(randomIndex.get(0)), availableSlotsJ.get(randomIndex.get(0)));
            }
            mapStates[toExploreI.remove(0)][toExploreJ.remove(0)] = MapState.EXPLORED;

        }
        return mapStates;

    }

    /**
     * prints the given mapState as a bi-dimensional table
     * @param map (MapState[][])
     */
    protected void printMap ( MapState [][] map ) {
        System . out . println (" Generated map:");
        System . out . print (" | ");
        for ( int j = 0; j < map [0]. length ; j ++) {
            System . out . print (j + " ");
        }
        System . out . println ();
        System . out . print (" --|-");
        for ( int j = 0; j < map [0]. length ; j ++) {
            System . out . print ("--");
        }
        System . out . println ();
        for ( int i = 0; i < map . length ; i ++) {
            System . out . print (i + " | ");
            for ( int j = 0; j < map [i]. length ; j ++) {
                System . out . print ( map [i][j] + " ");
            }
            System . out . println ();
        }
        System . out . println ();
    }

    /**
     * @param mapStates (MapState[][])
     * @param roomsDistribution (int[])
     */
    public void generateRandomMap(MapState[][] mapStates, int[]roomsDistribution){

        for(int k=0;k<roomsDistribution.length;++k){
            ArrayList<Integer> availableSlotsI = new ArrayList<>();
            ArrayList<Integer> availableSlotsJ = new ArrayList<>();
            ArrayList<Integer> indexIJ = new ArrayList<>();
            int count = 0;

            //adding available freeSpots
            for(int i=0;i< mapStates.length;++i){
                for(int j=0;j< mapStates.length;++j){
                    if(mapStates[i][j] == MapState.EXPLORED || mapStates[i][j] == MapState.PLACED){
                        availableSlotsI.add(i);
                        availableSlotsJ.add(j);
                        indexIJ.add(count);
                        ++count;
                    }
                }
            }
            List<Integer> randomIndex = RandomHelper.chooseKInList(roomsDistribution[k], indexIJ.stream().toList() );
            for(int index:randomIndex){
                int indexI = availableSlotsI.get(index);
                int indexJ = availableSlotsJ.get(index);
                mapStates[indexI][indexJ] = MapState.CREATED;
                //In this enhanced switch, k takes the values 0 to 6 corresponding to our enum Type "RoomType"  ordinals
                switch(k){
                    case 0 -> map[indexI][indexJ] = new Level0TurretRoom(
                            new DiscreteCoordinates(indexI,indexJ));
                    case 1 -> map[indexI][indexJ] = new Level0StaffRoom(
                            new DiscreteCoordinates(indexI,indexJ));
                    case 2 -> map[indexI][indexJ] = new Level0KeyRoom(
                            new DiscreteCoordinates(indexI,indexJ),BOSS_ROOM_KEY_ID);
                    case 3 -> {map[indexI][indexJ] = new Level0Room(
                            new DiscreteCoordinates(indexI,indexJ));
                            startPosition = new DiscreteCoordinates(indexI, indexJ);}
                    case 4 -> map[indexI][indexJ] = new Level0Room(
                            new DiscreteCoordinates(indexI,indexJ));
                    case 5 -> map[indexI][indexJ] = new Level0SwordRoom(
                            new DiscreteCoordinates(indexI,indexJ));
                    case 6 -> map[indexI][indexJ] = new Level0HealingRoom(
                            new DiscreteCoordinates(indexI,indexJ), HEALING_POINTS);
                }

            }
        }
        //set boss room
        map[bossRoomCoords.x][bossRoomCoords.y] = new Level0TurretRoom(
                new DiscreteCoordinates(bossRoomCoords.x,bossRoomCoords.y));
    }

    protected abstract void setUpConnector(MapState[][] roomsPlacement, ICRogueRoom room);

    public enum RoomType {
        TURRET_ROOM (3) , // type and number of room
        STAFF_ROOM (1) ,
        BOSS_KEY (1) ,
        SPAWN (1) ,
        NORMAL (1),
        SWORD_ROOM(1),
        HEALING_ROOM(1);

        private final int number;
        RoomType(int number){
            this.number = number;
        }
        public static int[] getRoomsDistribution(){
            int[] roomsDistribution = new int[RoomType.values().length];
            for(RoomType roomType:RoomType.values()){
                roomsDistribution[roomType.ordinal()] = roomType.number;
            }
            return roomsDistribution;
        }
    }




    @Override
    public boolean isOff() {
        return !isOn();
    }

    @Override
    public float getIntensity() {
        return 0;
    }

}
