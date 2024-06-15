package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class Connector extends AreaEntity {
    // Connector's default key identifier when no key is needed
    // by the player to open this connector (while interacting).
    public static final int NO_KEY_ID = 99;
    // Default connector type
    // Invisible because if the connector doesn't lead to any room, we just have a wall
    private static final connectorType DEFAULT_TYPE = connectorType.INVISIBLE;
    // Sprite name for closed door
    private static final String SPRITE_NAME_CLOSED = "icrogue/door_";
    // Sprite name for invisible door which just a wall
    private static final String SPRITE_NAME_INVISIBLE = "icrogue/invisibleDoor_";
    // Sprite name for locked door
    private static final String SPRITE_NAME_LOCKED = "icrogue/lockedDoor_";
    private connectorType type;
    private Sprite sprite;
    private String title;
    //THe player's spawn position in the room the connector transferred him in.
    private DiscreteCoordinates playerSpawnPosition;
    // Key identifier for the connector's lock
    private int keyId;

    /**
     * Default Connector Constructor
     * @param area (Area), not null
     * @param coordinates (DiscreteCoordinates): coordinates, not null
     * @param orientation (Orientation): orientation, not null
     * @param type (connectorType): indicates the connector's type
     */
    public Connector(Area area, DiscreteCoordinates coordinates, Orientation orientation,
                     connectorType type) {
        super(area, orientation, coordinates);
        this.type = type;
        this.keyId = NO_KEY_ID;
        updateSprite();
    }
    /**
     * Connector Constructor
     * @param area (Area), not null
     * @param coordinates (DiscreteCoordinates): coordinates, not null
     * @param orientation (Orientation), orientation, not null
     * the connector's type is set to the default type DEFAULT_TYPE as defined
     */
    public Connector(Area area, DiscreteCoordinates coordinates, Orientation orientation){
        this(area, coordinates, orientation, DEFAULT_TYPE);
    }

    /**
     * Set the connector's sprite in correspondence with his type
     */
    public void updateSprite(){
        int orientationOrdinal = getOrientation().ordinal();
        switch (type) {
            case CLOSED -> sprite = new Sprite(SPRITE_NAME_CLOSED + orientationOrdinal,
                    (orientationOrdinal + 1) % 2 + 1, orientationOrdinal % 2 + 1, this);
            case INVISIBLE -> sprite = new Sprite(SPRITE_NAME_INVISIBLE + orientationOrdinal,
                    (orientationOrdinal + 1) % 2 + 1, orientationOrdinal % 2 + 1, this);
            case LOCKED -> sprite = new Sprite(SPRITE_NAME_LOCKED + orientationOrdinal,
                    (orientationOrdinal + 1) % 2 + 1, orientationOrdinal % 2 + 1, this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(type!=connectorType.OPEN){
            sprite.draw(canvas);
        }

    }
    @Override
    public void update(float deltaTime){
        updateSprite();
        super.update(deltaTime);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        DiscreteCoordinates coord = getCurrentMainCellCoordinates ();
        return List.of(coord ,
                coord.jump(new Vector((getOrientation().ordinal()+1)%2,getOrientation().ordinal()%2)));
    }

    @Override
    public boolean takeCellSpace() {
        return type != connectorType.OPEN;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }


    public enum connectorType{
        CLOSED(),INVISIBLE(),LOCKED(),OPEN();
        connectorType(){}
        }

    /**
     * Getter for the connector's title
     * @return title (String)
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for the connector's title
     * @param title (String)
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Setter for the connector's type
     * @param type (ConnectorType)
     */
    public void setType(connectorType type){
        this.type = type;
    }

    /**
     * Setter for the connector's key ID
     * @param keyId (int)
     */
    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    /**
     * Setter for the connector's key ID
     * @return keyID (int)
     */
    public int getKeyId(){
        return  keyId;
    }

    /**
     * Getter for the player's spawn position after interacting with the connector
     * @return playerSpawnPosition (DiscreteCoordinates)
     */

    public DiscreteCoordinates getPlayerSpawnPosition() {
        return playerSpawnPosition;
    }

    /**
     * Setter for the player's spawn position after interacting wwith the connector
     * @param playerSpawnPosition (DiscreteCoordinates)
     */
    public void setPlayerSpawnPosition(DiscreteCoordinates playerSpawnPosition){
        this.playerSpawnPosition = playerSpawnPosition;
    }

    /**
     * Getter for the connector's type
     * @return type (ConnectorType)
     */
    public connectorType getType(){
        return type;
    }
}
