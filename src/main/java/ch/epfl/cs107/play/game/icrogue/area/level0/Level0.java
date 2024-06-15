package ch.epfl.cs107.play.game.icrogue.area.level0;

import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.icrogue.area.Level;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0KeyRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0StaffRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0TurretRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0 extends Level{
    private static final int DEFAULT_HEIGHT = 2;
    private static final int DEFAULT_WIDTH = 4;
    private static final DiscreteCoordinates INITIAL_ROOM_COORDS = new DiscreteCoordinates(1,0);
    private static final DiscreteCoordinates DEFAULT_START_POSITION= new DiscreteCoordinates(2,2);

    /**
     * Default Level0 Constructor
     * @param randomMap (boolean), not null
     * @param startPosition (DiscreteCoordinates): start position, not null
     * @param roomsDistribution (int[]): 1-dimentional int table containing the map's room distribution, not null
     * @param width (int): map's width, not null
     * @param height (height): map's height, not null
     */
    public Level0(boolean randomMap, DiscreteCoordinates startPosition,int[] roomsDistribution , int width, int height) {
        super(randomMap, startPosition, roomsDistribution, width, height);
        if(randomMap){
            generateRandomMap();
        }else{
            generateFixedMap();
        }
    }

    /**
     * Level0 Constructor
     * @param randomMap (boolean), not null;
     * startPosition, roomDistribution, width and heigth are all set to default values (predefined in the class Level0 as final and static)
     */
    public Level0(boolean randomMap){
        this(randomMap, DEFAULT_START_POSITION, RoomType.getRoomsDistribution(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Generate the map using the randomly generated mapStates,
     * set the initial room and set up the rooms' connectors
     */
    protected void generateRandomMap(){
        MapState[][] mapStates = generateRandomPlacement(width);
        generateRandomMap(mapStates, roomsDistribution);
        for (ICRogueRoom[] icRogueRooms : map) {
            for (ICRogueRoom icRogueRoom : icRogueRooms) {
                setUpConnector(mapStates, icRogueRoom);
            }
        }
        setInitialRoom(startPosition);
    }

    @Override
    protected void generateFixedMap(){
        generateFinalMap();
    }
    private static final int PART_1_KEY_ID = 1;
    private static final int BOSS_KEY_ID = 2;

    /**
     * Generate the non random map by setting the rooms, connectors and initial room
     */
    private void generateMap1() {
        DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
        setRoom(room00, new Level0KeyRoom(room00, PART_1_KEY_ID));
        setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connector.E);
        lockRoomConnector(room00, Level0Room.Level0Connector.E,  PART_1_KEY_ID);

        DiscreteCoordinates room10 = new DiscreteCoordinates(1, 0);
        setRoom(room10, new Level0Room(room10));
        setRoomConnector(room10, "icrogue/level000", Level0Room.Level0Connector.W);
        setInitialRoom(INITIAL_ROOM_COORDS);
    }
    /**
     * Generate the non random map by setting the rooms, connectors and initial room
     */
    private void generateMap2() {
        DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
        setRoom(room00, new Level0Room(room00));
        setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connector.E);

        DiscreteCoordinates room10 = new DiscreteCoordinates(1,0);
        setRoom(room10, new Level0Room(room10));
        setRoomConnector(room10, "icrogue/level011", Level0Room.Level0Connector.S);
        setRoomConnector(room10, "icrogue/level020", Level0Room.Level0Connector.E);

        lockRoomConnector(room10, Level0Room.Level0Connector.W,  BOSS_KEY_ID);
        setRoomConnectorDestination(room10, "icrogue/level000", Level0Room.Level0Connector.W);

        DiscreteCoordinates room20 = new DiscreteCoordinates(2,0);
        setRoom(room20,  new Level0StaffRoom(room20));
        setRoomConnector(room20, "icrogue/level010", Level0Room.Level0Connector.W);
        setRoomConnector(room20, "icrogue/level030", Level0Room.Level0Connector.E);

        DiscreteCoordinates room30 = new DiscreteCoordinates(3,0);
        setRoom(room30, new Level0KeyRoom(room30, BOSS_KEY_ID));
        setRoomConnector(room30, "icrogue/level020", Level0Room.Level0Connector.W);

        DiscreteCoordinates room11 = new DiscreteCoordinates(1, 1);
        setRoom (room11, new Level0Room(room11));
        setRoomConnector(room11, "icrogue/level010", Level0Room.Level0Connector.N);

        setInitialRoom(INITIAL_ROOM_COORDS);
    }

    /**
     * Generate the non random map by setting the rooms, connectors and initial room
     */
    private void generateFinalMap(){

        DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
        setRoom(room00, new Level0TurretRoom(room00));
        setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connector.E);

        DiscreteCoordinates room10 = new DiscreteCoordinates(1,0);
        setRoom(room10, new Level0Room(room10));
        setRoomConnector(room10, "icrogue/level011", Level0Room.Level0Connector.S);
        setRoomConnector(room10, "icrogue/level020", Level0Room.Level0Connector.E);

        lockRoomConnector(room10, Level0Room.Level0Connector.W,  BOSS_KEY_ID);
        setRoomConnectorDestination(room10, "icrogue/level000", Level0Room.Level0Connector.W);

        DiscreteCoordinates room20 = new DiscreteCoordinates(2,0);
        setRoom(room20,  new Level0StaffRoom(room20));
        setRoomConnector(room20, "icrogue/level010", Level0Room.Level0Connector.W);
        setRoomConnector(room20, "icrogue/level030", Level0Room.Level0Connector.E);

        DiscreteCoordinates room30 = new DiscreteCoordinates(3,0);
        setRoom(room30, new Level0KeyRoom(room30, BOSS_KEY_ID));
        setRoomConnector(room30, "icrogue/level020", Level0Room.Level0Connector.W);

        DiscreteCoordinates room11 = new DiscreteCoordinates(1, 1);
        setRoom (room11, new Level0Room(room11));
        setRoomConnector(room11, "icrogue/level010", Level0Room.Level0Connector.N);

        setInitialRoom(INITIAL_ROOM_COORDS);
    }

    /**
     * Set up the room's connectors if the room isn't null:
     * Link the room's connectors to the adjacent rooms if they exist
     * according to theirs placement in the map
     * @param roomsPlacement (MapState[][])
     * @param room (ICRogueRoom)
     */
    @Override
    protected void setUpConnector(MapState[][] roomsPlacement, ICRogueRoom room) {
        if(room != null){
            int i = room.getRoomCoordinates().x;
            int j = room.getRoomCoordinates().y;
            if(i+1<roomsPlacement.length && map[i+1][j]!=null){
                DiscreteCoordinates destination =
                        room.getRoomCoordinates().jump(Level0Room.Level0Connector.S.getOrientation().toVector());
                setRoomConnector(room.getRoomCoordinates(), ("icrogue/level0"+ destination.x+destination.y), Level0Room.Level0Connector.S);
                setRoomConnector(room.getRoomCoordinates(), ("icrogue/level0"+ (i+1)+j), Level0Room.Level0Connector.S);
                if(roomsPlacement[i+1][j] == MapState.BOSS_ROOM){
                    lockRoomConnector(room.getRoomCoordinates(), Level0Room.Level0Connector.S,  BOSS_ROOM_KEY_ID);
                }
            }
            if(i-1>=0 && map[i-1][j]!=null){
                DiscreteCoordinates destination =
                        room.getRoomCoordinates().jump(Level0Room.Level0Connector.N.getOrientation().toVector());
                setRoomConnector(room.getRoomCoordinates(), ("icrogue/level0"+ destination.x+destination.y), Level0Room.Level0Connector.N);
                setRoomConnector(room.getRoomCoordinates(), ("icrogue/level0"+(i-1)+j), Level0Room.Level0Connector.N);
                if(roomsPlacement[i-1][j] == MapState.BOSS_ROOM){
                    lockRoomConnector(room.getRoomCoordinates(), Level0Room.Level0Connector.N,  BOSS_ROOM_KEY_ID);
                }
            }
            if(j+1<roomsPlacement.length && map[i][j+1]!=null){
                DiscreteCoordinates destination =
                        room.getRoomCoordinates().jump(Level0Room.Level0Connector.E.getOrientation().toVector());
                setRoomConnector(room.getRoomCoordinates(), ("icrogue/level0"+ destination.x+destination.y), Level0Room.Level0Connector.E);
                setRoomConnector(room.getRoomCoordinates(), ("icrogue/level0"+i+(j+1)), Level0Room.Level0Connector.E);
                if(roomsPlacement[i][j+1] == MapState.BOSS_ROOM){
                    lockRoomConnector(room.getRoomCoordinates(), Level0Room.Level0Connector.E,  BOSS_ROOM_KEY_ID);
                }
            }
            if(j-1>=0 && map[i][j-1]!=null){
                DiscreteCoordinates destination =
                        room.getRoomCoordinates().jump(Level0Room.Level0Connector.W.getOrientation().toVector());
                setRoomConnector(room.getRoomCoordinates(), ("icrogue/level0"+ destination.x+destination.y), Level0Room.Level0Connector.W);
                setRoomConnector(room.getRoomCoordinates(), ("icrogue/level0"+i+(j-1)), Level0Room.Level0Connector.W);
                if(roomsPlacement[i][j-1] == MapState.BOSS_ROOM){
                    lockRoomConnector(room.getRoomCoordinates(), Level0Room.Level0Connector.W,  BOSS_ROOM_KEY_ID);
                }
            }

    }
}
}
