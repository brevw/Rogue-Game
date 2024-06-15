package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.items.*;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0EnemyRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ch.epfl.cs107.play.game.icrogue.actor.Connector.NO_KEY_ID;


public class ICRoguePlayer extends ICRogueActor implements Interactor {
    //main player sprite name used to create the associated sprite
    private static final String SPRITE_NAME = "zelda/player";
    //Player's default health points value
    private static final int MAX_HP = 6;
    /// Animation duration in frame number
    private final static int MOVE_DURATION = 5;
    private static final String HEART_SPRITE_NAME = "zelda/heartDisplay";
    private static final float ANIMATION_SWORD_DURATION = 0.2f;
    private Animation[] animations;
    //private final Sprite[] staticSprites;
    private boolean wantsViewInteraction;
    // True if the player has a staff, false otherwise
    private boolean getStaff;
    // True if a player has a sword, false otherwise
    private boolean getSword;
    // True if the sword is being used, so needs to be animated
    // False otherwise
    // Needs to be set to false after finishing the sword  animation
    private boolean animateSword;
    private float timeCounter;
    // True if the player is transiting from a room to another
    // False otherwise
    private boolean inTransition;
    // Title of the destination connector
    private String destination;
    private DiscreteCoordinates newPosition;
    //Player's health points
    private int hp;
    //hearts
    private Sprite[] hearts;
    //List of key identifiers collected by the player
    private ArrayList<Integer> keyIdList;
    private final ICRoguePlayerInteractionHandler handler ;
    private ImageGraphics[] balanceGraphics;
    // Player's current money balance
    private int balance;
    // True if the balance graphics need to be updated
    private boolean updateBalanceGraphicsBool;
    // True if we need to show the shop
    private boolean showShop;
    //array of booleans indicating whether the skin with the corresponding index has been acquired (bought)
    private final boolean[] itemBoughtState;
    private ArrayList<ImageGraphics> shopGraphics;
    // The selected skin in the shop
    private int selectedSkin;
    // The player's currently equipped skin
    private int equippedSkin;

    /**
     *  initializes the shop's graphics (background, slots, skins,..)
     * @param canvas (Canvas), not null
     */
    private void initShop(Canvas canvas){
        float width = canvas. getScaledWidth ();
        float height = canvas . getScaledHeight ();
        Vector[] slots = {
                new Vector (width/3-2 -0.15f, height/2),
                new Vector (width/3+1 -0.15f, height/2),
                new Vector (width/3+4 -0.15f, height/2),
                new Vector(width/3+1 -0.15f, height/2+2.5f)
        };
        Vector anchor = canvas . getTransform (). getOrigin (). sub ( new
                Vector ( width /2 , height /2) );
        shopGraphics = new ArrayList<>(Arrays.asList(
                new ImageGraphics(ResourcePath.getSprite("zelda/inventory.background"),
                        9f, 9f, new RegionOfInterest(0, 0, 240, 240),
                        anchor.add(new Vector(1, 1)), 1, 1),
                //slot1
                new ImageGraphics(ResourcePath.getSprite("zelda/inventory.slot"),
                        2f, 2f, new RegionOfInterest(0, 0, 64, 64),
                        anchor.add(slots[0]), 1, 1),
                //slot2
                new ImageGraphics(ResourcePath.getSprite("zelda/inventory.slot"),
                        2f, 2f, new RegionOfInterest(0, 0, 64, 64),
                        anchor.add(slots[1]), 1, 1),
                //slot3
                new ImageGraphics(ResourcePath.getSprite("zelda/inventory.slot"),
                        2f, 2f, new RegionOfInterest(0, 0, 64, 64),
                        anchor.add(slots[2]), 1, 1),
                //default slot
                new ImageGraphics(ResourcePath.getSprite("zelda/inventory.slot"),
                        2f, 2f, new RegionOfInterest(0, 0, 64, 64),
                        anchor.add(slots[3]), 1, 1),
                //selected slot
                new ImageGraphics(ResourcePath.getSprite("zelda/inventory.selector"),
                        2f, 2f, new RegionOfInterest(0, 0, 64, 64),
                        anchor.add(slots[selectedSkin]), 1, 1),
                //slot 1 item
                new ImageGraphics(ResourcePath.getSprite("policeman"),
                        1.4f, 1.83f, new RegionOfInterest(0, 0, 16, 21),
                        anchor.add(new Vector(slots[0].getX() + 0.35f, slots[0].getY() + 0.1f)), 1, 1),
                //slot 2 item
                new ImageGraphics(ResourcePath.getSprite("player"),
                        1.4f, 1.4f, new RegionOfInterest(0, 0, 16, 16),
                        anchor.add(new Vector(slots[1].getX() + 0.35f, slots[1].getY() + 0.3f)), 1, 1),
                //slot 3 item
                new ImageGraphics(ResourcePath.getSprite("girl.1"),
                        1.4f, 1.83f, new RegionOfInterest(0, 0, 16, 21),
                        anchor.add(new Vector(slots[2].getX() + 0.35f, slots[2].getY() + 0.3f)), 1, 1),
                //default slot item
                new ImageGraphics(ResourcePath.getSprite("zelda/player"),
                        1.4f, 2.8f, new RegionOfInterest(0, 0, 16, 32),
                        anchor.add(new Vector(slots[1].getX() + 0.35f, slots[1].getY() + 0.3f + 1.9f)), 1, 1)
        ));
        if(!itemBoughtState[selectedSkin]){
            shopGraphics.add(
                    new ImageGraphics ( ResourcePath. getSprite ("myPngs/buy"),
                            2f, 0.75f, new RegionOfInterest(0 , 0, 174 , 65) ,
                            anchor . add ( new Vector(width/2-1, height/2-3)), 1, 1 )
            );
            shopGraphics.add(
                    new ImageGraphics( ResourcePath. getSprite ("zelda/digits"),
                            0.75f, 0.75f, new RegionOfInterest(digitType.digitToType(1).coords.x , digitType.digitToType(1).coords.y, 16 , 16) ,
                            anchor . add ( new Vector (width/2-0.8f, height/2-3 +0.8f)), 1, 1 )
            );
            shopGraphics.add(
                    new ImageGraphics( ResourcePath. getSprite ("zelda/digits"),
                            0.75f, 0.75f, new RegionOfInterest(digitType.digitToType(0).coords.x , digitType.digitToType(0).coords.y, 16 , 16) ,
                            anchor . add ( new Vector (width/2-0.45f, height/2-3 +0.8f)), 1, 1 )
            );
            shopGraphics.add(
                    new ImageGraphics( ResourcePath. getSprite ("zelda/coin"),
                            0.75f, 0.75f, new RegionOfInterest(0 , 0, 16 , 16) ,
                            anchor . add ( new Vector (width/2+0.1f, height/2-3 +0.8f-0.05f)), 1, 1 )
            );
        }else {
            if(selectedSkin == equippedSkin){
                shopGraphics.add(
                        new ImageGraphics ( ResourcePath. getSprite ("myPngs/equiped"),
                                4f, 0.64f, new RegionOfInterest(0 , 0, 396 , 65) ,
                                anchor . add ( new Vector(width/2-2, height/2-3)), 1, 1 )
                );
            }else{
                shopGraphics.add(
                        new ImageGraphics ( ResourcePath. getSprite ("myPngs/use"),
                                2f, 0.75f, new RegionOfInterest(0 , 0, 174 , 65) ,
                                anchor . add ( new Vector(width/2-1, height/2-3)), 1, 1 )
                );
            }
        }
    }

    /**
     * draw on canvas the shop from the shopGraphics list
     * @param canvas (Canvas): Canvas to draw on, not null.
     */
    public void drawShop(Canvas canvas){
        for(ImageGraphics graphics:shopGraphics){
            graphics.draw(canvas);
        }
    }



    public enum digitType{
        VECT_0(new DiscreteCoordinates(16,32)),
        VECT_1(new DiscreteCoordinates(0,0)),
        VECT_2(new DiscreteCoordinates(16,0)),
        VECT_3(new DiscreteCoordinates(32,0)),
        VECT_4(new DiscreteCoordinates(48,0)),
        VECT_5(new DiscreteCoordinates(0,16)),
        VECT_6(new DiscreteCoordinates(16,16)),
        VECT_7(new DiscreteCoordinates(32,16)),
        VECT_8(new DiscreteCoordinates(48,16)),
        VECT_9(new DiscreteCoordinates(0,32));
        private final DiscreteCoordinates coords;
        digitType(DiscreteCoordinates coords){
            this.coords = coords;
        }
        public DiscreteCoordinates getCoords(){
            return coords;
        }
        public static digitType digitToType(int digit){
            if(digit>=0 && digit<=9){
                return digitType.values()[digit];
            }
            return null;
        }
    }

    /**
     * draw on canvas the balance from the balanceGraphics list
     * @param canvas (Canvas): Canvas to draw on, not null.
     */
    public void drawBalance(Canvas canvas){
        for(ImageGraphics graphics:balanceGraphics){
            graphics.draw(canvas);
        }
    }

    /**
     * update the balance graphics by getting the player's current balance
     * @param canvas (Canvas): Canvas to draw on, not null.
     */
    public void updateBalance(Canvas canvas){
        balanceGraphics = balanceToGraphics(balance, canvas);
    }

    /**
     *
     * @param balance (int): The player's money balance
     * @param canvas canvas (Canvas): Canvas to draw on, not null.
     * @return (ImageGraphics[]):The corresponding image graphic for
     * the coins of the player's  the balance (balance represented by digits sprites and the coin by its sprite)
     */
    private ImageGraphics[] balanceToGraphics(int balance, Canvas canvas){
        String balanceString = Integer.toString(balance);
        ImageGraphics[] toReturn = new ImageGraphics[balanceString.length()+1];
        float width = canvas. getScaledWidth ();
        float height = canvas . getScaledHeight ();
        Vector anchor = canvas . getTransform (). getOrigin (). sub ( new
                Vector ( width /2 , height /2) );
        toReturn[0] = new
                ImageGraphics ( ResourcePath. getSprite ("zelda/coinsDisplay"),
                3f, 1.5f, new RegionOfInterest(0 , 0, 64 , 32) ,
                anchor . add ( new Vector (width - 3.75f , height - 1.75f)), 1, 1 );
        for(int i=0;i<balanceString.length();++i){
            DiscreteCoordinates digitVector = digitType.digitToType(Integer.parseInt(String.valueOf(balanceString.charAt(balanceString.length()-1-i)))).getCoords();
            toReturn[i+1] = new
                    ImageGraphics( ResourcePath. getSprite ("zelda/digits"),
                    0.75f, 0.75f, new RegionOfInterest(digitVector.x , digitVector.y, 16 , 16) ,
                    anchor . add ( new Vector (width - 1.75f -i*0.35f , height - 1.35f)), 1, 1 );
        }
        return toReturn;
    }

    /**
     * Set the hearts sprites depending on his health points
     */
    public void updateHeartsSprites(){
        this.hearts = hpToSprites(hp);
        for(int i=0; i<hearts.length; ++i){
            hearts[i].setAnchor(new Vector(-0.25f+0.5f*i,1.7f));
        }
    }

    /**
     * Convert health points to concrete heart sprites next to the player.
     * Determine which sprite to set depending on the player's current hp value.
     * In our implementation, every half heart represents 1 hp of the total player's health points
     * The calculations for the correspondence between the hp value and the chosen sprite work on the chosen value of MAX_HP
     * @param hp (int): health points, positive integer.
     * @return (Sprite[]):  the chosen 3 sprites to draw. We always draw 3 heart sprites but weather it's full,
     * half full, or empty depends on the current hp value

     */
    private Sprite[] hpToSprites(int hp){
        return new Sprite[]{new Sprite(HEART_SPRITE_NAME, 0.6f, 0.6f, this, new RegionOfInterest(16*Math.min(Math.max(0,hp), 2), 0, 16, 16)),
                new Sprite(HEART_SPRITE_NAME, 0.6f, 0.6f, this, new RegionOfInterest(16*Math.min(Math.max(0,hp-2), 2), 0, 16, 16)),
                new Sprite(HEART_SPRITE_NAME, 0.6f, 0.6f, this, new RegionOfInterest(16*Math.min(Math.max(0,hp-4), 2), 0, 16, 16))};
    }

    /**
     * draw on canvas the hearts sprites already constructed in the list hearts
     * @param canvas (Canvas): Canvas to draw on, not null.
     */
    private void drawHearts(Canvas canvas){
        for(Sprite sprite:hearts){
            sprite.draw(canvas);
        }
    }
    /**
     * Default ICRoguePlayer Constructor
     * @param owner (Area), not null
     * @param orientation (Orientation): orientation, not null
     * @param coordinates (DiscreteCoordinates), coordinates, not null
     */
    public ICRoguePlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates);
        keyIdList = new ArrayList<>();
        wantsViewInteraction = false;
        getStaff = false;
        getSword = false;
        animateSword = false;
        inTransition = false;
        timeCounter = 0f;
        this.hp = MAX_HP;
        handler = new ICRoguePlayerInteractionHandler();
        balance = 10;
        showShop = false;
        itemBoughtState = new boolean[]{false,false,false,true};
        selectedSkin = 0;
        equippedSkin = 3;
        updateBalanceGraphicsBool = true;
        updateHeartsSprites();
        updatePlayerSkin();
        resetMotion();
    }

    @Override
    public void draw(Canvas canvas) {
        switch (getOrientation()) {
            case DOWN -> animations[0].draw(canvas);
            case RIGHT -> animations[1].draw(canvas);
            case UP -> animations[2].draw(canvas);
            case LEFT -> animations[3].draw(canvas);
        }
        drawHearts(canvas);
        //draw balance
        if(updateBalanceGraphicsBool){
            updateBalance(canvas);
            updateBalanceGraphicsBool = false;
        }

        //draw shop
        if(showShop){
            initShop(canvas);
            drawShop(canvas);
        }
        drawBalance(canvas);
        //draw wantViewInteraction state on the screen
        String text;
        if(wantsViewInteraction){
            text = "wantsViewInteraction : true";
        }else {
            text = "wantsViewInteraction : false";
        }
        TextGraphics textGraphics = new TextGraphics(text, 0.5f, Color.BLUE,Color.BLUE,0f,false,false,new Vector(0.1f, canvas.getScaledHeight()-1));
        textGraphics.draw(canvas);
    }


    @Override
    public void update(float deltaTime) {
        //sword animation when killing an enemy
        if(animateSword && equippedSkin ==3){
            timeCounter += deltaTime;
        }
        if(timeCounter > ANIMATION_SWORD_DURATION && equippedSkin ==3){
            updatePlayerSkin();
            timeCounter = 0;
            animateSword = false;
        }

        if( isDisplacementOccurs() || (animateSword && timeCounter < ANIMATION_SWORD_DURATION)){
            switch (getOrientation()) {
                case DOWN -> animations[0].update(deltaTime);
                case RIGHT -> animations[1].update(deltaTime);
                case UP -> animations[2].update(deltaTime);
                case LEFT -> animations[3].update(deltaTime);
            }
        }

        Keyboard keyboard = getOwnerArea().getKeyboard();
        if(keyboard.get(Keyboard.W).isPressed()){
            switchWantsViewInteraction();
        }

        if(getStaff() && keyboard.get(Keyboard.X).isPressed()){
            throwFire();
        }
        if(keyboard.get(Keyboard.P).isPressed()){
            showShop = true;
        }
        //modified
        if(!showShop){
            moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
            moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
            moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
            moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
        }else{
            if(!itemBoughtState[selectedSkin] && keyboard.get(Keyboard.ENTER).isPressed() && balance>=10){
                itemBoughtState[selectedSkin] = true;
                balance-=10;
                updateBalanceGraphicsBool = true;
            }
            else if (itemBoughtState[selectedSkin] && keyboard.get(Keyboard.ENTER).isPressed()){
                equippedSkin = selectedSkin;
            }
            if(keyboard.get(Keyboard.RIGHT).isPressed()){
                selectedSkin = (selectedSkin +1)%3;
            }
            if(keyboard.get(Keyboard.LEFT).isPressed()){
                selectedSkin = (selectedSkin - 1 + 3)%3;
            }
            if(selectedSkin <3 && keyboard.get(Keyboard.UP).isPressed()){
                selectedSkin = 3;
            }
            if(selectedSkin == 3 && keyboard.get(Keyboard.DOWN).isPressed()){
                selectedSkin = 1;
            }
            if(keyboard.get(Keyboard.ESCAPE).isPressed()){
                showShop = false;
                updatePlayerSkin();
            }
        }
        super.update(deltaTime);
    }
    /**
     * Orientate and Move this player in the given orientation if the given button is down
     * @param orientation (Orientation): given orientation, not null
     * @param b (Button): button corresponding to the given orientation, not null
     */
    private void moveIfPressed(Orientation orientation, Button b){
        if(b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
            }
        }
    }

    /**
     * Substract the taken damage's value from the player's health points
     * Set hp to 0 if the taken damage is higher than the player's hp
     * @param damage (int): inflicted damage, has to be be a positive int
     */
    public void takeDamage(int damage){
        hp = Math.max(hp-damage,0);
    }


    /**
     * @return getStaff (boolean), not null
     */
    public boolean getStaff(){
        return getStaff;
    }
    //Returns
    //Returns

    /**
     * @return intTransition (boolean): true if the player is transiting from an area to another.
     * false otherwise
     */
    public boolean isInTransition(){
        return inTransition;
    }

    /**
     * End the player's transition from a room to another by setting his inTransition
     * boolean attribute to false.
     */
    public void endTransition(){
        inTransition = false;
    }

    /**
     * Getter for destination
     * @return destination (String)
     */
    public String getDestination(){
        return destination;
    }

    /**
     * Getter for the player's new position
     * @return newPosition (DiscreteCoordinates)
     */
    public DiscreteCoordinates getNewPosition() {
        return newPosition;
    }

    public void throwFire() {
        Fire fire = new Fire(getOwnerArea(), getOrientation(),
                new DiscreteCoordinates((int)this.getPosition().x,
                        (int)this.getPosition().y));
        getOwnerArea().registerActor(fire);
    }


    private class ICRoguePlayerInteractionHandler implements ICRogueInteractionHandler{

        @Override
        public void interactWith(Cherry cherry, boolean isCellInteraction) {
            if(isCellInteraction){
                cherry.collect();
            }
        }

        @Override
        public void interactWith(Staff staff, boolean isCellInteraction){
            if(!isCellInteraction){
                staff.collect();
                getStaff=true;
            }
        }

        @Override
        public void interactWith(Key key, boolean isCellInteraction){
            if(isCellInteraction){
                key.collect();
                keyIdList.add(key.getIdentifier());
            }
        }

        @Override
        public void interactWith(Heart heart, boolean isCellInteraction){
            if(isCellInteraction){
                heart.collect();
                hp += heart.getHealingPoints();
                if(hp > MAX_HP) {
                    hp = MAX_HP;
                }
                updateHeartsSprites();
            }
        }


        @Override
        public void interactWith(Connector connector, boolean isCellInteraction){
            if(isCellInteraction && !isDisplacementOccurs()){
                inTransition = true;
                destination = connector.getTitle();
                newPosition = connector.getPlayerSpawnPosition();
            }
            if(!isCellInteraction && connector.getType() == Connector.connectorType.LOCKED) {
                //check if the locked door can be open without a key
                if (connector.getKeyId() == NO_KEY_ID) {
                    connector.setType(Connector.connectorType.OPEN);
                } else {
                    //check if the player has collected the key that opens this connector
                    for (int key : keyIdList) {
                        if (key == connector.getKeyId()){
                            connector.setType(Connector.connectorType.OPEN);
                            break;
                        }
                    }
                }
            }
        }

        @Override
        public void interactWith(Turret turret, boolean isCellInteraction){
            if(!isCellInteraction){
                if(getSword){
                    if(equippedSkin == 3){
                        animations = getSwordAnimations();
                        animateSword =true;
                    }
                }
            }
            turret.dies();
            ((Level0EnemyRoom)getOwnerArea()).removeEnemy(turret);
        }

        @Override
        public void interactWith(Sword sword, boolean isCellInteraction){
            if(isCellInteraction && !isDisplacementOccurs()){
                sword.collect();
                getSword = true;
            }
        }

        @Override
        public void interactWith(Coin coin, boolean isCellInteraction){
            if(isCellInteraction) {
                updateBalanceGraphicsBool = true;
                balance += 1;
                coin.collect();
            }
        }
    }

    /**
     * @return hp (boolean) : true if the player has 0 health points left
     * false otherwise
     */
    public boolean isDead(){
        return hp==0;
    }

    /**
     * Extract the sprites considering the image's dimensions
     * and create the sword animation
     * @return (Animation[]), not null.
     */
    private Animation[] getSwordAnimations(){

        int animatedSwordFramesNbr = 4;
        Sprite [][] sprites = Sprite.extractSprites("zelda/player.sword",
                animatedSwordFramesNbr, 2, 2,
                this, 32 , 32 , new Orientation [] { Orientation .UP ,
                        Orientation . DOWN , Orientation .RIGHT , Orientation . LEFT });
        return Animation . createAnimations ( MOVE_DURATION / animatedSwordFramesNbr, sprites );
    }
    private final ArrayList<Sprite[][]> sprites = new ArrayList<>( Arrays.asList(
            Sprite.extractSprites("myPngs/policeman",
                    4, 1, 1.31f,
                    this , 16 , 21 , new Orientation [] { Orientation .UP ,
                            Orientation . LEFT , Orientation .DOWN , Orientation . RIGHT }),
            Sprite.extractSprites("myPngs/player",
                    4, 1, 1,
                    this , 16 , 16 , new Orientation [] { Orientation .UP ,
                            Orientation . LEFT , Orientation .DOWN , Orientation . RIGHT }),
            Sprite.extractSprites("myPngs/girl.1",
                    4, 1, 1.31f,
                    this , 16 , 21 , new Orientation [] { Orientation .UP ,
                            Orientation . LEFT , Orientation .DOWN , Orientation . RIGHT }),
            Sprite.extractSprites(SPRITE_NAME,
                    4, 1, 2,
                    this , 16 , 32 , new Orientation [] { Orientation .UP ,
                            Orientation . RIGHT , Orientation .DOWN , Orientation . LEFT })
    ) );
    /**
     * Extract the sprites considering the image's dimensions
     * and create the player's animation
     * @return (Animation[]), not null.
     */
    private Animation[] getPlayerAnimations(int equipedItem){
        return Animation . createAnimations ( MOVE_DURATION, sprites.get(equipedItem) );
    }

    private void updatePlayerSkin(){
        animations = getPlayerAnimations(equippedSkin);
    }


    @Override
    public void enterArea(Area area, DiscreteCoordinates position){
        super.enterArea(area,position);
        ((ICRogueRoom)area).visit();
    }
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells(){
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }


    /**
     * Set the player's  wantsViewInteraction boolean attribute to its opposite value.
     */
    public void switchWantsViewInteraction(){
        wantsViewInteraction = !wantsViewInteraction;
    }
    @Override
    public boolean wantsCellInteraction(){
        return true;
    }
    @Override
    public boolean wantsViewInteraction(){
        return wantsViewInteraction;
    }
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction){
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }
}
