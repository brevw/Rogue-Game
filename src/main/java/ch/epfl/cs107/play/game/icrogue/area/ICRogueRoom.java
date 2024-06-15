package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.ICRogue;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ICRogueRoom extends Area implements Logic {
    protected abstract void createArea();
    public abstract DiscreteCoordinates getPlayerSpawnPosition();
    private ArrayList<Connector> connectors;

    public final float getCameraScaleFactor(){return ICRogue.CAMERA_SCALE_FACTOR;}
    private ICRogueBehavior behavior;
    private String behaviorName;
    private boolean visited;
    private DiscreteCoordinates roomCoordinates ;
    private List<DiscreteCoordinates> connectorsCoordinates;
    private List<Orientation> orientations;

    /**
     * Default ICRogueRoom Constructor
     * @param connectorsCoordinates (List<DiscreteCoordinates>): connector's coordinates, not null
     * @param orientations (List<Orientation>): orientation, not null
     * @param behaviorName (String): behaviorName, not null
     * @param roomCoordinates (DiscreteCoordinates): room coordinates in map, not null
     */
    public ICRogueRoom (List<DiscreteCoordinates> connectorsCoordinates ,
                        List<Orientation> orientations ,
                        String behaviorName , DiscreteCoordinates roomCoordinates ){
        this.behaviorName = behaviorName;
        this.roomCoordinates  = roomCoordinates;
        this.connectorsCoordinates = connectorsCoordinates;
        this.orientations = orientations;
        this.connectors = new ArrayList<>();
        this.visited = false;
        createConnectors();
    }

    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            // Set the behavior map
            behavior = new ICRogueBehavior(window, behaviorName);
            setBehavior(behavior);
            //create the areas
            createArea();
            return true;
        }
        return false;
    }
    protected void createConnectors() {
        if(connectorsCoordinates.size()==orientations.size()){
            for(int i=0;i<connectorsCoordinates.size();++i){
                Connector connector = new Connector(this, connectorsCoordinates.get(i), orientations.get(i).opposite());
                connector.setPlayerSpawnPosition(Arrays.stream(Level0Room.Level0Connector.values()).toList().get(i).getDestination());
                connectors.add(connector);
            }
        }else{
            System.out.println("Connector and orientation arrays do not have same length");
        }
    }

    protected void registerConnectors(Area area){
        for(Connector connector:connectors){
            registerActor(connector);
        }
    }

    public String getBehaviorName() {
        return behaviorName;
    }

    public DiscreteCoordinates getRoomCoordinates() {
        return roomCoordinates ;
    }
    public void setConnectors(Connector.connectorType connectorType){
        for(Connector connector:connectors){
            connector.setType(connectorType);
        }
    }
    public void switchConnectorsOpenClosed(){
        for(Connector connector: connectors){
            switch (connector.getType()){
                case OPEN -> connector.setType(Connector.connectorType.CLOSED);
                case CLOSED -> connector.setType(Connector.connectorType.OPEN);
            }
        }
    }

    /**
     * opens all the room's closed doors
     */
    public void openClosedConnectors(){
        for(Connector connector: connectors){
            if(connector.getType() == Connector.connectorType.CLOSED){
                connector.setType(Connector.connectorType.OPEN);
            }
        }
    }

    /**
     * @return visited (boolean)
     */
    public boolean isVisited(){
        return visited;
    }

    /**
     * Set visited to true
     */
    public void visit(){
        this.visited = true;
    }

    /**
     *
     */
    public void lockWestConnector(){setConnectorState(0, Connector.connectorType.LOCKED); }
    public void setConnetorDestination(int index, String destination){
        connectors.get(index).setTitle(destination);
    }
    public void setConnectorState(int index, Connector.connectorType connectorType){
        connectors.get(index).setType(connectorType);
    }

    /**
     * locks the room's conenctor at the index position with the given key "keyId"
     * @param index (int) - position of the connector we're considering
     * @param keyId (int) - the keyId we want to lock the door with
     */
    public void setConnectorKeyId(int index, int keyId){
        connectors.get(index).setKeyId(keyId);
    }

}
