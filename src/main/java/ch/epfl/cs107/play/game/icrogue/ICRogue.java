package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.icrogue.area.Level;
import ch.epfl.cs107.play.game.icrogue.area.level0.Level0;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;


public class ICRogue extends AreaGame {
    //Initial room coordinates for player
    private final static DiscreteCoordinates PLAYER_INITIAL_ROOM_COORDS = new DiscreteCoordinates(2, 2);
    //The default scale camera factor for ICRogue
    public final static float CAMERA_SCALE_FACTOR = 11.f;
    //Main player of the game
    private ICRoguePlayer player;
    //The displayed room of the current level. The one where the player is
    private ICRogueRoom currentRoom;
    //The level currently being solved by the player (one room at a time)
    private Level0 currentLevel;
    //create the list of all the levels
    private final static Level[] levels = new Level[]{new Level0(true), new Level0(false)};
    // Max value for level number
    private final static int MAX_LEVEL_NUMBER = levels.length - 1;
    //index of the current level
    //initially set to 0 to indicate that we start with the first level of the levels list
    private int levelNumber = 0;
    //Indicates if we need to initialize the players
    private boolean initializingPlayers = true;

    /**
     * initializes the current level
     * @param levelNumber (int) : current level's index in the list of levels, positive integer
     */
    private void initLevel(int levelNumber){
        //Create the level, add its rooms
        currentLevel = (Level0) levels[levelNumber];
        currentLevel.addArea(this);
        //Set the current room
        currentRoom = (ICRogueRoom) setCurrentArea(currentLevel.getInitialRoomTitle(), true);

        if (initializingPlayers) {
            //create players in the current (initial) room
            player = new ICRoguePlayer(currentRoom, Orientation.UP, PLAYER_INITIAL_ROOM_COORDS);
            initializingPlayers = false;
        }
        //transfer(register) the player to the new level
        player.enterArea(currentRoom, PLAYER_INITIAL_ROOM_COORDS);
    }

    /**
     * Remove the players from the finished level,
     * initialize the next one, and make the players enter it.
     * Need to verify that the next level exists before calling this function.
     */
    private void levelUp(){
       player.leaveArea();
        ++levelNumber;
        initLevel(levelNumber);
    }



    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            //initialize the first level of the Levels list
            initLevel(levelNumber);
            return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Keyboard keyboard = getCurrentArea().getKeyboard();
        if(keyboard.get(Keyboard.R).isDown()){
            player.leaveArea();
            initLevel(levelNumber);
        }
        if(player.isInTransition()){
            switchRoom();
        }
        //cheat codes
        /*if(keyboard.get(Keyboard.O).isDown()){
            currentRoom.openClosedConnectors();
        }
        if(keyboard.get(Keyboard.T).isDown()){
            currentRoom.switchConnectorsOpenClosed();
        }
        if(keyboard.get(Keyboard.L).isDown()){
            currentRoom.lockWestConnector();
        }*/
        if(currentRoom.isOn()){
            currentRoom.openClosedConnectors();
        }
        if(currentLevel.isOn()){
            if(levelNumber < MAX_LEVEL_NUMBER){
                System.out.println("Level UP!");
                levelUp();
            } else{

                System.out.println("WIN");
                end();
            }
        }
        if(player.isDead()){
            System.out.println("GameOver");
            end();
        }


    }
    @Override
    public void end() {
        System.exit(0);
    }

    @Override
    public String getTitle() {
        return "ICRogue";
    }

    /**
     * moves the player from the current area he's in to
     *the destination area and sets along the way this destination
     *  area as the current one
     */
    protected void switchRoom() {
        player.leaveArea();
        currentRoom = (ICRogueRoom) setCurrentArea(player.getDestination(), false);
        player.enterArea(currentRoom, player.getNewPosition());
        player.endTransition();
    }
}
